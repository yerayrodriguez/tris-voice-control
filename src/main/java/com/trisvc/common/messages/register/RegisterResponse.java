package com.trisvc.common.messages.register;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.Message;
import com.trisvc.common.messages.Response;
import com.trisvc.common.messages.ResponseType;
import com.trisvc.common.messages.register.structures.DataTypeConfig;
import com.trisvc.common.messages.register.structures.DataTypeDefinition;
import com.trisvc.common.messages.register.structures.DataTypeDefinitionList;
import com.trisvc.common.messages.register.structures.ModuleCommand;
import com.trisvc.common.messages.register.structures.PatternTemplateDefinition;

@XmlRootElement(name = "message")
public class RegisterResponse extends Response{

	private String errorMsg;
	private Boolean success;

	

	public RegisterResponse(String errorMsg, Boolean success) {
		super();
		this.errorMsg = errorMsg;
		this.success = success;
	}



	public RegisterResponse() {
	}



	@Override
	public String toString() {
		return RegisterResponse.marshal(context,this);
	}

	@Override
	public ResponseType getType() {
		// TODO Auto-generated method stub
		return ResponseType.RegisterResponseType;
	}
	
	// TODO
	// Think other way to implement
	static final JAXBContext context = initContext();

	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(Response.class, RegisterResponse.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
	


}
