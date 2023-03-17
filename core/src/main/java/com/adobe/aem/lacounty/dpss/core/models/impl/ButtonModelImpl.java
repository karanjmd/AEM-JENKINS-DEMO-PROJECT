/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.lacounty.dpss.core.models.impl;


import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.lacounty.dpss.core.models.ButtonModel;
import com.adobe.aem.lacounty.dpss.core.utils.ComponentUtils;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = {Resource.class,SlingHttpServletRequest.class}, adapters = ButtonModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ButtonModelImpl implements ButtonModel{
	private static final String PN_LINK = "link";
	private static final String PN_TAB = "openInTab";
	
	
	@ScriptVariable
	SlingHttpServletRequest request;
	
	@Inject
	@SlingObject
	Resource componentResource; 
		
	@ScriptVariable
	PageManager pageManager;
	
	@ValueMapValue(name = PN_LINK)
	private String link;

	@ValueMapValue(name = PN_TAB)
	private String openInNewTab;
	

	@ValueMapValue(name = JcrConstants.JCR_TITLE)
	private String text;
	
	@Override
	public String getLink() {
		return ComponentUtils.getURL(request, pageManager,link);
	}
	
	@Override
	public String getText() {
		return text;
	}
	
	@Override
	public String getOpenInNewTab() {
		if (StringUtils.isNotBlank(componentResource.getValueMap().get("openInTab", String.class))) {
		Boolean tabValue = componentResource.getValueMap().get("openInTab", Boolean.class);	
		this.openInNewTab=ComponentUtils.processOpenInTabValue(tabValue);
		}else {
			this.openInNewTab= "_self";
		}
		return openInNewTab;
	}
	
}