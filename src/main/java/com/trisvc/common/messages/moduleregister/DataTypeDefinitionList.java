package com.trisvc.common.messages.moduleregister;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "datatypes")
public class DataTypeDefinitionList {

	private List<DataTypeDefinition> datatype;

	public DataTypeDefinitionList(List<DataTypeDefinition> datatype) {
		super();
		this.datatype = datatype;
	}

	public DataTypeDefinitionList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<DataTypeDefinition> getDatatype() {
		return datatype;
	}

	public void setDatatype(List<DataTypeDefinition> datatype) {
		this.datatype = datatype;
	}

	// TODO
	// Think other way to implement
	static final JAXBContext context = initContext();

	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(DataTypeDefinitionList.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static DataTypeDefinitionList unmarshal(File file){
		DataTypeDefinitionList d = new DataTypeDefinitionList();
		try {

			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
			d = (DataTypeDefinitionList) jaxbUnmarshaller.unmarshal(file);

		} catch (JAXBException e) {
			e.printStackTrace();
		}		
		return d;
	}
	
	public static void main(String[] args) {


			File file = new File("./config/datatypes.xml");
			System.out.println(unmarshal(file));


	}

}
