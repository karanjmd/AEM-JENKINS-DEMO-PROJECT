package com.adobe.aem.lacounty.dpss.core.solr.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.osgi.service.event.Event;

import com.adobe.aem.lacounty.dpss.core.solr.listners.SolrPushIndexHandler;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
public class SolrPushIndexHandlerTest {

	private AemContext aemContext = new AemContext();
	private SolrPushIndexHandler solrPushIndexHandler = new SolrPushIndexHandler();
	
	@Mock
	SolrSearchService solrSearchService;

	@Mock
	SolrSearchHelperService solrSearchHelperService;
	
	@Mock
	Event event;
	
			
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		aemContext.registerService(SolrSearchService.class);
		aemContext.registerService(SolrSearchHelperService.class);
		aemContext.registerService(ReplicationActionType.class);
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void handleEventTest() {
		Event event = mock(Event.class);
		//when(event.getTopic()).thenReturn("TRUE");
		//when(ReplicationAction.fromEvent(event).getType().equals(ReplicationActionType.ACTIVATE)).thenReturn(Boolean.TRUE);
		//solrPushIndexHandler.handleEvent(event);
	}
}
