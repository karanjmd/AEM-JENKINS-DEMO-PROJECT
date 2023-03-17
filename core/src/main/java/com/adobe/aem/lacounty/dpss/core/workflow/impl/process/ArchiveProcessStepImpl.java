package com.adobe.aem.lacounty.dpss.core.workflow.impl.process;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.utils.ServiceUserUtil;
import com.day.cq.commons.date.DateUtil;
import com.day.cq.commons.feed.Feed;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

@Component(service = WorkflowProcess.class, property = { "process.label= DPSS Archive Page" })
public class ArchiveProcessStepImpl implements WorkflowProcess {

	private static final Logger LOG = LoggerFactory.getLogger(ArchiveProcessStepImpl.class);

	@Reference
	private ResourceResolverFactory resourceResolverFactory;
	
	@Reference
	private Replicator replicator;

	@Override
	public void execute(WorkItem item, WorkflowSession workflowSession, MetaDataMap meta) throws WorkflowException {
		String path = item.getWorkflowData().getPayload().toString();
		
		if (!(path.startsWith("/content/dpss") && path.contains("/epolicy/program"))) {
			return;
		}
		
		try (ResourceResolver resourceResolver = resourceResolverFactory.getServiceResourceResolver(
				ServiceUserUtil.getWorkflowServiceUserParam())) {
			Resource resource = resourceResolver.getResource(path);

			if (resource != null && resource.isResourceType("cq:Page")) {					
				Session session = resourceResolver.adaptTo(Session.class);
				Node srcNode = resource.adaptTo(Node.class);

				Resource jcrResource = resource.getChild(JcrConstants.JCR_CONTENT);
				ValueMap valueMap = jcrResource.getValueMap();
				Date lastModified = valueMap.get("cq:lastModified", Date.class);
				Date lastReplicated = valueMap.get("cq:lastReplicated", Date.class);

				String pagelink = addHTMLIfPage(resourceResolver, path);
				String achiveEpolicyPath = StringUtils.substringBefore(path, "program");
				String archivedFileName = StringUtils.substringAfterLast(path, "/") + "-" + formatTimeInGMT(lastReplicated);
				String achiveProgramPath = StringUtils.substringAfter(path, "epolicy");
				achiveProgramPath = StringUtils.substringBeforeLast(achiveProgramPath, "/");

				Resource resToAchiveFolder = resourceResolver.getResource(achiveEpolicyPath + "archivedFolder" + achiveProgramPath);

				if (resToAchiveFolder == null) {
					// just create path since first time
					createBackUp(achiveEpolicyPath, achiveProgramPath, archivedFileName, srcNode, session, pagelink);							
				} else {
					createArchivePage(achiveEpolicyPath, achiveProgramPath, 
							lastModified, session, srcNode, resToAchiveFolder, resourceResolver);						
					createBackUp(achiveEpolicyPath, achiveProgramPath, archivedFileName, srcNode, session, pagelink);
				}

				session.save();					
			}				

		} catch (LoginException | RepositoryException | WCMException e) {
			LOG.error("Exception while epolicy achive", e);
		}

	}

	/**
	 * Create archive page
	 * 
	 * @param achiveEpolicyPath
	 * @param achiveProgramPath
	 * @param lastModified
	 * @param session
	 * @param srcNode
	 * @param resToAchiveFolder
	 * @param resourceResolver 
	 * @throws RepositoryException
	 * @throws WCMException 
	 */
	private void createArchivePage(String achiveEpolicyPath, String achiveProgramPath, 
			Date lastModified, Session session, Node srcNode, Resource resToAchiveFolder, ResourceResolver resourceResolver) throws RepositoryException, WCMException {
		String achivePath = achiveEpolicyPath + "archive";
		String archiveFileName = getArchivedNameFromSource(srcNode);
		Resource resChild = resToAchiveFolder.getChild(archiveFileName);
		Node nodeChild = resChild.adaptTo(Node.class);
		String nodeName = nodeChild.getName();
		Date archiveModifiedDate = resChild.getValueMap().get("jcr:content/cq:lastModified", Date.class);

		createMicrositeFiles(achiveEpolicyPath, StringUtils.substringAfter(achivePath + achiveProgramPath, "epolicy/"), 
				session, resourceResolver);
		
		if (archiveModifiedDate.compareTo(lastModified) < 0) {
			Node nodeParent = JcrUtil.createPath(achivePath + achiveProgramPath, 
					"cq:Page", "cq:Page", session, true);
			JcrUtil.copy(nodeChild, nodeParent, nodeName, false);
		}
		
		nodeChild.remove();
		replicateNode(achivePath + achiveProgramPath + "/" + nodeName, session);		
	}

	/**
	 * Replicated the archive Page
	 * @param replicationPath
	 * @param session
	 */
	private void replicateNode(String replicationPath, Session session) {
		try {
			replicator.replicate(session, ReplicationActionType.ACTIVATE, replicationPath);
		} catch (ReplicationException e) {
			LOG.error("Replication Exception while epolicy is published", e);
		}		
	}

	/**
	 * Create microsite pages
	 * 
	 * @param rootPath
	 * @param archivePath
	 * @param session
	 * @param resourceResolver
	 * @throws RepositoryException
	 * @throws WCMException
	 */
	private void createMicrositeFiles(String rootPath, String archivePath, 
			Session session, ResourceResolver resourceResolver) throws RepositoryException, WCMException {
		String[] token = StringUtils.split(archivePath, "/");
		PageManager pageMgr = resourceResolver.adaptTo(PageManager.class);
				
		for (String fileName : token) {
			String filePath = rootPath + fileName;
			if (!session.nodeExists(filePath)) {
				pageMgr.create(rootPath, fileName, "/conf/dpss/settings/wcm/templates/microsite-page", fileName, true);
			}
			rootPath = filePath + "/";
		}
	}

	/**
	 * Create back up for the latest epolicy page
	 * 
	 * @param achiveEpolicyPath
	 * @param achiveProgramPath
	 * @param archivedFileName
	 * @param srcNode
	 * @param session
	 * @param pagelink
	 * @throws RepositoryException
	 */
	private void createBackUp(String achiveEpolicyPath, 
			String achiveProgramPath, String archivedFileName, Node srcNode, Session session, String pagelink) throws RepositoryException {
		achiveEpolicyPath = achiveEpolicyPath + "archivedFolder";
		Node nodeParent = JcrUtil.createPath(achiveEpolicyPath + achiveProgramPath, "nt:unstructured", "nt:unstructured", session, true);						
		JcrUtil.copy(srcNode, nodeParent, archivedFileName, false);

		Node jcrNode = nodeParent.getNode(archivedFileName + "/jcr:content");
		jcrNode.setProperty("isArchived", true);
		jcrNode.setProperty("jcr:title", archivedFileName);
		jcrNode.setProperty("mainPageLink", pagelink);

		setArchivedNameToSource(srcNode, archivedFileName);		
	}

	/**
	 * Set the archive page name
	 * 
	 * @param srcNode
	 * @param archivedFileName
	 * @throws RepositoryException
	 */
	private void setArchivedNameToSource(Node srcNode, String archivedFileName) throws RepositoryException {
		Node srcJCRNode = srcNode.getNode("jcr:content");
		srcJCRNode.setProperty("archivedFileName", archivedFileName);		
	}

	/**
	 * Get the archive page name
	 * 
	 * @param srcNode
	 * @return String
	 * @throws RepositoryException
	 */
	private String getArchivedNameFromSource(Node srcNode) throws RepositoryException {
		Node jcrNode = srcNode.getNode("jcr:content");
		return jcrNode.getProperty("archivedFileName").getString();		
	}

	/**
	 * Format time in GMT
	 * 
	 * @param date
	 * @return String
	 */
	private String formatTimeInGMT(final Date date) {
		if (date != null) {
			final TimeZone tz = TimeZone.getTimeZone("GMT");
			final String gmtDateTime = DateUtil.getISO8601Date(date, tz);
			String datetime = Instant.parse(gmtDateTime).atOffset(ZoneOffset.UTC)
					.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
			datetime = StringUtils.replace(datetime, " ", "-");
			datetime = StringUtils.replace(datetime, ":", "-");
			return datetime;
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Add html extension if page
	 * 
	 * @param resourceResolver
	 * @param url
	 * @return String
	 */
	private String addHTMLIfPage(final ResourceResolver resourceResolver, final String url) {
		if (url == null || url.length() == 0)
			return StringUtils.EMPTY;

		final PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		if (pageManager != null) {
			final Page page = pageManager.getPage(url);
			if (page == null)
				return url;
		}
		final String mappedURL = resourceResolver.map(url);
		return (mappedURL.length() > 1) ? (mappedURL + Feed.SUFFIX_HTML) : url;
	}
}
