package com.trisvc.common.messages.modulefunctionality;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.common.messages.Message;
import com.trisvc.common.messages.MessageType;

@XmlRootElement(name = "message")
public class ModuleFunctionality extends Message{

	private String moduleName;
	private List<DataTypeDefinition> datatype;
	private List<ModuleCommand> commands;

	public ModuleFunctionality(String moduleName, List<DataTypeDefinition> datatype, List<ModuleCommand> commands) {
		this.moduleName = moduleName;
		this.datatype = datatype;
		this.commands = commands;
	}

	public ModuleFunctionality() {
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String name) {
		this.moduleName = name;
	}

	@XmlElementWrapper( name="datatypes" )
	public List<DataTypeDefinition> getDatatype() {
		return datatype;
	}

	public void setDatatype(List<DataTypeDefinition> datatype) {
		this.datatype = datatype;
	}
	
	

	public List<ModuleCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<ModuleCommand> commands) {
		this.commands = commands;
	}

	@Override
	public String toString() {
		return "ModuleFunctionality [moduleName=" + moduleName + ", datatype=" + datatype + "]";
	}

	@Override
	public MessageType getType() {
		// TODO Auto-generated method stub
		return MessageType.ModuleFunctionality;
	}
	
	// TODO
	// Think other way to implement
	static final JAXBContext context = initContext();

	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(Message.class, ModuleFunctionality.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}	
	
	public static void main(String[] args) {
		// MessagePrueba prueba = new MessagePrueba();
		ModuleFunctionality message = new ModuleFunctionality();
		message.setModuleName("pruebamodulo");
		List<DataTypeDefinition> listDataTypes = new ArrayList<DataTypeDefinition>();
		
		DataTypeDefinition tipo1 = new DataTypeDefinition();
		tipo1.setDataTypeName("Tipo1");
		
		List<PatternTemplateDefinition> definition1 = new ArrayList<PatternTemplateDefinition>();
		PatternTemplateDefinition t1p1 = new PatternTemplateDefinition("in t1p1","out t1p1");
		definition1.add(t1p1);
		PatternTemplateDefinition t1p2 = new PatternTemplateDefinition("in t1p2","out t1p2");
		definition1.add(t1p2);	
		
		tipo1.setDefinition(definition1);
		listDataTypes.add(tipo1);
		
		DataTypeDefinition tipo2 = new DataTypeDefinition();
		tipo2.setDataTypeName("Tipo2");
		
		List<PatternTemplateDefinition> definition2 = new ArrayList<PatternTemplateDefinition>();
		PatternTemplateDefinition t2p1 = new PatternTemplateDefinition("in t2p1","out t2p1");
		definition2.add(t2p1);
		PatternTemplateDefinition t2p2 = new PatternTemplateDefinition("in t2p2","out t2p2");
		definition2.add(t2p2);			
		
		tipo2.setDefinition(definition2);
		listDataTypes.add(tipo2);		
		
		message.setDatatype(listDataTypes);
		
		
		List<ModuleCommand> listcommands = new ArrayList<ModuleCommand>();
		
		List<DataTypeConfig> lc1 = new ArrayList<DataTypeConfig>();
		lc1.add(new DataTypeConfig("Tipo1",false));
		lc1.add(new DataTypeConfig("Tipo2",true));
		ModuleCommand c1 = new ModuleCommand("comando1",lc1);
		listcommands.add(c1);
		
		List<DataTypeConfig> lc2 = new ArrayList<DataTypeConfig>();
		lc2.add(new DataTypeConfig("TipoX",false));
		lc2.add(new DataTypeConfig("TipoY",true));
		ModuleCommand c2 = new ModuleCommand("comando2",lc2);
		listcommands.add(c2);
		
		message.setCommands(listcommands);

		try {

			String text = Message.marshal(ModuleFunctionality.context, message);
			System.out.println(text);
			ModuleFunctionality m2 = (ModuleFunctionality) Message.unmarshal(ModuleFunctionality.context, text);
			String text2 = Message.marshal(ModuleFunctionality.context, m2);
			if (text.equals(text2)){
				System.out.println("OK");
			}else{
				System.out.println("KO");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
