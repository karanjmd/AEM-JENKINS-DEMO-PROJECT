package com.adobe.aem.lacounty.dpss.core.solr;

import org.apache.solr.client.solrj.beans.Field;

public class DPSSSolrDocument {

	@Field
	private String id;
	@Field
	private String title;
	@Field
	private String description;
	@Field
	private String categorys;
	@Field
	private String groups;
	@Field
	private String imageLink;
	@Field
	private String body;
	@Field
	private String link;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategorys() {
		return categorys;
	}

	public void setCategorys(String categorys) {
		this.categorys = categorys;
	}

	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public DPSSSolrDocument(String id, String title, String description, String categorys, String groups,
			String imageLink, String body, String link) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.categorys = categorys;
		this.groups = groups;
		this.imageLink = imageLink;
		this.body = body;
		this.link = link;
	}

	public DPSSSolrDocument() {
		super();
	}

	@Override
	public String toString() {
		return "DPSSSolrDoc [id=" + id + ", title=" + title + ", description=" + description + ", categorys="
				+ categorys + ", groups=" + groups + ", imageLink=" + imageLink + ", body=" + body + ", link=" + link
				+ "]";
	}
}
