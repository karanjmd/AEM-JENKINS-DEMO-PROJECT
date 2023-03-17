/**
 * 
 */
package com.adobe.aem.lacounty.dpss.core.workflow.impl.process;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import javax.jcr.Session;

import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.ParticipantStepChooser;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.metadata.MetaDataMap;


/**
 * @author vikas-ku
 *
 */
@Component(service = ParticipantStepChooser.class, immediate = true, property = {
		"chooser.label=" + "Contributors Step Selection" })
public class ContributorsDynamicParticipantStep implements ParticipantStepChooser{
	private static final Logger log = LoggerFactory.getLogger(ContributorsDynamicParticipantStep.class);
	
	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {
			
		String participant = "";
		String payloadPath = workItem.getWorkflowData().getPayload().toString();
		String initiator = workItem.getWorkflow().getInitiator();
		
		ResourceResolver resourceResolver = workflowSession.adaptTo(ResourceResolver.class);
		
		Resource resource = resourceResolver.getResource(payloadPath);
		 
		
		
		if (Objects.nonNull(resource)) {
			
			MetaDataMap wfd = workItem.getWorkflow().getWorkflowData().getMetaDataMap();
			wfd.put("Initiator", initiator);
			
			Set<String> keyset = wfd.keySet();
			Iterator<String> i = keyset.iterator();
			

			ConfigurationBuilder configurationBuilder = resource.adaptTo(ConfigurationBuilder.class);
			if (Objects.nonNull(configurationBuilder)) {
				EPolicyApprovalConfiguration ePolicyApprovalConfiguration = configurationBuilder
						.as(EPolicyApprovalConfiguration.class);
				String contributors = ePolicyApprovalConfiguration.contributors();
				if (!contributors.isEmpty()) {					

					participant = contributors;
					wfd.put("Participant", participant);
					
				} else {
					participant = "admin";
				}

			}

		}
		return participant;
	}


}
