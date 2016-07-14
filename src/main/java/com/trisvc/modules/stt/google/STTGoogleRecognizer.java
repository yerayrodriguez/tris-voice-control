package com.trisvc.modules.stt.google;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.darkprograms.speech.recognizer.GoogleResponse;
import com.darkprograms.speech.recognizer.Recognizer;
import com.darkprograms.speech.util.Complex;
import com.darkprograms.speech.util.FFT;
import com.trisvc.core.launcher.Launcher;

import net.sourceforge.javaflacencoder.FLACFileWriter;

public class STTGoogleRecognizer {
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	final int FREQ_LIMIT = 150;
	final int WINDOW_UP_LIMIT = 1;
	final int WINDOW_DOWN_LIMIT = 8;
	final int WINDOW_OVERFLOW_LIMIT = 50;
	final double CONFIDENCE_LIMIT = 0.85;
	final String FILE_PATH = "/tmp/trisvc.flac";
	final String GOOGLE_KEY = Launcher.config.getGoogleKey();
	final AudioFormat format = getAudioFormat();
	
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	public static void main(String[] args) {
		STTGoogleRecognizer recognizer = new STTGoogleRecognizer();
		while (true){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(recognizer.recognize());
		}
	}

	public String recognize() {
		TargetDataLine line = null;
		
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); 
		
		if (!AudioSystem.isLineSupported(info)) {
			// Handle the error.
		}
		// Obtain and open the line.
		try {
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}

		
		int numBytesRead;
		byte[] data = new byte[(line.getBufferSize() / 5)];

		// Begin audio capture.
		line.start();

		// Here, stopped is a global boolean set by another thread.
		int contadorArriba = 0, contadorAbajo = 0;

		while (true) {
			// Read the next chunk of data from the TargetDataLine.
			numBytesRead = line.read(data, 0, data.length);
			// Save this chunk of data.
			// System.out.println(numBytesRead);
			int frequency = getFrequency(Arrays.copyOfRange(data, 0, 1024));
			//System.out.println(frequency);

			if (frequency > FREQ_LIMIT) {
				contadorArriba++;
				contadorAbajo = 0;
			} else {
				if (contadorArriba > 0) {
					contadorAbajo++;
				} else {
					contadorAbajo = 0;
				}
			}
			
			if (contadorArriba != 0 || contadorAbajo != 0){
				logger.trace("Counters: "+contadorArriba+" "+contadorAbajo);
			}	
			
			if (contadorArriba > WINDOW_OVERFLOW_LIMIT){
				contadorArriba = 0;
				contadorAbajo = 0;
				out = new ByteArrayOutputStream();
			}

			//if (contadorArriba > 0) {
				out.write(data, 0, numBytesRead);
			//}

			if (contadorArriba > 0 && contadorAbajo > WINDOW_DOWN_LIMIT) {
				if (contadorArriba > WINDOW_UP_LIMIT) {

					
					String result = dumpAndRecognize();
					logger.debug("Voice detected: "+result);
					contadorArriba = 0;
					contadorAbajo = 0;
					out = new ByteArrayOutputStream();					
					
					if (result != null){
						return result;
					}

				}
				contadorArriba = 0;
				contadorAbajo = 0;
				out = new ByteArrayOutputStream();

			}

		}
		

	}
	
	private String dumpAndRecognize(){
		File targetFile = new File(FILE_PATH);
		
		if (targetFile.exists()){
			targetFile.delete();
		}
		
		byte[] abAudioData = out.toByteArray();
		ByteArrayInputStream bais = new ByteArrayInputStream(abAudioData);
		AudioInputStream outputAIS = new AudioInputStream(bais, format,
				abAudioData.length / format.getFrameSize());
		try {
			int nWrittenBytes = AudioSystem.write(outputAIS, FLACFileWriter.FLAC, targetFile);
			outputAIS.close();
			//bais.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		Recognizer rec = new Recognizer(Recognizer.Languages.SPANISH_SPAIN,
				GOOGLE_KEY);
		try {
			GoogleResponse gr = rec.getRecognizedDataForFlac(FILE_PATH);
			if (logger.isDebugEnabled()){
				displayResponse(gr);
			}
			if (gr != null && gr.getConfidence()!= null){
				if (Double.parseDouble(gr.getConfidence())>=CONFIDENCE_LIMIT) {
					return gr.getResponse();
				}	
			}		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void displayResponse(GoogleResponse gr) {
		if (gr.getResponse() == null) {
			logger.trace("Google Response: null");
			return;
		}
		logger.trace("Google Response: " + gr.getResponse());
		logger.trace("Google is " + Double.parseDouble(gr.getConfidence()) * 100 + "% confident in" + " the reply");
		logger.trace("Other Possible responses are: ");
		for (String s : gr.getOtherPossibleResponses()) {
			logger.trace("\t" + s);
		}
	}

	public int getFrequency(byte[] bytes) {
		double[] audioData = bytesToDoubleArray(bytes);

		//System.out.println("lengh: " + audioData.length);
		audioData = applyHanningWindow(audioData);
		Complex[] complex = new Complex[audioData.length];
		for (int i = 0; i < complex.length; i++) {
			complex[i] = new Complex(audioData[i], 0);
		}
		Complex[] fftTransformed = FFT.fft(complex);
		return calculateFundamentalFrequency(fftTransformed, 4);
	}

	private double[] applyHanningWindow(double[] data) {
		return applyHanningWindow(data, 0, data.length);
	}

	/**
	 * Applies a Hanning Window to the data set. Hanning Windows are used to
	 * increase the accuracy of the FFT. One should always apply a window to a
	 * dataset before applying an FFT
	 * 
	 * @param The
	 *            data you want to apply the window to
	 * @param The
	 *            starting index you want to apply a window from
	 * @param The
	 *            size of the window
	 * @return The windowed data set
	 */
	private double[] applyHanningWindow(double[] signal_in, int pos, int size) {
		for (int i = pos; i < pos + size; i++) {
			int j = i - pos; // j = index into Hann window function
			signal_in[i] = (signal_in[i] * 0.5 * (1.0 - Math.cos(2.0 * Math.PI * j / size)));
		}
		return signal_in;
	}

	private int calculateFundamentalFrequency(Complex[] fftData, int N) {
		if (N <= 0 || fftData == null) {
			return -1;
		} // error case

		final int LENGTH = fftData.length;// Used to calculate bin size
		fftData = removeNegativeFrequencies(fftData);
		Complex[][] data = new Complex[N][fftData.length / N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < data[0].length; j++) {
				data[i][j] = fftData[j * (i + 1)];
			}
		}
		Complex[] result = new Complex[fftData.length / N];// Combines the
															// arrays
		for (int i = 0; i < result.length; i++) {
			Complex tmp = new Complex(1, 0);
			for (int j = 0; j < N; j++) {
				tmp = tmp.times(data[j][i]);
			}
			result[i] = tmp;
		}
		int index = findMaxMagnitude(result);
		return index * getFFTBinSize(LENGTH);
	}

	private Complex[] removeNegativeFrequencies(Complex[] c) {
		Complex[] out = new Complex[c.length / 2];
		for (int i = 0; i < out.length; i++) {
			out[i] = c[i];
		}
		return out;
	}

	/**
	 * Calculates the FFTbin size based off the length of the the array Each
	 * FFTBin size represents the range of frequencies treated as one. For
	 * example, if the bin size is 5 then the algorithm is precise to within
	 * 5hz. Precondition: length cannot be 0.
	 * 
	 * @param fftDataLength
	 *            The length of the array used to feed the FFT algorithm
	 * @return FFTBin size
	 */
	private int getFFTBinSize(int fftDataLength) {
		return (int) (getAudioFormat().getSampleRate() / fftDataLength + .5);
	}

	/**
	 * Calculates index of the maximum magnitude in a complex array.
	 * 
	 * @param The
	 *            Complex[] you want to get max magnitude from.
	 * @return The index of the max magnitude
	 */
	private int findMaxMagnitude(Complex[] input) {
		// Calculates Maximum Magnitude of the array
		double max = Double.MIN_VALUE;
		int index = -1;
		for (int i = 0; i < input.length; i++) {
			Complex c = input[i];
			double tmp = c.getMagnitude();
			if (tmp > max) {
				max = tmp;
				;
				index = i;
			}
		}
		return index;
	}

	private double[] bytesToDoubleArray(byte[] bufferData) {
		final int bytesRecorded = bufferData.length;
		final int bytesPerSample = getAudioFormat().getSampleSizeInBits() / 8;
		final double amplification = 100.0; // choose a number as you like
		double[] micBufferData = new double[bytesRecorded - bytesPerSample + 2];
		for (int index = 0, floatIndex = 0; index < bytesRecorded - bytesPerSample
				+ 1; index += bytesPerSample, floatIndex++) {
			double sample = 0;
			for (int b = 0; b < bytesPerSample; b++) {
				int v = bufferData[index + b];
				if (b < bytesPerSample - 1 || bytesPerSample == 1) {
					v &= 0xFF;
				}
				sample += v << (b * 8);
			}
			double sample32 = amplification * (sample / 32768.0);
			micBufferData[floatIndex] = sample32;

		}
		//System.out.println(micBufferData.length);
		return micBufferData;
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 8000.0F;
		// 8000,11025,16000,22050,44100
		int sampleSizeInBits = 16;
		// 8,16
		int channels = 1;
		// 1,2
		boolean signed = true;
		// true,false
		boolean bigEndian = false;
		// true,false
		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
	}



}
