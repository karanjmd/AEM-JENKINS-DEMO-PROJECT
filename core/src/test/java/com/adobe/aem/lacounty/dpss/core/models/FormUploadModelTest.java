package com.adobe.aem.lacounty.dpss.core.models;

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

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.adobe.aem.lacounty.dpss.core.services.AssetSupportService;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.AssetManager;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class FormUploadModelTest {
	private final AemContext aemContext = new AemContext(ResourceResolverType.JCR_MOCK);

	@Mock
	AssetSupportService assetSupportService;

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

	@Mock
	FormUploadModel formUpload;

	@BeforeEach
	void setUp() throws NoSuchFieldException, RepositoryException, IOException {
		MockitoAnnotations.initMocks(this);
		aemContext.addModelsForClasses(FormUploadModel.class);
		aemContext.registerService(AssetSupportService.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/formupload.json", "/content");
		aemContext.currentResource("/content/formupload");
		aemContext.request().adaptTo(FormUploadModel.class);
	}

	@Test
	void getFileName() throws RepositoryException {
		when(formUpload.getFileName()).thenReturn("assetupload.jpg");
		String expected = "assetupload.jpg";
		String actual = formUpload.getFileName();
		assertEquals(expected, actual);
	}

	@Test
	void getFolderName() {
		when(formUpload.getFolderName()).thenReturn("image-gallery");
		String expected = "image-gallery";
		String actual = formUpload.getFolderName();
		assertEquals(expected, actual);
	}

	@Test
	void getGalleryLocation() {
		when(formUpload.getGalleryLocation()).thenReturn("/content/dam/dpss");
		String expected = "/content/dam/dpss";
		String actual = formUpload.getGalleryLocation();
		assertEquals(expected, actual);
	}
	
	@Test
	void getTitle() {
		when(formUpload.getTitle()).thenReturn("form upload");
		String expected = "form upload";
		String actual = formUpload.getTitle();
		assertEquals(expected, actual);
	}

}
