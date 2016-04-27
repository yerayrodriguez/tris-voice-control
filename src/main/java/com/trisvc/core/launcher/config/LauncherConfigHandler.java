package com.trisvc.core.launcher.config;

import java.io.File;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LauncherConfigHandler {

	private static Logger logger = LogManager.getLogger(LauncherConfigHandler.class.getName());

	static public LauncherConfig load() {

		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error("Could not get host name");
		}

		File f = null;
		if (hostName != null) {
			f = new File("config/launcher." + hostName + ".xml");
		}

		if (f == null || !f.exists()) {
			f = new File("config/launcher.xml");

			if (!f.exists()) {
				logger.error("launcher.xml does not exist");
				return new LauncherConfig();
			}
		}

		logger.info("Loading " + f.getName());

		return unmarshal(f);

	}

	static final JAXBContext context = initContext();

	private static JAXBContext initContext() {
		try {
			return JAXBContext.newInstance(LauncherConfig.class);
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static LauncherConfig unmarshal(File file) {
		LauncherConfig l = new LauncherConfig();
		try {
			Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
			l = (LauncherConfig) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return l;
	}

	public static String marshal(LauncherConfig t) {

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

}
