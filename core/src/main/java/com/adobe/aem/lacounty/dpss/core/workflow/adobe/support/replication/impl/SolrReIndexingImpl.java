/**
 * 
 */
package com.adobe.aem.lacounty.dpss.core.workflow.adobe.support.replication.impl;

import com.adobe.aem.lacounty.dpss.core.solr.services.SolrReIndexingService;
import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchHelperService;
import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchService;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gajula
 *
 */
@Component(service=WorkflowProcess.class, 
property = {
	"process.label=Solr ReIndexing"})
public class SolrReIndexingImpl implements WorkflowProcess{
	
	private static final Logger LOG = LoggerFactory.getLogger(SolrReIndexingImpl.class);
	
	@Reference
	SolrReIndexingService solrReIndexingService;

	@Reference
	SolrSearchHelperService solrSearchHelperService;

	@Override
	public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap arg) throws WorkflowException {
		
		final WorkflowData workflowData = workItem.getWorkflowData();
		final String type = workflowData.getPayloadType();
		final String payloadPath = workflowData.getPayload().toString();
		
		LOG.info("SOLR: Page publish index creation for the payload started : " + payloadPath);

		boolean resultindexingPages = solrReIndexingService.crawlAndCreateIndex(payloadPath);
		LOG.info("SOLR: Successfully indexed content pages to Solr server  for " + payloadPath);
		
	}

}
