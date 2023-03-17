package com.adobe.aem.lacounty.dpss.core.models.impl;




import com.adobe.aem.lacounty.dpss.core.beans.PhotoGalleryItem;
import com.adobe.aem.lacounty.dpss.core.constants.Constants;
import com.adobe.aem.lacounty.dpss.core.models.PhotoGallery;
import com.adobe.aem.lacounty.dpss.core.utils.DynamicMediaUtils;
import com.adobe.aemds.guide.utils.JcrResourceConstants;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

/**
 * Photo Gallery Model Class.
 */
@Model(adaptables = {SlingHttpServletRequest.class}, adapters = {
        PhotoGallery.class}, resourceType = PhotoGalleryImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PhotoGalleryImpl implements PhotoGallery {

    /**
     * The Constant RESOURCE TYPE.
     */
    protected static final String RESOURCE_TYPE = "dpss/components/content/photogallery";

    /**
     *The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(PhotoGalleryImpl.class);

    /**
     * The SlingHttpServletRequest.
     */
    @Self
    private SlingHttpServletRequest slingHttpServletRequest;

    /**
     * The ResourceResolver.
     */
    @SlingObject
    private ResourceResolver resourceResolver;

    /**
     * Field listFrom.
     */
    @JsonIgnore
    @ValueMapValue
    private String listFrom;

    /**
     * Field assetRootPath.
     */
    @ValueMapValue
    private String assetRootPath;

    /**
     * Field includeSubDirs
     */
    @ValueMapValue
    private boolean includeSubDirs;

    /**
     * Field displayFolderName
     */
    @ValueMapValue
    private boolean displayFolderName;

    /**
     * Field displayPhotoTitle.
     */
    @ValueMapValue
    private boolean displayPhotoTitle;

    /**
     * Field imgDisplayOption.
     */
    @ValueMapValue
    private String imgDisplayOption;

    /**
     * Field itemBgColor.
     */
    @ValueMapValue
    private String itemBgColor;

    /**
     * Field layout.
     */
    @ValueMapValue
    private String layout;

    /**
     * Field columns.
     */
    @ValueMapValue
    private String columns;

    /**
     * Field maxRowsb4Pagination.
     */
    @ValueMapValue
    private String maxRowsb4Pagination;

    /**
     * Field paginateAfter.
     */
    @ValueMapValue
    private String paginateAfter;

    /**
     * Field paginateAfterMob.
     */
    @ValueMapValue
    private String paginateAfterMob;

    /**
     * Field maxItems.
     */
    @ValueMapValue
    private String maxItems;

    /**
     * Field maxItemsMob.
     */
    @ValueMapValue
    private String maxItemsMob;

    /**
     * Field assets.
     */
    @ValueMapValue
    private String[] assets;

    /**
     * Field showTitleInSlideshow.
     */
    @ValueMapValue
    private boolean showTitleInSlideshow;

    /**
     * Field showDescInSlideshow.
     */
    @ValueMapValue
    private boolean showDescInSlideshow;

    /**
     * Field thumbnailModifiers.
     */
    @ValueMapValue
    private String thumbnailModifiers;

    /**
     * Field slideshowModifiers.
     */
    @ValueMapValue
    private String slideshowModifiers;

    @Inject
    Resource componentResource;

    private final List<PhotoGalleryItem> galleryItems = new ArrayList<>();

    private String layoutClass;

    private String columnClass;

    private List<String> fixedAssertList = new ArrayList<>();

    private Map<String,String> fixedListMap = new HashedMap();

    /**
     * Init Method.
     */
    @PostConstruct
    private void initModel() {
        if (StringUtils.equalsIgnoreCase(this.listFrom, Constants.ASSET)) {
            String relPath = slingHttpServletRequest.getParameter("relPath");
            String folderPath = StringUtils.isNotBlank(relPath) ? assetRootPath + relPath : assetRootPath;
            Resource assetRoot = resourceResolver.getResource(folderPath);
            if (assetRoot != null) {
                Iterable<Resource> children = assetRoot.getChildren();
                children.forEach(this::addAssetOrFolderToPhotoGallery);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("No folder or asset was found at the path {} " ,folderPath);
                }
            }
        //} else if (ArrayUtils.isNotEmpty(assets)) {
        } else if (StringUtils.equalsIgnoreCase(this.listFrom, "static")) {
            fixedListMap = new HashedMap();
            Resource fixedListResource = componentResource.getChild("fixedAssetContainer");
            if(fixedListResource!=null){
                for(Resource fixedList : fixedListResource.getChildren()){
                    fixedAssertList.add(fixedList.getValueMap().get("assets",String.class));
                    fixedListMap.put(fixedList.getValueMap().get("assets",String.class),fixedList.getValueMap().get("imageAltText",String.class));
                }
            }
            //Arrays.stream(assets).forEach(asset -> addAssetOrFolderToPhotoGallery(resourceResolver.getResource(asset)));
            fixedAssertList.forEach(asset -> addAssetOrFolderToPhotoGallery(resourceResolver.getResource(asset)));
        }

        if (StringUtils.equals(layout, "list")) {
            columns = StringUtils.EMPTY;
        }
    }

    /**
     * Method to add asset or folder to PhotoGallery.
     *
     * @param resource
     */
    private void addAssetOrFolderToPhotoGallery(Resource resource) {
        if (resource != null) {
            String resourceType = resource.getResourceType();
            if (resourceType.equalsIgnoreCase(Constants.DAM_ASSET)) {
                Asset asset = resource.adaptTo(Asset.class);
                if (asset != null) {
                    String mimeType = asset.getMetadataValue(Constants.DAM_ASSET_MIMETYPE);
                    if (mimeType == null){
                        mimeType=asset.getMetadataValue(Constants.DAM_DC_FORMAT);
                    }
                    if (StringUtils.isNotBlank(mimeType) && (mimeType.equalsIgnoreCase(Constants.IMAGE_JPEG) ||
                            mimeType.equalsIgnoreCase(Constants.IMAGE_PNG) ||
                            mimeType.equalsIgnoreCase(Constants.IMAGE_TIFF) ||
                            mimeType.equalsIgnoreCase(Constants.IMAGE_SVG))) {
                        addAssetToPhotoGallery(resource, asset);
                    }
                }
            } else if ((resourceType.equalsIgnoreCase(Constants.SLING_FOLDER)||  resourceType.equalsIgnoreCase(DamConstants.NT_SLING_ORDEREDFOLDER)) && includeSubDirs) {
                Resource jcrContentResource = resource.getChild(Constants.JCR_CONTENT);
                if (jcrContentResource != null) {
                    addFolderToPhotoGallery(resource, jcrContentResource);
                }
            }
        }
    }

    /**
     * Method to add folder to PhotoGallery.
     *
     * @param resource
     * @param jcrContentResource
     */
    private void addFolderToPhotoGallery(Resource resource, Resource jcrContentResource) {
        ValueMap valueMap = jcrContentResource.getValueMap();
        String title = valueMap.get(Constants.JCR_TITLE, String.class) != null ? valueMap.get(Constants.JCR_TITLE, String.class) : resource.getName();
        String path = resource.getPath().contains(assetRootPath) ? resource.getPath().replace(assetRootPath, StringUtils.EMPTY) : resource.getPath();
        PhotoGalleryItem item = new PhotoGalleryItem(title, valueMap.get(JcrConstants.JCR_DESCRIPTION, String.class), JcrResourceConstants.NT_SLING_FOLDER,
                "?relPath=" + path);
        String folderThumbnail = setFolderThumbnailPath(resource);
        if (StringUtils.isNotEmpty(folderThumbnail)) {
            item.setThumbnailUrl(folderThumbnail);
        }
        galleryItems.add(item);
    }

    /**
     * Method to add asset to PhotoGallery.
     * @param resource
     * @param asset
     */
    private void addAssetToPhotoGallery(Resource resource, Asset asset) {
        String title = asset.getMetadataValue(DamConstants.DC_TITLE) != null ? asset.getMetadataValue(DamConstants.DC_TITLE) : asset.getName();
        PhotoGalleryItem item = new PhotoGalleryItem(title, asset.getMetadataValue(DamConstants.DC_DESCRIPTION), resource.getResourceType(),
                asset.getPath());
        String thumbnailUrl = DynamicMediaUtils.getDMUrl(asset, StringUtils.EMPTY, thumbnailModifiers);
        String slideShowUrl = DynamicMediaUtils.getDMUrl(asset, StringUtils.EMPTY, slideshowModifiers);
        item.setThumbnailUrl(thumbnailUrl);
        item.setSlideShowUrl(slideShowUrl);
        item.setWidth(asset.getMetadataValue(DamConstants.TIFF_IMAGEWIDTH));
        item.setHeight(asset.getMetadataValue(DamConstants.TIFF_IMAGELENGTH));

        if (StringUtils.equalsIgnoreCase(this.listFrom, "static")){
            if ( fixedListMap.get(asset.getPath()) ==null
                    ||fixedListMap.get(asset.getPath()).isEmpty()) {
                Resource childResource = resourceResolver.getResource(resource.getPath()+"/jcr:content/metadata");
                if (childResource != null){
                    ValueMap bannerValueMap = childResource.getValueMap();
                    if (bannerValueMap.get(Constants.DPSS_ALT_TEXT) != null){
                        item.setAltText(bannerValueMap.get(Constants.DPSS_ALT_TEXT).toString());
                    }
                }
            }else{
                String imageAltText = fixedListMap.get(asset.getPath());
                item.setAltText(imageAltText);
            }
        }
        galleryItems.add(item);
    }

    /**
     *
     * Method to Set folder thumbnail Path
     * @param subFolder
     * @return
     */
    private String setFolderThumbnailPath(Resource subFolder) {
        Resource jcrContent = subFolder.getChild(Constants.JCR_CONTENT);
        if (jcrContent != null) {
            Resource folderThumbnail = jcrContent.getChild("folderThumbnail");
            if (folderThumbnail != null) {
                Resource folderThumbnailJcrContent = folderThumbnail.getChild(Constants.JCR_CONTENT);
                if (folderThumbnailJcrContent != null) {
                    ValueMap valueMap = folderThumbnailJcrContent.getValueMap();
                    String[] folderThumbnailsArr = valueMap.get("dam:folderThumbnailPaths", String[].class);
                    if (folderThumbnailsArr != null && folderThumbnailsArr.length > 0) {
                        Resource thumbnailResource = resourceResolver.getResource(folderThumbnailsArr[0]);
                        if (thumbnailResource != null) {
                            Asset thumbnailAsset = thumbnailResource.adaptTo(Asset.class);
                            if (thumbnailAsset != null) {
                                return DynamicMediaUtils.getDMUrl(thumbnailAsset, StringUtils.EMPTY, StringUtils.EMPTY);
                            }
                        }
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * Returns the galleryItems
     * @return galleryItems
     */
    public List<PhotoGalleryItem> getGalleryItems() {
        return galleryItems;
    }

    /**
     * Returns the displayFolderName
     * @return displayFolderName
     */
    public boolean isDisplayFolderName() {
        return displayFolderName;
    }

    /**
     * Returns the displayPhotoTitle
     * @return displayPhotoTitle
     */
    public boolean isDisplayPhotoTitle() {
        return displayPhotoTitle;
    }

    /**
     * Returns the imgDisplayOption
     * @return imgDisplayOption
     */
    public String getImgDisplayOption() {
        return imgDisplayOption;
    }

    /**
     * Returns the itemBgColor
     * @return itemBgColor
     */
    public String getItemBgColor() {
        return itemBgColor;
    }

    /**
     * Returns the layout
     * @return layout
     */
    public String getLayout() {
        return layout;
    }

    /**
     * Returns the columns
     * @return columns
     */
    public String getColumns() {
        return columns;
    }

    /**
     * Returns the maxRowsb4Pagination
     * @return maxRowsb4Pagination
     */
    public String getMaxRowsb4Pagination() {
        return maxRowsb4Pagination;
    }

    /**
     * Returns the paginateAfter
     * @return paginateAfter
     */
    public String getPaginateAfter() {
        return paginateAfter;
    }

    /**
     * Returns the paginateAfterMob
     * @return paginateAfterMob
     */
    public String getPaginateAfterMob() {
        return paginateAfterMob;
    }

    /**
     * Returns the maxItems
     * @return maxItems
     */
    public String getMaxItems() {
        return maxItems;
    }

    /**
     * Returns the maxItemsMob
     * @return maxItemsMob
     */
    public String getMaxItemsMob() {
        return maxItemsMob;
    }

    /**
     * Returns the showTitleInSlideshow
     * @return showTitleInSlideshow
     */
    public boolean isShowTitleInSlideshow() {
        return showTitleInSlideshow;
    }

    /**
     * Returns the showDescInSlideshow
     * @return showDescInSlideshow
     */
    public boolean isShowDescInSlideshow() {
        return showDescInSlideshow;
    }

    /**
     * Returns the RESOURCE_TYPE
     * @return RESOURCE_TYPE
     */
    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }

    /** New Methods for DPSS */
    /**
     * Method to return layout class based on the layout selection
     * @return
     */


    public String getLayoutClass() {
        if(getLayout()!=null){
            if(getLayout().isEmpty()){
                return "";
            }else if(getLayout().equalsIgnoreCase("list")){
                return "grid-list-type";
            }else if(getLayout().equalsIgnoreCase("columns")){
                return "grid-column-control";
            }else if(getLayout().equalsIgnoreCase("grid")){
                return "grid-masonary-layout";
            }
        }return "";
    }
}
