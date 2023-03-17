/**
 * 
 */
package com.adobe.aem.lacounty.dpss.core.workflow.impl.process;

import java.util.Collections;
import java.util.Iterator;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
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
		"chooser.label=" + "DPSS Get Initiator Group" })
public class GetInitiator implements ParticipantStepChooser {

	private static final Logger log = LoggerFactory.getLogger(GetInitiator.class);

	@Reference
	private ResourceResolverFactory resourceResolverFactory;

	@Override
	public String getParticipant(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
			throws WorkflowException {

		Session session = workflowSession.getSession();
		ResourceResolver resourceResolver = null;
		String participant = "";
		WorkflowData data = workItem.getWorkflow().getWorkflowData();
		try {
			resourceResolver = resourceResolverFactory.getResourceResolver(
					Collections.singletonMap(JcrResourceConstants.AUTHENTICATION_INFO_SESSION, (Object) session));

			final UserManager userManager = resourceResolver.adaptTo(UserManager.class);
			final User user = (User) userManager.getAuthorizable(session.getUserID());
			Iterator<Group> itr = user.memberOf();
			while (itr.hasNext()) {
				Group group = (Group) itr.next();
				log.info("group.getID().." + group.getID());
				String gr = group.getID();
				if (gr.equalsIgnoreCase("gain-1-contributors") || gr.equalsIgnoreCase("gain-2-contributors")
						|| gr.equalsIgnoreCase("gain-3-contributors")
						|| gr.equalsIgnoreCase("calfresh-contributors")
						|| gr.equalsIgnoreCase("calworks-contributors")
						|| gr.equalsIgnoreCase("capi-contributors")
						|| gr.equalsIgnoreCase("childcare-contributors")
						|| gr.equalsIgnoreCase("gr-contributors")
						|| gr.equalsIgnoreCase("grow-contributors")
						|| gr.equalsIgnoreCase("ihss-contributors")
						|| gr.equalsIgnoreCase("medical-contributors")) {
					participant = gr;
					data.getMetaDataMap().put("Participant", participant);
					workflowSession.updateWorkflowData(workItem.getWorkflow(), data);
					System.out.println("Participant" + participant);
					break;
				}else {
					participant=gr;
					data.getMetaDataMap().put("Participant", participant);
					workflowSession.updateWorkflowData(workItem.getWorkflow(), data);
				}
				log.info("group.getPrincipal().getName().." + group.getPrincipal().getName());
			}
		} catch (LoginException e) {

			e.printStackTrace();
		} catch (RepositoryException e) {

			e.printStackTrace();
		}

		return participant;
	}

}
