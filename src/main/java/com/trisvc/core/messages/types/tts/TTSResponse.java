package com.trisvc.core.messages.types.tts;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.MessageBody;

@XmlRootElement
public class TTSResponse extends MessageBody {

	private String codedSound;

	public TTSResponse() {
		super();
	}

	public TTSResponse(String codedSound) {
		super();
		this.codedSound = codedSound;
	}

	public String getCodedSound() {
		return codedSound;
	}

	public void setCodedSound(String codedSound) {
		this.codedSound = codedSound;
	}

}
