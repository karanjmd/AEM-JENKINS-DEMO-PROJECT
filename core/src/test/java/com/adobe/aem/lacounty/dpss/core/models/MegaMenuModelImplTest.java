package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adobe.aem.lacounty.dpss.core.helper.MegaMenuModelHelper;
import com.adobe.aem.lacounty.dpss.core.models.MegaMenuModel;
import com.adobe.aem.lacounty.dpss.core.models.impl.MegaMenuModelImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
public class MegaMenuModelImplTest {
	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(MegaMenuModelImpl.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/megamenu.json", "/content");
	}

	@Test
	void testGetLogoLink() {
		aemContext.currentResource("/content/mega");
		MegaMenuModel megaMenu = aemContext.request().adaptTo(MegaMenuModel.class);
		String expected = "/content/dpss/en/Homepage";
		String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void testGetLogo() {
		aemContext.currentResource("/content/mega");
		MegaMenuModel megaMenu = aemContext.request().adaptTo(MegaMenuModel.class);
		String expected = "/content/dam/dpss/dpss-logo.svg";
		String actual = megaMenu.getLogo();
		assertEquals(expected, actual);

	}
	
	@Test
	void testGetALtText() {
		aemContext.currentResource("/content/mega");
		MegaMenuModel megaMenu = aemContext.request().adaptTo(MegaMenuModel.class);
		String expected = "DPSS LOGO";
		String actual = megaMenu.getLogoAltTxt();
		assertEquals(expected, actual);
	}

	@Test
	void testGetResultPage() {
		aemContext.currentResource("/content/mega");
		MegaMenuModel megaMenu = aemContext.request().adaptTo(MegaMenuModel.class);
		String expected = "/content/dpss/en/Homepage/ResultPage";
		String actual = megaMenu.getResultPage();
		assertEquals(expected, actual);

	}

	@Test
	void testGetSearch() {
		aemContext.currentResource("/content/mega");
		MegaMenuModel megaMenu = aemContext.request().adaptTo(MegaMenuModel.class);
		final int exp = 6;
		int actual = megaMenu.getSearch().size();
		assertEquals(exp, actual);
		assertEquals("My DPSS", megaMenu.getSearch().get(0).getFilter());

	}

	@Test
	void testTitleLinks() {
		aemContext.currentResource("/content/mega");
		MegaMenuModel megaMenu = aemContext.request().adaptTo(MegaMenuModel.class);
		List<MegaMenuModelHelper> bureaulink = megaMenu.getTitleLinks();
		final int exp = 5;
		String one = "Applications";
		String two = "/content/dpss/en/Homepage/sites/application.html";
		String three = "_blank";
		int actual = megaMenu.getTitleLinks().size();
		assertEquals(exp, actual);
		assertEquals(one, bureaulink.get(0).getTitle());
		assertEquals(two, bureaulink.get(0).getLinks());
		assertEquals(three, bureaulink.get(0).getOpenInTab());

	}

}
