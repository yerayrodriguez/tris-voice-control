package com.trisvc.core.messages.types.register;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.MessageBody;
import com.trisvc.core.messages.types.register.structures.DataTypeConfig;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;
import com.trisvc.core.messages.types.register.structures.PatternTemplateDefinition;
import com.trisvc.core.messages.util.MessageUtil;

@XmlRootElement
public class RegisterMessage extends MessageBody{

	public String moduleName;
	public DataTypeDefinitionList datatypes;
	public List<ModuleCommand> commands;
	
	public RegisterMessage() {
		super();
	}	

	public RegisterMessage(String moduleName, DataTypeDefinitionList datatypes, List<ModuleCommand> commands) {
		super();
		this.moduleName = moduleName;
		this.datatypes = datatypes;
		this.commands = commands;
	}
	
	public static void main(String[] args) {
		// MessagePrueba prueba = new MessagePrueba();
		RegisterMessage message = new RegisterMessage();
		message.moduleName = "pruebamodulo";
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
		
		message.datatypes = new DataTypeDefinitionList(listDataTypes);
		
		
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
		
		message.commands = listcommands;
		
		Message<RegisterMessage> t = new Message<RegisterMessage> ("CallerID", "MessageID",message); 
		t.time = MessageUtil.getXMLGregorianCalendar(); 


		try {
			String text = t.toString();
			System.out.println(text);
			@SuppressWarnings("unchecked")
			Message<RegisterMessage> t2 = (Message<RegisterMessage>) MessageUtil.unmarshal( text);
			// TTSMessage t2 = new TTSMessage();
			// t2.unmarshal(text);
			System.out.println(t2.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
