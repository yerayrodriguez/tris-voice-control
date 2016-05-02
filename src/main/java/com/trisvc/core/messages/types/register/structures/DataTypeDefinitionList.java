package com.trisvc.core.messages.types.register.structures;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dataTypes") 
public class DataTypeDefinitionList { 
 
  private List<DataTypeDefinition> dataType; 
 
  public DataTypeDefinitionList(List<DataTypeDefinition> dataType) { 
    super(); 
    this.dataType = dataType; 
  } 
 
  public DataTypeDefinitionList() { 
    super(); 
    // TODO Auto-generated constructor stub 
  } 
 
  public List<DataTypeDefinition> getDataType() { 
    return dataType; 
  } 
 
  public void setDataType(List<DataTypeDefinition> dataType) { 
    this.dataType = dataType; 
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
  
	public static String marshal(DataTypeDefinitionList t) {

		try {
			Marshaller marshaller;
			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(t, stringWriter);
			return stringWriter.toString();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null; 
		}

	}  
   
  public static void main(String[] args) { 
 
 
      File file = new File("./config/datatypes.xml"); 
      System.out.println(marshal(unmarshal(file))); 
 
 
  } 
 
} 