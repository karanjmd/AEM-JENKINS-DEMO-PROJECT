package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.lacounty.dpss.core.models.FooterModel;
import com.adobe.aem.lacounty.dpss.core.models.impl.FooterModelImpl;
import com.adobe.aem.lacounty.dpss.core.helper.FooterHelper;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class FooterModelImplTest {

	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(FooterModelImpl.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/footer.json", "/content");

	}

	@Test
	void getTitle() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "Department of Public Social Services";
		String actual = megaMenu.getTitle();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getAddress() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "12860 Crossroads Parkway South City of Industry, CA. 91746";
		String actual = megaMenu.getAddress();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getPhone() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "(562) 908-8400";
		String actual = megaMenu.getPhone();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getEmail() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "webmaster@dpss.gov";
		String actual = megaMenu.getEmail();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getTagline() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "Enriching Lives through effective and caring service";
		String actual = megaMenu.getTagline();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getCopyrights() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "© 2021 MY DPSS";
		String actual = megaMenu.getCopyrights();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getReserved() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "All rights reserved";
		String actual = megaMenu.getReserved();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getLogo() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "/content/dam/dpss/seal@2x.png";
		String actual = megaMenu.getLogo();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getHeading() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "Contacts";
		String actual = megaMenu.getHeading();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getHeading2() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "Bureaus";
		String actual = megaMenu.getHeading2();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getHeading3() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "External Links";
		String actual = megaMenu.getHeading3();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getHeading4() {
		// aemContext.currentResource("/content/mega");
		aemContext.currentResource("/content/footer");
		FooterModel megaMenu = aemContext.request().adaptTo(FooterModel.class);
		String expected = "Policy";
		String actual = megaMenu.getHeading4();
		// String actual = megaMenu.getLogoLink();
		assertEquals(expected, actual);

	}

	@Test
	void getBureausLinks() {
		aemContext.currentResource("/content/footer");
		int expected = 7;
		String bureautext = "Administrative Services (BAS)";
		String burelink = "https://www.google.com";
		String bopenInTab = "_blank";
		FooterModel bureaumodel = aemContext.request().adaptTo(FooterModel.class);
		List<FooterHelper> bureaulink = bureaumodel.getBureausLinks();
		assertEquals(expected, bureaumodel.getBureausLinks().size());
		String actualtext = bureaulink.get(0).getBlinktext();
		String actuallink = bureaulink.get(0).getBlinkURL();
		String actualtab = bureaulink.get(0).getBopenInTab();
		assertEquals(bureautext, actualtext);
		assertEquals(burelink, actuallink);
		assertEquals(bopenInTab, actualtab);

	}

	@Test
	void getExternalLinks() {
		aemContext.currentResource("/content/footer");
		int expected = 6;
		String exttext = "211 LA";
		String extlinkt = "https://www.google.com";
		String openInTab = "_blank";
		FooterModel footermodel = aemContext.request().adaptTo(FooterModel.class);
		List<FooterHelper> externallink = footermodel.getExternalLinks();
		assertEquals(expected, footermodel.getExternalLinks().size());
		String actualtext = externallink.get(0).getLinkText();
		String actuallink = externallink.get(0).getLinkURL();
		String actualtab = externallink.get(0).getOpenInTab();
		assertEquals(exttext, actualtext);
		assertEquals(extlinkt, actuallink);
		assertEquals(openInTab, actualtab);
	}

	@Test
	void getPolicyLinks() {
		aemContext.currentResource("/content/footer");
		int expected = 3;
		String plinktext = "Accessibilities";
		String plinkurl = "https://www.google.com";
		String popenInTab = "_blank";
		FooterModel footermodel = aemContext.request().adaptTo(FooterModel.class);
		List<FooterHelper> policylink = footermodel.getPolicyLinks();
		assertEquals(expected, footermodel.getPolicyLinks().size());
		String actualtext = policylink.get(0).getPlinktext();
		String actuallink = policylink.get(0).getPlinkURL();
		String actualtab = policylink.get(0).getPopenInTab();
		assertEquals(plinktext, actualtext);
		assertEquals(plinkurl, actuallink);
		assertEquals(popenInTab, actualtab);

	}
	@Test
	void getSocialLinks() {
		aemContext.currentResource("/content/footer");
		int expected = 2;
		String slinkicon = "fa fa-instagram";
		String slinkURL = "www.instagram.com";
        String sopenInTab = "_blank";
		FooterModel footermodel = aemContext.request().adaptTo(FooterModel.class);
		List<FooterHelper> sociallink = footermodel.getSocialLinks();
		assertEquals(expected, footermodel.getSocialLinks().size());
		String actualtext = sociallink.get(0).getSlinkicon();
		String actuallink = sociallink.get(0).getSlinkURL();
		String actualtab = sociallink.get(0).getSopenInTab();
		assertEquals(slinkicon, actualtext);
		assertEquals(slinkURL, actuallink);
		assertEquals(sopenInTab, actualtab);

	}
	@Test
	void getGenericText() {
		aemContext.currentResource("/content/footer");
		int expected = 2;
		String gtext = "This is dpss text";
    	FooterModel footermodel = aemContext.request().adaptTo(FooterModel.class);
		List<FooterHelper> generictext = footermodel.getGenericText();
		assertEquals(expected, footermodel.getGenericText().size());
		String actualtext = generictext.get(0).getGtext();
		assertEquals(gtext, actualtext);		

	}



}
