package com.adobe.aem.lacounty.dpss.core.model.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;

import com.adobe.aem.lacounty.dpss.core.helper.SearchFilter;
import com.adobe.aem.lacounty.dpss.core.models.SearchFilterModel;

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, adapters = SearchFilterModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchFilterModelImpl implements SearchFilterModel {

	static final String RESOURCE_TYPE = "dpss/components/content/searchfilter";

	final String DEFAULT_TITLE="Search LACounty.GOV";

	final String DEFAULT_URL = "https://lacounty.gov/";

	@Inject
	Resource resource;

	@Inject
	@Via("resource")
	private String filterSection;

	@Inject
	@Via("resource")
	private String	resetLabel;

	@Inject
	@Via("resource")
	private String	extSearchTitle;

	@Inject
	@Via("resource")
	private String	extSearchLink;


	@Override
	public String getFilterSection() {
		return filterSection;
	}

	@Override
	public String getResetLabel() {
		return resetLabel;
	}

	@Override
	public List<SearchFilter> getFilters() {
		List<SearchFilter> filterList = new ArrayList<>();

		Resource filtersResource = resource.getChild("filters");
		if (filtersResource != null) {
			for (Resource filterBean : filtersResource.getChildren()) {
				filterList.add(new SearchFilter(filterBean));
			}
		}
		return filterList;
	}

	@Override
	public String getExtTitle() {
		return extSearchTitle;
	}

	@Override
	public String getExtLink() {
		return extSearchLink;
	}

	@Override
	public String getExtSearchTitle() {
		String title= getExtTitle();
		if (title != null){
			return title;
		}
		return DEFAULT_TITLE;
	}

	@Override
	public String getExtSearchLink() {
		String link= getExtLink();
		if (link != null){
			return link;
		}
		return DEFAULT_URL;
	}


}
