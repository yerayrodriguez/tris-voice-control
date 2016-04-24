package com.trisvc.core;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NumeralUtil {
	// Por ahora solo voy a contemplar de 0 a cien en español

		private static Logger logger = LogManager.getLogger(NumeralUtil.class.getName());
		
		private static final String pattern = "(" + "cero|uno|una|dos|tres|cuatro|cinco|seis|siete|ocho|nueve|diez|once|"
				+ "doce|trece|catorce|quince|diecis[eé]is|diecisiete|dieciocho|"
				+ "diecinueve|veinte|veintiun|veintiuno|veintiuna|veintid[oó]s|veintitr[ée]s|veinticuatro|"
				+ "veinticinco|veintis[eé]is|veintisiete|veintiocho|veintinueve|cien" + ")|(?:"
				+ "(treinta|cuarenta|cincuenta|sesenta|setenta|ochenta|noventa)"
				+ "(?:\\sy\\s(uno|una|dos|tres|cuatro|cinco|seis|siete|ocho|nueve))?)";

		private static Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);

		private static final Map<String, Integer> equivalence = new HashMap<String, Integer>();
		static {
			equivalence.put("cero", 0);
			equivalence.put("uno", 1);
			equivalence.put("una", 1);
			equivalence.put("dos", 2);
			equivalence.put("tres", 3);
			equivalence.put("cuatro", 4);
			equivalence.put("cinco", 5);
			equivalence.put("seis", 6);
			equivalence.put("siete", 7);
			equivalence.put("ocho", 8);
			equivalence.put("nueve", 9);
			equivalence.put("diez", 10);
			equivalence.put("once", 11);
			equivalence.put("doce", 12);
			equivalence.put("trece", 13);
			equivalence.put("catorce", 14);
			equivalence.put("quince", 15);
			equivalence.put("dieciseis", 16);
			equivalence.put("dieciséis", 16);
			equivalence.put("diecisiete", 17);
			equivalence.put("dieciocho", 18);
			equivalence.put("diecinueve", 19);
			equivalence.put("veinte", 20);
			equivalence.put("veintiuno", 21);
			equivalence.put("veintiun", 21);
			equivalence.put("veintiuna", 21);
			equivalence.put("veintodos", 22);
			equivalence.put("veintodós", 22);
			equivalence.put("veintitres", 23);
			equivalence.put("veintitrés", 23);
			equivalence.put("veinticuatro", 24);
			equivalence.put("veinticinco", 25);
			equivalence.put("veintiseis", 26);
			equivalence.put("veintiséis", 26);
			equivalence.put("veintisiete", 27);
			equivalence.put("veintiocho", 28);
			equivalence.put("veintinueve", 29);
			equivalence.put("treinta", 30);
			equivalence.put("cuarenta", 40);
			equivalence.put("cincuenta", 50);
			equivalence.put("sesenta", 60);
			equivalence.put("setenta", 70);
			equivalence.put("ochenta", 80);
			equivalence.put("noventa", 90);
			equivalence.put("cien", 100);
		}
		
		public static String convert(String text){
			logger.debug("Converting: "+text);
			StringBuffer sb = new StringBuffer();
			Matcher matcher = compiledPattern.matcher(text);

			while (matcher.find()) {
				logger.trace("Start index: " + matcher.start());
				logger.trace(" End index: " + matcher.end() + " ");
				logger.trace(matcher.group());
				
				Integer value = 0;
				for (int i=1; i<=matcher.groupCount(); i++){
					logger.trace("Group "+i+" "+matcher.group(i)); 
					if (matcher.group(i) != null && equivalence.get(matcher.group(i).toLowerCase())!=null)
						value = value + equivalence.get(matcher.group(i).toLowerCase());
				}			
				logger.trace("Value: "+value);	
				matcher.appendReplacement(sb, Matcher.quoteReplacement(value.toString()));
			}	
			matcher.appendTail(sb);
			logger.debug("Converted: "+sb.toString());
			return sb.toString();
		}

		public static void main(String[] args) {

			convert("es la una y cinco con treinta y TRes y dieciseis o dieciséis ta");

		}

}
