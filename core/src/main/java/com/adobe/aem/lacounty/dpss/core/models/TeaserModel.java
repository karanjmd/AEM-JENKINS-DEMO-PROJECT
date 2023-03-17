package com.adobe.aem.lacounty.dpss.core.models;



import com.adobe.cq.wcm.core.components.models.Teaser;
import java.util.Calendar;

public interface TeaserModel extends Teaser {
	
	public Calendar   getTeaserDate();
	
	public String getOpenInNewTab();

}
