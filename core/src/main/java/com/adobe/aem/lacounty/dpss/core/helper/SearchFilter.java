package com.adobe.aem.lacounty.dpss.core.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

public class SearchFilter {

	private String filterType;
	private List<SearchFilterName> filterList;

	public String getFilterType() {
		return filterType;
	}

	public List<SearchFilterName> getFilterList() {
		return new ArrayList<>(filterList);
	}

	public SearchFilter(Resource resource) {
		if (StringUtils.isNotBlank(resource.getValueMap().get("filterType", String.class))) {
			this.filterType = resource.getValueMap().get("filterType", String.class);
		}

		List<SearchFilterName> filterList = new ArrayList<>();
		Resource filtersResource = resource.getChild("filterList");
		if (filtersResource != null) {
			for (Resource filterNameBean : filtersResource.getChildren()) {
				filterList.add(new SearchFilterName(filterNameBean));
			}
			this.filterList = filterList;
		}
	}

}
