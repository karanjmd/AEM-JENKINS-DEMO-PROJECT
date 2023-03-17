package com.adobe.aem.lacounty.dpss.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "DPSS ErrorPageHandler", description = "DPSS Error Pages Configuration")
public @interface ErrorPageHandlerConfig {
	
	@AttributeDefinition(
			name = "Path of error pages",description = "Enter the error pages path for redirection", type=AttributeType.STRING)
	public String[] getPagePath() ;


	
}
