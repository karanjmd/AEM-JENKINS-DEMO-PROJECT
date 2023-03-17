package com.adobe.aem.lacounty.dpss.core.services;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

/**
 * This AssetSupportService will be used to communicate with DAM and upload the
 * authored asset to the authored location.
 * 
 * @author abhashkumar.budayak
 *
 */
public interface AssetSupportService {
	/**
	 * The method returns a Boolean on upload of asset to the DAM on the specified
	 * location
	 * 
	 * @param resourceResolver
	 * @param resource
	 * @param damLocation
	 * @return Boolean
	 */
	Boolean uploadAssetToDam(ResourceResolver resourceResolver, Resource resource, Session session, String damLocation);

}
