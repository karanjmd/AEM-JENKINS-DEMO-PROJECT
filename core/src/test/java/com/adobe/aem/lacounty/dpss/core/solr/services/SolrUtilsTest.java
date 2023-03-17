package com.adobe.aem.lacounty.dpss.core.solr.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.lacounty.dpss.core.solr.utils.SolrUtils;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class })
public class SolrUtilsTest {

	private AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() throws NoSuchFieldException {
		aemContext.registerService(SolrUtils.class);
	}

	@Test
	void replaceHTMLCharTest() {
		assertEquals("solr search", SolrUtils.replaceHTMLChar("<p>solr search</p>"));
		assertEquals("solrsearch", SolrUtils.replaceHTMLChar("&nbsp;solrsearch"));
	}

	@Test
	void isImageUrlTest() {
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.jpeg"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.ai"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.bmp"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.gif"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.ico"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.jpg"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.ps"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.psd"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.svg"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.tif"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.tiff"));
		assertEquals(Boolean.TRUE, SolrUtils.isImageUrl("testimage.png"));
	}

	@Test
	void isInvalidImageUrlTest() {
		assertEquals(Boolean.FALSE, SolrUtils.isImageUrl("testimage.txt"));
		assertEquals(Boolean.FALSE, SolrUtils.isImageUrl("testimage.pdf"));
		assertEquals(Boolean.FALSE, SolrUtils.isImageUrl("testimage.java"));
		assertEquals(Boolean.FALSE, SolrUtils.isImageUrl("testimage.html"));
	}

	@Test
	void isEmptyImageUrlTest() {
		assertEquals(Boolean.FALSE, SolrUtils.isImageUrl(StringUtils.EMPTY));
	}

	@Test
	void isBooleanTest() {
		assertEquals(Boolean.TRUE, SolrUtils.isBoolean("FALSE"));
		assertEquals(Boolean.TRUE, SolrUtils.isBoolean("false"));
		assertEquals(Boolean.TRUE, SolrUtils.isBoolean("TRUE"));
		assertEquals(Boolean.TRUE, SolrUtils.isBoolean("true"));
	}

	@Test
	void isBooleanEmptyTest() {
		assertEquals(Boolean.FALSE, SolrUtils.isBoolean(StringUtils.EMPTY));
	}

	@Test
	void isBooleanInvalidInputTest() {
		assertEquals(Boolean.FALSE, SolrUtils.isBoolean("test"));
	}
}
