package com.adobe.aem.lacounty.dpss.core.services.impl;

import java.io.InputStream;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.services.AssetSupportService;
import com.day.cq.dam.api.AssetManager;

@Component
public class AssetSupportServiceImpl implements AssetSupportService {
	private static final Logger LOG = LoggerFactory.getLogger(AssetSupportServiceImpl.class);

	private static final String FILE = "file";
	private static final String DUMMY_PATH = "dummy";

	/**
	 * This method is responsible for upload the files to the specified location to the DAM
	 * @param ResourceResolver
	 * @param Resource
	 * @param session
	 * @param damLocation
	 * @return Boolean
	 */
	@Override
	public Boolean uploadAssetToDam(ResourceResolver resourceResolver, Resource resource, Session session,
			String damLocation) {
		Boolean assetCreationStatus = Boolean.FALSE;

		try {
			InputStream is = null;
			Node node = session.getNode(resource.getPath());
			LOG.debug("Asset support service upload start for resource node path : " + node.getPath()
					+ "with node name: " + node.getName());
			NodeIterator nodeItr = node.getNodes();
			while (nodeItr.hasNext()) {
				Node childNode = nodeItr.nextNode();
				String childNodeName = childNode.getName();
				if (childNodeName.equals(FILE)) {
					Node subChildNode = childNode.getNode(JcrConstants.JCR_CONTENT);
					is = subChildNode.getProperty(JcrConstants.JCR_DATA).getBinary().getStream();
					String mimeType = subChildNode.getProperty(JcrConstants.JCR_MIMETYPE).getValue().getString();
					AssetManager manager = resourceResolver.adaptTo(AssetManager.class);
					if (null != manager) {
						manager.createAsset(damLocation, is, mimeType, true);
					}
					assetCreationStatus = Boolean.TRUE;
				}
				if (damLocation.equals(DUMMY_PATH)) {
					break;
				}
			}
		} catch (RepositoryException e) {
			assetCreationStatus = false;
			LOG.error("RepositoryException while trasforming the asset to dam.", e);
		}
		return assetCreationStatus;
	}
}
