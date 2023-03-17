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
		"chooser.label=" + "Division Step Selection" })
public class DivisionDynamicParticipantStep implements ParticipantStepChooser {
	private static final Logger log = LoggerFactory.getLogger(ReviewerDynamicParticipantStep.class);

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		Session session = workflowSession.getSession();
		ResourceResolver resourceResolver = null;
		String participant = "";
		String part = " ";
		String payloadPath = workItem.getWorkflowData().getPayload().toString();
		WorkflowData data = workItem.getWorkflow().getWorkflowData();
		part = data.getMetaDataMap().get("Participant").toString();
		if (part.contains("approvers")) {
			part = part.replace("approvers", "");
		} else if (part.contains("reviewers")) {
			part = part.replace("reviewers", "");
		}

		try {
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
				String[] divisionList = ePolicyApprovalConfiguration.division();
				String division = null;
				if (divisionList.length == 1) {
					for (String str : divisionList) {

						division = str;

					}
				} else {
					for (String str : divisionList) {
						if (str.contains("calfresh") ||str.contains("capi")||str.contains("gr")||str.contains("grow")) {
							division = "gr-and-calfresh-approvers";
						}else if(str.contains("calworks")||str.contains("childcare")) {
							division = "calworks-and-csbg-approvers";
						}else if(str.contains("ihss")||str.contains("medical")) {
							division = "medical-and-ihss-approvers";
						}else if(str.contains("gain-1")||str.contains("gain-2")||str.contains("gain-3")) {
							division = "gain-program-approvers";
						}
					}
				}

				if (!division.isEmpty()) {

					participant = division;
					data.getMetaDataMap().put("Participant", participant);

				}

			}

		}
		return participant;
	}

	static void remove(String str, String word) {
		// Split the string using split() method
		String msg[] = str.split(" ");
		String new_str = "";

		// Iterating the string using for each loop
		for (String words : msg) {

			// If desired word is found
			if (!words.equals(word)) {

				// Concat the word not equal to the given
				// word
				new_str += words + " ";
			}
		}

		// Print the new String
		System.out.print(new_str);
	}

}
