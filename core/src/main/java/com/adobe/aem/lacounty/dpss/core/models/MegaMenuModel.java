package com.adobe.aem.lacounty.dpss.core.models;

import java.util.List;

import com.adobe.aem.lacounty.dpss.core.helper.MegaMenuModelHelper;

public interface MegaMenuModel {
	String getLogo();

	String getLogoLink();

	String getLogoHyperLink();

	String getLogoAltTxt();

	String getResultPage();

	String getResultLink();

	List<MegaMenuModelHelper> getTitleLinks();

	List<MegaMenuModelHelper> getSearch();
}
