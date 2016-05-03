package com.trisvc.test.core.messages.types.register;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.trisvc.core.messages.Message;
import com.trisvc.core.messages.types.register.RegisterMessage;
import com.trisvc.core.messages.types.register.structures.DTPatternDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinition;
import com.trisvc.core.messages.types.register.structures.DataTypeDefinitionList;
import com.trisvc.core.messages.types.register.structures.ModuleCommand;
import com.trisvc.core.messages.util.MessageUtil;

public class RegisterMessageTest {

	@Test
	public void marshalAndUnmarshallShouldBeEquals() {

		RegisterMessage message = new RegisterMessage();
		message.setModuleName("pruebamodulo");
		List<DataTypeDefinition> listDataTypes = new ArrayList<DataTypeDefinition>();

		DataTypeDefinition tipo1 = new DataTypeDefinition();
		tipo1.setType("Tipo1");

		List<DTPatternDefinition> definition1 = new ArrayList<DTPatternDefinition>();
		DTPatternDefinition t1p1 = new DTPatternDefinition("in t1p1", "out t1p1");
		definition1.add(t1p1);
		DTPatternDefinition t1p2 = new DTPatternDefinition("in t1p2", "out t1p2");
		definition1.add(t1p2);

		tipo1.setDefinitions(definition1);
		listDataTypes.add(tipo1);

		DataTypeDefinition tipo2 = new DataTypeDefinition();
		tipo2.setType("Tipo2");
		tipo2.setWeight(33);

		List<DTPatternDefinition> definition2 = new ArrayList<DTPatternDefinition>();
		DTPatternDefinition t2p1 = new DTPatternDefinition("in t2p1", "out t2p1");
		definition2.add(t2p1);
		DTPatternDefinition t2p2 = new DTPatternDefinition("in t2p2", "out t2p2");
		definition2.add(t2p2);

		tipo2.setDefinitions(definition2);
		listDataTypes.add(tipo2);

		message.setDataTypes(new DataTypeDefinitionList(listDataTypes));

		List<ModuleCommand> listcommands = new ArrayList<ModuleCommand>();

		List<String> lc1 = new ArrayList<String>();
		lc1.add("asdf");
		lc1.add("sdaf");
		ModuleCommand c1 = new ModuleCommand("comando1", lc1);
		listcommands.add(c1);

		List<String> lc2 = new ArrayList<String>();
		lc2.add("asdfasdf");
		lc2.add("ASDFAS");
		ModuleCommand c2 = new ModuleCommand("comando2", lc2);
		listcommands.add(c2);

		message.setCommands(listcommands);

		Message m1 = new Message("CallerID", "MessageID", message);
		m1.setTime(MessageUtil.getXMLGregorianCalendar());

		try {
			String x1 = m1.toString();
			System.out.println(x1);
			Message m2 = (Message) MessageUtil.unmarshal(x1);
			String x2 = m2.toString();
			assertTrue(x1.equals(x2));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
