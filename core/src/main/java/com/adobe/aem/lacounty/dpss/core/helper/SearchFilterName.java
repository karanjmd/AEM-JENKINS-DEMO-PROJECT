package com.adobe.aem.lacounty.dpss.core.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

public class SearchFilterName {
	private String filterName;
	private String filterValue;
	private String filterId;

	public String getFilterName() {
		return filterName;
	}

	public String getFilterValue() {
		return filterValue;
	}
	
	public String getFilterId() {
		return filterId;
	}

	public SearchFilterName(Resource resource) {
		if (StringUtils.isNotBlank(resource.getValueMap().get("filterName", String.class))) {
			String filterName = resource.getValueMap().get("filterName", String.class);
			this.filterName = filterName;
			this.filterId = filterName.replace(" ", "-");
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("filterValue", String.class))) {
			this.filterValue = resource.getValueMap().get("filterValue", String.class);
		}
	}

}
