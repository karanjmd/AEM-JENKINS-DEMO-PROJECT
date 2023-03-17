package com.adobe.aem.lacounty.dpss.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.lacounty.dpss.core.beans.HeaderLinkBean;
import com.adobe.aem.lacounty.dpss.core.models.ContentPageBannerModel;
import com.adobe.aem.lacounty.dpss.core.utils.ComponentUtils;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = ContentPageBannerModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentPageBannerModelImpl implements ContentPageBannerModel{

	private static final String PN_BG_IMAGE = "globalBgImage";
	private static final String PN_ITEM_TEXT = "itemText";
	private static final String PN_ITEM_TARGET = "itemTarget";
	private static final String PN_ITEM_ACTION = "itemAction";
	private static final String NODE_LINKS = "link";

	@Self
	private SlingHttpServletRequest request;

	@ScriptVariable
	@SlingObject
	private Resource resource;

	@ScriptVariable
	private PageManager pageManager;

	@ScriptVariable
	private Page currentPage;
	
	@ScriptVariable(injectionStrategy = InjectionStrategy.OPTIONAL)
	protected ValueMap inheritedPageProperties;

	@ValueMapValue(name = JcrConstants.JCR_TITLE)
	private String title;

	@ValueMapValue(name = "subText")
	private String subText;

	@ValueMapValue(name = "linkURL")
	private String linkURL;

	private String bgImagePath = "";

	private List<HeaderLinkBean> links;

	private String description;
	
	@ValueMapValue(name = "hideDesc")
	private boolean hideDescription;

	@Override
	public boolean isHideDescription() {
		return hideDescription;
	}

	@PostConstruct
	protected void initModel() {
		if (StringUtils.isBlank(title)) {
			title = StringUtils.defaultIfEmpty(currentPage.getPageTitle(),currentPage.getPageTitle());
		}

		if (StringUtils.isNotEmpty(linkURL)) {
			linkURL = ComponentUtils.getURL(request, pageManager, linkURL);
		} else {
			linkURL = null;
		}
		if(inheritedPageProperties!=null) {
			bgImagePath = inheritedPageProperties.get(PN_BG_IMAGE,String.class);
		}
		if(currentPage.getDescription() != null) {
			description =  currentPage.getDescription();
		}
		populateLinks();
	}

	@Override
	public String getText() {
		return title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getBgImagePath() {
		return bgImagePath;
	}

	@Override
	public String getLinkURL() {
		return linkURL;
	}

	@Override
	public List<HeaderLinkBean> getLinks() {
		return new ArrayList<>(links);
	}

	@Override
	public String getSubText() {
		return subText;
	}

	private void populateLinks() {
		links = new ArrayList<>();
		Resource linkNode = resource.getChild(NODE_LINKS); 
		if(linkNode!=null) {
			for(Resource link: linkNode.getChildren()) {
				links.add(new HeaderLinkBean() {
					private ValueMap properties = link.getValueMap();
					private String linkText = properties.get(PN_ITEM_TEXT,String.class);
					private String linkUrl = properties.get(PN_ITEM_TARGET,String.class);
					private String openInNewTab = properties.get(PN_ITEM_ACTION,String.class);

					@Override
					public String getLinkText() {
						return linkText;
					}
					@Override
					public String getLinkUrl() {
						return ComponentUtils.getURL(request, pageManager, linkUrl);
					}
					@Override
					public String getOpenInNewTab() {
						return openInNewTab;
					}

				});
			}
		}
	}

}
