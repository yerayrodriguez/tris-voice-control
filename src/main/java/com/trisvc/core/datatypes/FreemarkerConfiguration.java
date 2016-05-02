package com.trisvc.core.datatypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerConfiguration {

	private static Logger logger = LogManager.getLogger(FreemarkerConfiguration.class.getName());
	private volatile static Configuration instance = null;

	protected FreemarkerConfiguration() {
	}

	// Lazy Initialization (If required then only)
	public static Configuration getInstance() {
		if (instance == null) {
			// Thread Safe. Might be costly operation in some case
			synchronized (Configuration.class) {
				if (instance == null) {
					initializeConfiguration();
				}
			}
		}
		return instance;
	}

	private static void initializeConfiguration() {
		logger.debug("Creating configuration");
		instance = new Configuration(Configuration.VERSION_2_3_24);
		instance.setDefaultEncoding("UTF-8");
		// Sets how errors will appear.
		// During web page *development*
		// TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		instance.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		instance.setLogTemplateExceptions(false);
	}
}