
package com.adobe.aem.lacounty.dpss.core.models.impl;

import com.adobe.aem.lacounty.dpss.core.models.EpolicyApprovalsModel;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;


/**
 * Defines the {@code EventDetails} Sling Model used for the
 * {@code dpss/components/content/epolicyApprovals} component.
 * 
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = EpolicyApprovalsModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class EpolicyApprovalsModelImpl implements EpolicyApprovalsModel {

	private static final String CONTRIBUTORS = "contributors";
	private static final String REVIEWER = "reviewers";
	private static final String SECTION = "section";
	private static final String BRANCH = "branch";
	private static final String DIVISION = "division";
	private static final String BUREAU = "bureau";



	@SlingObject
	protected Resource currentResource;

	@Self
	private SlingHttpServletRequest request;

	@ValueMapValue(name = CONTRIBUTORS)
	private String contributors;
	
	@ValueMapValue(name = REVIEWER)
	private String reviewers;

	@ValueMapValue(name = SECTION)
	private String section;

	@ValueMapValue(name = BRANCH)
	private String branch;
	
	@ValueMapValue(name = DIVISION)
	private String division;

	@ValueMapValue(name = BUREAU)
	private String bureau;


	@ValueMapValue(name = "jcr:title")
	private String title;

	@PostConstruct
	protected void initModel() {
		Page currentPage = request.getResourceResolver().adaptTo(PageManager.class).getContainingPage(currentResource);
		if (StringUtils.isBlank(title) && null != currentPage) {
			title = StringUtils.defaultIfEmpty(currentPage.getPageTitle(), currentPage.getTitle());
		}

	}


	@Override
	public String getContributors() {
		return contributors;
	}

	@Override
	public String getReviewers() {
		return reviewers;
	}

	@Override
	public String getSection() {
		return section;
	}

	@Override
	public String getBranch() {
		return branch;
	}

	@Override
	public String getDivision() {
		return division;
	}

	@Override
	public String getBureau() {
		return bureau;
	}

	@Override
	public String getTitle() {
		return title;
	}
}