package com.adobe.aem.lacounty.dpss.core.models.impl;

import javax.annotation.PostConstruct;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.models.CardModel;
import com.adobe.aem.lacounty.dpss.core.utils.ComponentUtils;

@Model(adaptables = { Resource.class, SlingHttpServletRequest.class }, adapters = CardModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CardModelImpl implements CardModel {

	private static final Logger LOG = LoggerFactory.getLogger(CardModel.class);
	private static final String MOBILE_CARD_TRANSFORM_CONFIGNAME = "mobileTransformedCardImage";
	private static final String TABLET_CARD_TRANSFORM_CONFIGNAME = "tabTransformedCardImage";

	@ScriptVariable
	private Resource resource;

	@ValueMapValue(name = "areaLandmark")
	private String areaLandmark;

	@ValueMapValue(name = "cardImage")
	private String cardImage;

	@ValueMapValue(name = "cardImageAltTxt")
	private String cardImageAltTxt;

	@ValueMapValue(name = "expandLabel")
	@Default(values = "More")
	private String expandLabel;

	@ValueMapValue(name = "collapseLabel")
	@Default(values = "Less")
	private String collapseLabel;

	@ValueMapValue(name = "description")
	private String description;

	@ValueMapValue(name = "cardTitle")
	private String cardTitle;

	@ValueMapValue(name = "cardSubtitle")
	private String cardSubtitle;

	@ValueMapValue(name = "hideMore")
	private boolean hideDescription;

	public boolean isHideDescription() {
		return hideDescription;
	}

	private String cardMobileImage;
	private String cardTabletImage;

	@PostConstruct
	protected void initModel() {
		if (StringUtils.isNoneEmpty(cardImage)) {
			LOG.info("Executing Card Model");
			this.cardMobileImage = ComponentUtils.getTransformImgPath(cardImage, MOBILE_CARD_TRANSFORM_CONFIGNAME);
			this.cardTabletImage = ComponentUtils.getTransformImgPath(cardImage, TABLET_CARD_TRANSFORM_CONFIGNAME);
			LOG.info("Card Model execution sucessfull");
		}
	}

	@Override
	public String getAreaLandmark() {
		String compId = StringUtils.EMPTY;
		try {
			Node node = resource.adaptTo(Node.class);
			if (null != node) {
				compId = node.getName() + "-" + String.valueOf(Math.abs(resource.getPath().hashCode() - 1)).toString();
				node.setProperty("accessibilityLabel", compId);
				node.getSession().save();
				this.areaLandmark = compId;
			}
		} catch (RepositoryException e) {
			LOG.error("Exception in card model ", e);
		}
		return areaLandmark;
	}

	@Override
	public String getCardImage() {
		return cardImage;
	}

	@Override
	public String getCardImageAltTxt() {
		return cardImageAltTxt;
	}

	@Override
	public String getExpandLabel() {
		return expandLabel;
	}

	@Override
	public String getCollapseLabel() {
		return collapseLabel;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getCardTitle() {
		return cardTitle;
	}

	@Override
	public String getCardSubtitle() {
		return cardSubtitle;
	}

	@Override
	public String getCardMobileImage() {
		return cardMobileImage;
	}

	@Override
	public String getCardTabletImage() {
		return cardTabletImage;
	}

}