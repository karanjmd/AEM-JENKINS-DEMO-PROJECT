package com.adobe.aem.lacounty.dpss.core.solr.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author abhashkumar.budayak
 *
 */
public final class SolrUtils {
	private static final Logger LOG = LoggerFactory.getLogger(SolrUtils.class);

	private SolrUtils() {
	}

	/**
	 * This method handles the HTML character removal from the input parameter
	 *
	 * @param Takes input as string
	 * @return Empty string value
	 */
	public static String replaceHTMLChar(String inputString) {
		if (StringUtils.isNotEmpty(inputString)) {
			inputString = inputString.replaceAll("\\<.*?\\>", "");
			inputString = inputString.replaceAll("(\r\n|\n)", "");
			inputString = inputString.replaceAll("&nbsp;", "");
		}
		return inputString;
	}
	
	/**
	 * This method return boolean in case of image
	 *
	 * @param Takes input as string
	 * @return Boolean
	 */
	public static Boolean isImageUrl(String inputString) {
		if (StringUtils.isNotEmpty(inputString)) {
			return StringUtils.endsWithAny(inputString, "ai", "bmp", "gif", "ico", "jpg", "jpeg", "ps", "psd", "svg", "tif",
					"tiff","png");
		}
		return false;
	}
	
	/**
	 * This method return boolean type of the input string
	 *
	 * @param Takes input as string
	 * @return Boolean
	 */
	public static Boolean isBoolean(String inputString) {
		if (StringUtils.isNotEmpty(inputString)
				&& Pattern.compile("true|false", Pattern.CASE_INSENSITIVE).matcher(inputString.trim()).matches()) {
			return true;
		}
		return false;
	}
}
