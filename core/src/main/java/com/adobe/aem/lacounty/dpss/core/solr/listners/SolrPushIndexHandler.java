package com.adobe.aem.lacounty.dpss.core.solr.listners;

import java.io.IOException;
import java.util.Set;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchHelperService;
import com.adobe.aem.lacounty.dpss.core.solr.services.SolrSearchService;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;

@Component(immediate = true, service = EventHandler.class, property = {
		Constants.SERVICE_DESCRIPTION
				+ "= This event handler listens the events on page activation and communicate with solr",
		EventConstants.EVENT_TOPIC + "=" + ReplicationAction.EVENT_TOPIC })
public class SolrPushIndexHandler implements EventHandler {
	private static final Logger LOG = LoggerFactory.getLogger(SolrPushIndexHandler.class);

	@Reference
	SolrSearchService solrSearchService;

	@Reference
	SolrSearchHelperService solrSearchHelperService;

	@Override
	public void handleEvent(Event event) {

		if (ReplicationAction.fromEvent(event).getType().equals(ReplicationActionType.ACTIVATE)) {
			String payloadPath = ReplicationAction.fromEvent(event).getPath();
			LOG.debug("SOLR: Page publish index creation for the payload started : " + payloadPath);

			boolean resultindexingPages = solrSearchService.crawlAndCreateIndex(payloadPath);
			LOG.info("SOLR: Successfully indexed content pages to Solr server  for " + payloadPath);
		}

		if (ReplicationAction.fromEvent(event).getType().equals(ReplicationActionType.DEACTIVATE)) {
			String payloadPath = ReplicationAction.fromEvent(event).getPath();
			LOG.info("SOLR: Page publish index deletion for the payload started : ", payloadPath);
			solrSearchService.solrDeleteContent(payloadPath);
		}		

	}
}
