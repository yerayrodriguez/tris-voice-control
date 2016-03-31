package com.trisvc.common.messages;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface MessageBody {
	public String getModuleName();
}
