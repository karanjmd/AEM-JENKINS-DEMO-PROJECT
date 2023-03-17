package com.adobe.aem.lacounty.dpss.core.beans;

public class PhotoGalleryItem {
    
    private String title;
    private String description;
    private String type;
    private String path;
    private String thumbnailUrl;
    private String slideShowUrl;
    private String width;
    private String height;

    private String altText;
    
    public PhotoGalleryItem(String title, String description, String type, String path) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.path = path;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
    public String getSlideShowUrl() {
        return slideShowUrl;
    }
    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    public void setSlideShowUrl(String slideShowUrl) {
        this.slideShowUrl = slideShowUrl;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    @Override
    public String toString() {
        return "PhotoGalleryItem [title=" + title + ", description=" + description + ", type=" + type + "]";
    }
    
}
