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

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.lacounty.dpss.core.models.VideoModel;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = VideoModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VideoModelImpl implements VideoModel {
	private static final String PN_VIDEO_TITLE = "videoTitle";
	private static final String PN_VIDEO_CODE = "videoCode";

	@ValueMapValue(name = PN_VIDEO_TITLE)
	private String title;

	@ValueMapValue(name = PN_VIDEO_CODE)
	private String videoCode;

	@Override
	public String getVideoCode() {
		return videoCode;
	}

	@Override
	public String getTitle() {
		return title;
	}

}