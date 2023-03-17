package com.adobe.aem.lacounty.dpss.core.models.impl;

import javax.annotation.PostConstruct;
import javax.jcr.Session;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.models.FormUploadModel;
import com.adobe.aem.lacounty.dpss.core.services.AssetSupportService;

/**
 * Defines the {@code FormUploadModel} Sling Model used for the
 * {@code dpss/components/content/form-upload} component.
 * 
 */
@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = FormUploadModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FormUploadModelImpl implements FormUploadModel {

	private static final Logger LOG = LoggerFactory.getLogger(FormUploadModelImpl.class);

	@SlingObject
	protected Resource resource;

	@Self
	private SlingHttpServletRequest request;

	@OSGiService
	private AssetSupportService assetSupportService;

	@ValueMapValue
	private String fileName;

	@ValueMapValue
	private String folderName;

	@ValueMapValue
	private String galleryLocation;
	
	private String title;

	@Override
	public String getFileName() {
		return fileName;
	}

	@Override
	public String getFolderName() {
		return folderName;
	}

	@Override
	public String getGalleryLocation() {
		return galleryLocation;
	}
	
	@Override
	public String getTitle() {
		if(StringUtils.isNotEmpty(title)) {
			title = title.replace("_", " ");
		}
		return title;
	}

	/**
	 * Initializes on load of the component sling model. Fetch the authored dam
	 * location and folder name and upload the image file.
	 */
	@PostConstruct
	protected void init() {
		String damLocation = galleryLocation + "/" + folderName;
		if(StringUtils.isNotEmpty(fileName)) {
			damLocation = damLocation + "/" + fileName;
		}
		LOG.debug("DAM Location with file path : " + damLocation);
		title = resource.getName();
		ResourceResolver resolver = request.getResourceResolver();
		Session session = resolver.adaptTo(Session.class);
		if (session != null) {
			Boolean isAssetUploaded = assetSupportService.uploadAssetToDam(resolver, resource, session, damLocation);
			LOG.debug("DAM Location with file path uploaded status : " + isAssetUploaded);
		}
	}

}