package com.adobe.aem.lacounty.dpss.core.solr.config;

import org.apache.http.HttpHost;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "DPSS Solr - Solr Configuration", description = "DPSS Solr Service Configuration")
public @interface DPSSSOLRConfig {


	@AttributeDefinition(name = "Solr Query Server URL", defaultValue = "http://localhost:8983", description = "Replica Solr Server used for Searching")
	String queryServerName();

	@AttributeDefinition(name = "Solr Indexing Server URL", defaultValue = "http://localhost:8983", description = "Primary Solr Server used for Indexing content")
	String indexSeverName();

	@AttributeDefinition(name = "Content page path", defaultValue = "/content/dpss", description = "Content page path from where solr has to index the pages")
	String sitePath();

	@AttributeDefinition(name = "User name", description = "User name having permission on AEM for index operation ", type = AttributeType.STRING)
	String userName();

	@AttributeDefinition(name = "Password", description = "Password ", type = AttributeType.PASSWORD)
	String password();
	
	@AttributeDefinition(name = "Solr Collection Mapping", defaultValue = "[/content/dpss/en:aem-employee]", description = "List of Solr Collection Page Mapping with : separation ", type = AttributeType.STRING)
	String[] collectionMappings();

	@AttributeDefinition(name = "DPSS Default Core Name", defaultValue = "aem-employee", description = "DPSS default collection name to be used when there is no apping found.")
	String collectionName();

}