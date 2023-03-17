package com.adobe.aem.lacounty.dpss.core.constants;

public class Constants {
	public static final String CONTENT_BASE_PATH = "/content";
	public static final String FORWARD_SLASH = "/";
	public static final String HOMEPAGE_TEMPLATE_NAME = "home-page";
	public static final String PROGRAMES_SERVICES_TEMPLATE_NAME = "program-page";
	public static final String DPSS_CONTENT_ROOT_PATH = "/content/dpss/";
	public static final String ENGLISH_HOMEPAGE_ISO_LANG="en";
	public static final String PREDICATE_START = "start";
	public static final String PREDICATE_END = "end";
	public static final String PN_HIDEINNAV = "jcr:content/hideInNav";
	public static final String PN_JCR_LASTMODIFIED = "@jcr:content/cq:lastModified";
	public static final String PN_JCR_NEWS_PUBLICATION_DATE = "@jcr:content/newsPublicationDate";
	public static final String PARAM_TAGS = "tags";
	public static final String PN_EVENT_TITLE = "eventTitle";
	public static final String TIMEZONE_PST = "PST";
	public static final String START_DATE = "startDate";
	public static final String END_DATE = "endDate";
	public static final String CT_JSON = "application/json";
	public static final String UTF8_ENCODING = "utf-8";
	public static final String ISO8601_DATE = "YYYY-MM-dd'T'HH:mm:ss.sssZ";
	public static final String DOT = ".";
	public static final String STRING_TRUE = "true";
	public static final String STRING_FALSE = "false";
	public static final String PREDICATE_DATERANGE = "daterange";
	public static final String PN_NEWS_BLURB = "newsBlurb";
	public static final String DEFAULT_STATUS_CODE = "404";
	public static final String DEFAULT_INTERNAL_CODE = "500";
	public static final String UTF_8 = "UTF-8";
	public static final String DPSS_DEFAULT_ERROR_PAGE = "/content/dpss/en/Homepage/Error/404";
	public static final String DPSS_INTERNAL_ERROR_PAGE = "/content/dpss/en/Homepage/Error/500";
	public static final String ERROR_PAGE_NAME = "error";
	public static final String CONTENT = "/content";

	public static final String S7_IS_IMAGE = "is/image/";

	public static final String DAM_S7_DOMAIN = "dam:scene7Domain";

	public static final String DAM_S7_LAST_MODIFIED = "dam:scene7LastModified";

	public static final String JCR_CONTENT = "jcr:content";

	public static final String ASSET = "asset";

	public static final String DAM_ASSET = "dam:Asset";

	public static final String DAM_ASSET_MIMETYPE = "dam:MIMEtype";

	public static final String IMAGE_JPEG = "image/jpeg";

	public static final String IMAGE_PNG = "image/png";

	public static final String IMAGE_TIFF = "image/tiff";

	public static final String CONTENT_DAM = "/content/dam";

	public static final String JCR_TITLE = "jcr:title";

	public static final String SLING_FOLDER = "sling:Folder";
	
	public static final String IMAGE_SVG = "image/svg+xml";
	
	public static final String DAM_DC_FORMAT ="dc:format";
	
	public static final String DPSS_ALT_TEXT = "dpssalttext";
	
	private Constants() {
     //Making the constructor private to avoid object creation
	}
}
