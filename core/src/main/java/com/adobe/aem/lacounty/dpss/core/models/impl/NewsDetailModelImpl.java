package com.adobe.aem.lacounty.dpss.core.models.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.lacounty.dpss.core.models.NewsDetailModel;
import com.adobe.aem.lacounty.dpss.core.utils.ComponentUtils;
import com.day.cq.wcm.api.Page;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = NewsDetailModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsDetailModelImpl implements NewsDetailModel {

	private static final String MOBILE_NEWS_TRANSFORM_CONFIGNAME = "mobileTransformedNDImage";
	private static final String TABLET_NEWS_TRANSFORM_CONFIGNAME = "tabTransformedNDImage";
	private static final String PN_IMAGE_ALIGN = "imageAlign";
	private static final String PN_TITLE = "newsTitle";
	private static final String PN_ARTICLE_IMAGE = "articleImage";

	@ScriptVariable
	private Page currentPage;

	@ValueMapValue(name = PN_TITLE)
	private String newsTitle;

	@ValueMapValue(name = PN_IMAGE_ALIGN)
	private String imageAlign;

	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String articleDesc;

	@ValueMapValue(name = PN_ARTICLE_IMAGE)
	private String articleImage;

	@ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
	private String imageAltText;

	private String articleMobileImage;
	private String articleTabletImage;

	@PostConstruct
	protected void initModel() {
		if (StringUtils.isNotEmpty(articleImage)) {
			articleMobileImage = ComponentUtils.getTransformImgPath(articleImage, MOBILE_NEWS_TRANSFORM_CONFIGNAME);
			articleTabletImage = ComponentUtils.getTransformImgPath(articleImage, TABLET_NEWS_TRANSFORM_CONFIGNAME);
		}
		if (StringUtils.isBlank(newsTitle)) {
			newsTitle = StringUtils.defaultIfEmpty(currentPage.getPageTitle(), currentPage.getTitle());
		}
	}

	@Override
	public String getNewsTitle() {
		return newsTitle;
	}

	@Override
	public String getArticleDesc() {
		return articleDesc;
	}

	@Override
	public String getArticleImage() {
		return articleImage;
	}

	@Override
	public String getImageAltText() {
		return imageAltText;
	}

	@Override
	public String getArticleMobileImage() {
		return articleMobileImage;
	}

	@Override
	public String getArticleTabletImage() {
		return articleTabletImage;
	}

	@Override
	public String getImageAlign() {
		return imageAlign;
	}
}