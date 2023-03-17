/**
 * 
 */
package com.adobe.aem.lacounty.dpss.core.workflow.impl.process;

import java.util.Collections;
import java.util.Objects;

import javax.jcr.Session;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.caconfig.ConfigurationBuilder;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.ParticipantStepChooser;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.metadata.MetaDataMap;

/**
 * @author vikas-ku
 *
 */
@Component(service = ParticipantStepChooser.class, immediate = true, property = {
		"chooser.label=" + "Reviewers Step Selection" })
public class ReviewerDynamicParticipantStep implements ParticipantStepChooser {
	private static final Logger log = LoggerFactory.getLogger(ReviewerDynamicParticipantStep.class);

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		Session session = workflowSession.getSession();
		ResourceResolver resourceResolver = null;
		String participant = "";
		String part = "";
		String payloadPath = workItem.getWorkflowData().getPayload().toString();
		WorkflowData data = workItem.getWorkflow().getWorkflowData();
		part = data.getMetaDataMap().get("Participant").toString();
		if (!part.isEmpty()) {
			if (part.contains("contributors")) {
				part = part.replace("contributors", "");
			} else {
				part = "";
			}
		}

		try

		{
			resourceResolver = resourceResolverFactory.getResourceResolver(
					Collections.singletonMap(JcrResourceConstants.AUTHENTICATION_INFO_SESSION, (Object) session));
		} catch (LoginException e) {

			e.printStackTrace();
		}

		Resource resource = resourceResolver.getResource(payloadPath);
		if (Objects.nonNull(resource)) {
			ConfigurationBuilder configurationBuilder = resource.adaptTo(ConfigurationBuilder.class);
			if (Objects.nonNull(configurationBuilder)) {
				EPolicyApprovalConfiguration ePolicyApprovalConfiguration = configurationBuilder
						.as(EPolicyApprovalConfiguration.class);
				String[] reviewerList = ePolicyApprovalConfiguration.reviewers();
				String[] secList = ePolicyApprovalConfiguration.section();
				String reviewer = null;
				String sec = null;

				for (String str : reviewerList) {
					
						if (str.contains(part)) {
							reviewer = str;
						
					}
				}
				for (String str : secList) {
					
						if (str.contains(part)) {
							sec = str;
						
					}
				}

				if (!reviewer.isEmpty()) {

					participant = reviewer;
					data.getMetaDataMap().put("Participant", participant);

				} else {

					participant = sec;
					data.getMetaDataMap().put("Participant", participant);

				}

			}

		}
		return participant;
	}

}
