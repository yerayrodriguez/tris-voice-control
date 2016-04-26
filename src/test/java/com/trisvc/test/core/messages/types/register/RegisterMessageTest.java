package com.trisvc.test.core.messages.types.register;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.types.parser.ParserMessage;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.structures.PhraseDefinition;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;
import com.trisvc.core.messages.types.register.structures.PatternTemplateDefinition;
import com.trisvc.core.messages.types.register.structures.PhraseConfig;
import com.trisvc.core.messages.util.MessageUtil;

public class RegisterMessageTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {

		RegisterMessage message = new RegisterMessage();
		message.setModuleName("pruebamodulo");
		List<PhraseDefinition> listDataTypes = new ArrayList<PhraseDefinition>();
		
		PhraseDefinition tipo1 = new PhraseDefinition();
		tipo1.setName("Tipo1");
		
		List<PatternTemplateDefinition> definition1 = new ArrayList<PatternTemplateDefinition>();
		PatternTemplateDefinition t1p1 = new PatternTemplateDefinition("in t1p1","out t1p1");
		definition1.add(t1p1);
		PatternTemplateDefinition t1p2 = new PatternTemplateDefinition("in t1p2","out t1p2");
		definition1.add(t1p2);	
		
		tipo1.setPatterns(definition1);
		listDataTypes.add(tipo1);
		
		PhraseDefinition tipo2 = new PhraseDefinition();
		tipo2.setName("Tipo2");
		
		List<PatternTemplateDefinition> definition2 = new ArrayList<PatternTemplateDefinition>();
		PatternTemplateDefinition t2p1 = new PatternTemplateDefinition("in t2p1","out t2p1");
		definition2.add(t2p1);
		PatternTemplateDefinition t2p2 = new PatternTemplateDefinition("in t2p2","out t2p2");
		definition2.add(t2p2);			
		
		tipo2.setPatterns(definition2);
		listDataTypes.add(tipo2);		
		
		message.setPhraseDefinitions(listDataTypes);
		
		
		List<ModuleCommand> listcommands = new ArrayList<ModuleCommand>();
		
		List<PhraseConfig> lc1 = new ArrayList<PhraseConfig>();
		lc1.add(new PhraseConfig("Tipo1",false, false));
		lc1.add(new PhraseConfig("Tipo2",true, false));
		ModuleCommand c1 = new ModuleCommand("comando1",lc1);
		listcommands.add(c1);
		
		List<PhraseConfig> lc2 = new ArrayList<PhraseConfig>();
		lc2.add(new PhraseConfig("TipoX",false, true));
		lc2.add(new PhraseConfig("TipoY",true, true));
		ModuleCommand c2 = new ModuleCommand("comando2",lc2);
		listcommands.add(c2);
		
		message.setCommands(listcommands);
		
		Message<RegisterMessage> m1 = new Message<RegisterMessage> ("CallerID", "MessageID",message); 
		m1.setTime(MessageUtil.getXMLGregorianCalendar()); 


		try {
			String x1 = m1.toString();
			System.out.println(x1);
			@SuppressWarnings("unchecked")
			Message<ParserMessage> m2 = (Message<ParserMessage>) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
