package com.adobe.aem.lacounty.dpss.core.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Servlet;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.drew.lang.annotations.NotNull;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=DPSS AssetList Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + "dpss/components/content/assetlist", "sling.servlet.selectors=" + "assetlist",
		"sling.servlet.extensions=" + "json" })

public class AssetListServlet extends SlingAllMethodsServlet {
	@Reference

	transient QueryBuilder queryBuilder;

	private static final long serialVersionUID = -2985313612534603774L;

	protected static final String EXTENSION = "json";

	private static final Logger LOG = LoggerFactory.getLogger(AssetListServlet.class);



	@Override
	protected void doGet(@NotNull SlingHttpServletRequest request, @NotNull SlingHttpServletResponse response)
			throws IOException {

		ResourceResolver resolver = request.getResourceResolver();
		String pathInfo = request.getPathInfo();
		String assetListPath = pathInfo.substring(0, pathInfo.indexOf('.'));
		if (StringUtils.isBlank(assetListPath) || null == resolver.getResource(assetListPath)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("assetList path/resource is empty");
			}
			return;
		}
		Session session = resolver.adaptTo(Session.class);
		Resource assetListResource = resolver.getResource(assetListPath);
		ValueMap valueMap = assetListResource.adaptTo(ValueMap.class);
		String assetListResourcePath = valueMap.get("pathexplorer", String.class);
		Map predicateMap = new HashMap();
		predicateMap.put("path", assetListResourcePath);
		predicateMap.put("path.exact", "true");
		predicateMap.put("type", "dam:Asset");
		predicateMap.put("p.limit", "-1");
		predicateMap.put("orderby", "@jcr:content/jcr:lastModified");
		predicateMap.put("orderby.sort", "desc");
		com.day.cq.search.Query query = queryBuilder.createQuery(PredicateGroup.create(predicateMap), session);

		// Get search results
		SearchResult result = ((com.day.cq.search.Query) query).getResult();
		List<Hit> hits = result.getHits();
		StringBuilder dsb = new StringBuilder();
		Integer delemnt = 0;
		String dctitle = null;
		String ftitle = null;
		dsb.append("[");
		for (Hit hit : hits) {
			if (delemnt > 0) {
				dsb.append(",");
			}
			try {
				
				String dmpath = hit.getPath();
				Resource resource = request.getResourceResolver().getResource(dmpath);
				if (resource != null) {
					Asset asset = resource.adaptTo(Asset.class);
					dctitle = asset.getMetadataValue("dc:title");

				}

				String[] dtm = dmpath.split("/");
				if (null != dctitle) {
					ftitle = dctitle;
				} else {
					ftitle = dtm[dtm.length - 1];
				}

				String fpath = dtm[0];
				for (int i = 1; i < dtm.length; i++) {
					fpath += "/" + dtm[i];
				}
				dsb.append("{" + "\"title\":" + "\"" + ftitle + "\"" + ",");
				dsb.append("\"path\":" + "\"" + fpath + "\"" + "}");

			} catch (RepositoryException e) {
				
			}
			delemnt++;
		}
		dsb.append("]");

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(dsb.toString());

	}

}
