package com.adobe.aem.lacounty.dpss.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.lacounty.dpss.core.constants.Constants;
import com.adobe.aem.lacounty.dpss.core.models.ErrorPageHandlerModel;
import com.adobe.aem.lacounty.dpss.core.services.ErrorPageHandlerService;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = ErrorPageHandlerModel.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ErrorPageHandlerModelImpl implements ErrorPageHandlerModel{
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorPageHandlerModelImpl.class);
	private String errorPage;

	@Inject
	@Default(values = { Constants.DEFAULT_STATUS_CODE })
	private String errorCode;

	@Self
	private SlingHttpServletRequest slingRequest;

	@OSGiService
	ErrorPageHandlerService config;

	/**
	 * The current request.
	 */
	@ScriptVariable
	private SlingHttpServletResponse response;

	@PostConstruct
	protected void init() {

		String requestURI = this.slingRequest.getRequestPathInfo().getResourcePath();
		response.setStatus(Integer.parseInt(errorCode));
		response.setContentType("text/html");
		response.setCharacterEncoding(Constants.UTF_8);
		this.errorPage = getDefaultErrorPage(requestURI, errorCode);
		if (StringUtils.isNotBlank(requestURI)) {
			this.errorPage = getErrorPageFromRequestedUrl(this.errorCode, requestURI);
			LOGGER.debug("Page path - init method {}", this.errorPage);
		}

	}

	/**
	 * Returns the error page from request URL
	 * 
	 * @param errorCode
	 * @param requestURI
	 * @return
	 */
	private String getErrorPageFromRequestedUrl(String errorCode, String requestURI) {
		LOGGER.info("error code is" + errorCode);
		LOGGER.info("requestURI  is" + requestURI);
		Page resolvedPage = getPageFromPath(requestURI);
		if (resolvedPage != null)
			return getErrorPathFromPage(errorCode, resolvedPage);
		return this.errorPage;
	}

	/**
	 * Returns the page object from the request URL.
	 * 
	 * @param requestURI
	 * @return
	 */
	private Page getPageFromPath(String requestURI) {
		final PageManager pageManager = this.slingRequest.getResourceResolver().adaptTo(PageManager.class);
		while (requestURI.contains("/")) {
			if (pageManager == null) {
				LOGGER.debug("Pagemanager is null");
				return null;
			}
			final Page page = pageManager.getContainingPage(requestURI);
			if (page == null) {
				requestURI = requestURI.substring(0, requestURI.lastIndexOf("/"));
				LOGGER.debug("Request URI is {}", requestURI);
				continue;
			}
			LOGGER.debug("Page path is {}", page.getPath());
			return page;
		}
		return null;
	}

	/**
	 * Returns the error page path.
	 * 
	 * @param errorCode
	 * @param resolvedPage
	 * @return
	 */
	private String getErrorPathFromPage(String errorCode, Page resolvedPage) {
		if (resolvedPage.hasChild(Constants.ERROR_PAGE_NAME))
			return new StringBuilder().append(resolvedPage.getPath()).append("/error/").append(errorCode).toString();
		if (resolvedPage.getParent() != null && resolvedPage.getDepth() >= 2)
			return getErrorPathFromPage(errorCode, resolvedPage.getParent());
		return this.errorPage;
	}

	/**
	 * @return the errorPage
	 */
	@Override
	public String getErrorPage() {
		return this.errorPage;
	}

	/**
	 * Returns the site specific default error page.
	 * 
	 * @param path
	 * @return
	 */
	@SuppressWarnings("null")
	private String getDefaultErrorPage(String path, String errorCode) {
		String defaultErrorpage = "";
		if (errorCode.equals(Constants.DEFAULT_STATUS_CODE)) {
			defaultErrorpage = Constants.DPSS_DEFAULT_ERROR_PAGE;
		} else if (errorCode.equals(Constants.DEFAULT_INTERNAL_CODE)) {
			defaultErrorpage = Constants.DPSS_INTERNAL_ERROR_PAGE;
		}

		if (null != config) {
			defaultErrorpage = iterateErrorPage(errorCode, config.getPagePath());
		}

		return defaultErrorpage;
	}

	private String iterateErrorPage(String errorCode, String[] pagePath) {
		String fPath = "";
		if (null != pagePath) {
			for (String path : pagePath) {
				if (path.contains(errorCode)) {
					fPath = path;
				}
			}
		}
		return fPath;
	}
}
