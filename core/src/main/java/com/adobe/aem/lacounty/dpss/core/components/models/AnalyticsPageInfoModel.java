package com.adobe.aem.lacounty.dpss.core.components.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import com.day.text.Text;
import org.apache.sling.api.resource.Resource;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import com.day.cq.wcm.api.Page;
import com.day.cq.commons.jcr.JcrConstants;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

@Model(adaptables = SlingHttpServletRequest.class, adapters = AnalyticsPageInfoModel.class)
public class AnalyticsPageInfoModel {
	@ScriptVariable
	private Page currentPage;

	@SlingObject
	private ResourceResolver resourceResolver;

	@Self
	protected SlingHttpServletRequest request;

	public String getSiteSection() {
		String returnValue = "";
		if (currentPage.getDepth() >= 5) {
			returnValue = getPageNameByCategory(4, currentPage, resourceResolver);
		}
		if (returnValue == null || "".equals(returnValue)) {
			returnValue = "";
		}
		return returnValue.trim().toLowerCase();
	}

	public String getSiteSectionLevelOne() {
		String returnValue = "";
		if (currentPage.getDepth() >= 6) {
			returnValue = getPageNameByCategory(5, currentPage, resourceResolver);
		}
		if (returnValue == null || "".equals(returnValue)) {
			returnValue = "";
		}
		return returnValue.trim().toLowerCase();
	}

	public String getSiteSectionLevelTwo() {
		String returnValue = "";
		if (currentPage.getDepth() >= 7) {
			returnValue = getPageNameByCategory(6, currentPage, resourceResolver);
		}
		if (returnValue == null || "".equals(returnValue)) {
			returnValue = "";
		}
		return returnValue.trim().toLowerCase();
	}

	public String getSiteSectionLevelThree() {
		String returnValue = "";
		if (currentPage.getDepth() >= 8) {
			returnValue = getPageNameByCategory(7, currentPage, resourceResolver);
		}
		if (returnValue == null || "".equals(returnValue)) {
			returnValue = "";
		}
		return returnValue.trim().toLowerCase();
	}

	public String getSiteSectionLevelFour() {
		String returnValue = "";
		if (currentPage.getDepth() >= 9) {
			returnValue = getPageNameByCategory(8, currentPage, resourceResolver);
		}
		if (returnValue == null || "".equals(returnValue)) {
			returnValue = "";
		}
		return returnValue.trim().toLowerCase();
	}

	public String getSiteSectionLevelFive() {
		String returnValue = "";
		if (currentPage.getDepth() >= 10) {
			returnValue = getPageNameByCategory(9, currentPage, resourceResolver);
		}
		if (returnValue == null || "".equals(returnValue)) {
			returnValue = "";
		}
		return returnValue.trim().toLowerCase();
	}

	public String getPageName() {
		String urlname = "";
		String pageName = currentPage.getPath();
		String[] tokens = pageName.split("/");
		if(tokens[tokens.length-1].equals("homepage"))
		{
			return "dpss:en:homepage";
		}
		else
		{
		for (String s1 : tokens) {
			if (!s1.equals("content")) {
				urlname += s1 + ":";
			}
		}
		return urlname.substring(1, urlname.length() - 1).toLowerCase().replaceAll("homepage:","");
		}

	}

	public String getPageType() throws ValueFormatException, PathNotFoundException, RepositoryException {
		String pageName = currentPage.getPath();
		Resource resource = resourceResolver.getResource(pageName + "/jcr:content");
		Node pageNode = resource.adaptTo(Node.class);
		String templatepath = pageNode.getProperty("cq:template").getString();
		Resource resourcetemplate = resourceResolver.getResource(templatepath + "/jcr:content");
		Node tempnode = resourcetemplate.adaptTo(Node.class);
		return tempnode.getProperty("jcr:title").getString().toLowerCase();
	}

	public static String getPageNameByCategory(int level, Page currentPage, ResourceResolver resourceResolver) {
		String returnValue = null;

		String pagePathForLevel = currentPage.getPath();
		try {
			String sectionPagePath = Text.getAbsoluteParent(pagePathForLevel, level);
			if (sectionPagePath != null && sectionPagePath.length() > 0) {
				Resource resource = resourceResolver.getResource(sectionPagePath + "/jcr:content");
				if (resource != null) {
					Node pageNode = resource.adaptTo(Node.class);
					if (pageNode != null) {
						returnValue = pageNode.getProperty(JcrConstants.JCR_TITLE).getString();
					}
				}
			}
		} catch (RepositoryException re) {
		}
		if (returnValue == null || "".equals(returnValue)) {
			returnValue = "";
		}
		return returnValue.trim();
	}
}
