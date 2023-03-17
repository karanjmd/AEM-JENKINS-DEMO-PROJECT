package com.adobe.aem.lacounty.dpss.core.model.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.helper.SearchResultHelper;
import com.adobe.aem.lacounty.dpss.core.models.SearchResultModel;

@Model(adaptables = SlingHttpServletRequest.class, adapters = SearchResultModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchResultModelImpl implements SearchResultModel {

	private static final Logger LOG = LoggerFactory.getLogger(SearchResultModelImpl.class);
	static final String RESOURCE_TYPE = "dpss/components/content/searchresult";

	@Inject
	@SlingObject
	Resource componentResource;

	@SlingObject
	SlingHttpServletRequest slingHttpServletRequest;
	
	@SlingObject
	ResourceResolver resolver;

	@ValueMapValue
	private String searchPlaceHolder;
	
	@ValueMapValue
	private String resultpage;

	@ValueMapValue
	private String resultsSize;

	@ValueMapValue
	private String showResultCount;

	@ValueMapValue
	private String defaultSort;
	
	@ValueMapValue
	private String defaultSortDirection;
	
	@ValueMapValue
	private String loadMoreText;
	
	@ValueMapValue
	private String noResultText;
	
    @Override
	public String getSearchPlaceHolder() {
		return searchPlaceHolder;
	}

    @Override
	public String getResultpage() {
		return resultpage;
	}

    @Override
	public String getResultsSize() {
		return resultsSize;
	}

    @Override
	public String getShowResultCount() {
		return showResultCount;
	}

    @Override
	public String getDefaultSort() {
		return defaultSort;
	}

    @Override
	public String getDefaultSortDirection() {
		return defaultSortDirection;
	}

    @Override
	public String getLoadMoreText() {
		return loadMoreText;
	}

    @Override
	public String getNoResultText() {
		return noResultText;
	}

	@Override
	public List<SearchResultHelper> getSortOptions() {
		List<SearchResultHelper> searchOptionBean = new ArrayList<>();
		Resource searchOptionResource = componentResource.getChild("optionList");
		if (searchOptionResource != null) {
			for (Resource optionBean : searchOptionResource.getChildren()) {
				searchOptionBean.add(new SearchResultHelper(optionBean));
			}
		}
		return searchOptionBean;
	}

	@Override
	public List<SearchResultHelper> getSortDirections() {
		List<SearchResultHelper> searchOptionDirectionBean = new ArrayList<>();
		Resource searchOptionDirectionResource = componentResource.getChild("sortDirectionList");
		if (searchOptionDirectionResource != null) {
			for (Resource optionDirectionBean : searchOptionDirectionResource.getChildren()) {
				searchOptionDirectionBean.add(new SearchResultHelper(optionDirectionBean));
			}
		}
		return searchOptionDirectionBean;
	}
}
