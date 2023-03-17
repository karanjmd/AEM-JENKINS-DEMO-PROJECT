package com.adobe.aem.lacounty.dpss.core.models.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.helper.FooterHelper;
import com.adobe.aem.lacounty.dpss.core.models.FooterModel;

@Model(adaptables = SlingHttpServletRequest.class, adapters = FooterModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FooterModelImpl implements FooterModel {

	private static final Logger LOG = LoggerFactory.getLogger(FooterModelImpl.class);
	static final String RESOURCE_TYPE = "dpss/components/structure/footer";

	@SlingObject
	@Inject
	Resource componentResource;

	@ValueMapValue
	@Default(values = "Department of Public Social Services")
	private String title;

	@ValueMapValue
	private String address;

	@ValueMapValue
	private String phone;

	@ValueMapValue
	private String email;

	@ValueMapValue
	private String tagline;

	@ValueMapValue
	private String copyrights;

	@ValueMapValue
	private String reserved;

	@ValueMapValue
	private String logo;

	@ValueMapValue
	private String heading;

	@ValueMapValue
	private String heading2;

	@ValueMapValue
	private String heading3;

	@ValueMapValue
	private String heading4;

	public String getTitle() {
		return title;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getTagline() {
		return tagline;
	}

	public String getCopyrights() {
		return copyrights;
	}

	public String getReserved() {
		return reserved;
	}

	public String getLogo() {
		return logo;
	}

	@Override
	public List<FooterHelper> getBureausLinks() {
		final List<FooterHelper> footerDetailsBean = new ArrayList<>();

		Resource footerDetailBean = componentResource.getChild("bureauslinkdetails");
		if (footerDetailBean != null) {

			for (Resource footerBean : footerDetailBean.getChildren()) {
				footerDetailsBean.add(new FooterHelper(footerBean));
			}

		}

		return footerDetailsBean;
	}

	@Override
	public List<FooterHelper> getExternalLinks() {
		List<FooterHelper> footerDetailsBean = new ArrayList<>();

		Resource footerDetailBean = componentResource.getChild("externallinkdetails");
		if (footerDetailBean != null) {
			for (Resource footerBean : footerDetailBean.getChildren()) {
				footerDetailsBean.add(new FooterHelper(footerBean));
			}
		}

		return footerDetailsBean;
	}

	@Override
	public List<FooterHelper> getPolicyLinks() {
		List<FooterHelper> footerDetailsBean = new ArrayList<>();

		Resource footerDetailBean = componentResource.getChild("policylinkdetails");
		if (footerDetailBean != null) {
			for (Resource footerBean : footerDetailBean.getChildren()) {
				footerDetailsBean.add(new FooterHelper(footerBean));
			}
		}

		return footerDetailsBean;
	}

	@Override
	public List<FooterHelper> getSocialLinks() {
		List<FooterHelper> footerDetailsBean = new ArrayList<>();

		Resource footerDetailBean = componentResource.getChild("sociallinkdetails");
		if (footerDetailBean != null) {
			for (Resource footerBean : footerDetailBean.getChildren()) {
				footerDetailsBean.add(new FooterHelper(footerBean));
			}
		}

		return footerDetailsBean;
	}
	
	@Override
	public List<FooterHelper> getGenericText() {
		List<FooterHelper> textDetailsBean = new ArrayList<>();

		Resource textDetailBean = componentResource.getChild("generictext");
		if (textDetailBean != null) {
			for (Resource textBean : textDetailBean.getChildren()) {
				textDetailsBean.add(new FooterHelper(textBean));
			}
		}
		return textDetailsBean;
	}

	@Override
	public String getHeading() {

		return heading;
	}

	@Override
	public String getHeading2() {

		return heading2;
	}

	@Override
	public String getHeading3() {

		return heading3;
	}

	@Override
	public String getHeading4() {

		return heading4;
	}

	

}
