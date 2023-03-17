package com.adobe.aem.lacounty.dpss.core.models;

import java.util.List;

import com.adobe.aem.lacounty.dpss.core.helper.SearchResultHelper;

public interface SearchResultModel {
	public String getSearchPlaceHolder();

	public String getResultpage();

	public String getResultsSize();

	public String getShowResultCount();

	public String getDefaultSort();

	public String getDefaultSortDirection();

	public String getLoadMoreText();

	public String getNoResultText();
	
	public List<SearchResultHelper> getSortOptions();
	
	public List<SearchResultHelper> getSortDirections();
}
