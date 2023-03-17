package com.adobe.aem.lacounty.dpss.core.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;

public class SearchResultHelper {

	private String option;
	private String optionValue;
	private String sortDirection;
	private String sortDirectionValue;

	public String getOption() {
		return option;
	}

	public String getSortDirection() {
		return sortDirection;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public String getSortDirectionValue() {
		return sortDirectionValue;
	}

	public SearchResultHelper(Resource resource) {

		if (StringUtils.isNotBlank(resource.getValueMap().get("option", String.class))) {
			this.option = resource.getValueMap().get("option", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("optionValue", String.class))) {
			this.optionValue = resource.getValueMap().get("optionValue", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("sortDirection", String.class))) {
			this.sortDirection = resource.getValueMap().get("sortDirection", String.class);
		}
		if (StringUtils.isNotBlank(resource.getValueMap().get("sortDirectionValue", String.class))) {
			this.sortDirectionValue = resource.getValueMap().get("sortDirectionValue", String.class);
		}
	}
}
