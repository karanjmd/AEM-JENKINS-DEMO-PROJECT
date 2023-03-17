package com.adobe.aem.lacounty.dpss.core.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;

import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adobe.aem.lacounty.dpss.core.services.impl.AssetSupportServiceImpl;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
public class AssetSupportServiceTest {
	private static final String DUMMY_PATH = "dummy";
	private AssetSupportServiceImpl assetSupportService = new AssetSupportServiceImpl();

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Resource resource;

	@Mock
	Session session;

	@Mock
	Node node;

	@Mock
	NodeIterator nodeItr;

	@Mock
	InputStream inputStream;

	@Mock
	Property property;

	@Mock
	Binary binary;

	@Mock
	Value value;

	@Mock
	AssetManager assetManager;

	@Mock
	Asset asset;

	@BeforeEach
	void setUp() throws NoSuchFieldException, RepositoryException, IOException {

		MockitoAnnotations.initMocks(this);

		when(resource.getPath()).thenReturn(
				"/content/dpss/en/homepage/detail/jcr:content/root/responsivegrid/container/layoutcontainer/form_upload");
		when(session.getNode(resource.getPath())).thenReturn(node);
		when(node.getNodes()).thenReturn(nodeItr);
		when(nodeItr.hasNext()).thenReturn(Boolean.TRUE);
		when(nodeItr.nextNode()).thenReturn(node);
		when(node.getName()).thenReturn("file");
		when(node.getNode(JcrConstants.JCR_CONTENT)).thenReturn(node);
		when(node.getProperty(JcrConstants.JCR_DATA)).thenReturn(property);
		when(property.getBinary()).thenReturn(binary);
		when(binary.getStream()).thenReturn(inputStream);
		when(node.getProperty(JcrConstants.JCR_MIMETYPE)).thenReturn(property);
		when(property.getValue()).thenReturn(value);
		when(value.getString()).thenReturn("image/jpeg");
		when(resourceResolver.adaptTo(AssetManager.class)).thenReturn(assetManager);
	}

	@Test
	void uploadAssetToDamTest() throws RepositoryException {
		Boolean isAssetCreated = assetSupportService.uploadAssetToDam(resourceResolver, resource, session, DUMMY_PATH);
		assertEquals(isAssetCreated, Boolean.TRUE);
	}

}
