package com.adobe.aem.lacounty.dpss.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.helper.MegaMenuModelHelper;
import com.adobe.aem.lacounty.dpss.core.models.MegaMenuModel;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = SlingHttpServletRequest.class, adapters = MegaMenuModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MegaMenuModelImpl implements MegaMenuModel {

	private static final Logger LOG = LoggerFactory.getLogger(MegaMenuModelImpl.class);
	static final String RESOURCE_TYPE = "dpss/components/content/megamenu";
	@SlingObject
	Resource componentResource;

	@ValueMapValue
	private String logo;

	@ValueMapValue
	private String resultpage;

	@ValueMapValue
	@Default(values = "DPSS logo")
	private String logoAltTxt;

	@ValueMapValue
	private String logolink;

	@SlingObject
	SlingHttpServletRequest slingHttpServletRequest;

	@SlingObject
	ResourceResolver resolver;

	private String logoHyperLink;

	private String resultLink;

	@Override
	public List<MegaMenuModelHelper> getTitleLinks() {

		List<MegaMenuModelHelper> megamenuDetailsBean = new ArrayList<>();

		Resource megamenuDetailBean = componentResource.getChild("titleandlinks");
		if (megamenuDetailBean != null) {
			for (Resource megamenuBean : megamenuDetailBean.getChildren()) {
				MegaMenuModelHelper megaMenu = new MegaMenuModelHelper(megamenuBean);
				String link = megaMenu.getLinks();
				Boolean isNavHidden = false;
				if (StringUtils.isNoneEmpty(link) && link.startsWith("/content")) {
					ResourceResolver resourceResolver = componentResource.getResourceResolver();
					PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
					if (pageManager != null) {
						Page page = pageManager.getPage(link.replace(".html", ""));
						if (page != null) {
							isNavHidden = page.isHideInNav();
						}
					}
					
				}
				if (!isNavHidden) {
					megamenuDetailsBean.add(new MegaMenuModelHelper(megamenuBean));
				}

			}
		}

		return megamenuDetailsBean;
	}

	@Override
	public String getLogo() {

		return logo;
	}

	@Override
	public String getLogoLink() {

		return logolink;
	}

	@PostConstruct
	protected void init() throws Exception {
		resultLink = resultpage + ".html";
		logoHyperLink = logolink + ".html";

	}

	@Override
	public String getLogoHyperLink() {
		return logoHyperLink;
	}

	@Override
	public String getLogoAltTxt() {

		return logoAltTxt;
	}

	@Override
	public List<MegaMenuModelHelper> getSearch() {
		List<MegaMenuModelHelper> searchFiltersBean = new ArrayList<>();

		Resource searchFilterBean = componentResource.getChild("searchdetails");
		if (searchFilterBean != null) {
			for (Resource footerBean : searchFilterBean.getChildren()) {
				searchFiltersBean.add(new MegaMenuModelHelper(footerBean));
			}
		}

		return searchFiltersBean;
	}

	@Override
	public String getResultPage() {
		return resultpage;
	}

	@Override
	public String getResultLink() {
		return resultLink;
	}

}
