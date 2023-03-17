package com.adobe.aem.lacounty.dpss.core.models;

import java.util.List;

import com.adobe.aem.lacounty.dpss.core.helper.SearchFilter;

public interface SearchFilterModel {
	public String getFilterSection();

	public String getResetLabel();

	public List<SearchFilter> getFilters();

	public String getExtTitle();

	public String getExtLink();

	public String getExtSearchTitle();

	public String getExtSearchLink();
}
