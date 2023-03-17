package com.adobe.aem.lacounty.dpss.core.utils;


import com.adobe.aem.lacounty.dpss.core.constants.Constants;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.scene7.api.constants.Scene7Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * Dynamic Media Utils Class
 */
public class DynamicMediaUtils {
    private DynamicMediaUtils() {
    }

    /**
     *
     * @param asset {@link Asset}
     * @param preset Preset
     * @param modifiers DM Modifiers
     * @return
     */
    public static String getDMUrl(Asset asset, String preset, String modifiers) {
        String dmAssetName = asset.getMetadataValue(Scene7Constants.PN_S7_FILE);
        if(StringUtils.isNotBlank(dmAssetName)) {
            String domain = asset.getMetadataValue(Constants.DAM_S7_DOMAIN);
            String lastModified = asset.getMetadataValue(Constants.DAM_S7_LAST_MODIFIED);
            String imageUrl = domain.concat(Constants.S7_IS_IMAGE).concat(dmAssetName)
                              .concat("?ts=").concat(lastModified);
            if (StringUtils.isNotBlank(preset)){
                String imagePresetParam = (imageUrl.contains("?") ? '&':'?') + "$" + preset + "$";
                imageUrl+=imagePresetParam;
            }
            if (StringUtils.isNotBlank(modifiers)){
                String imageModifiersParam = (imageUrl.contains("?") ? '&':'?') + modifiers;
                imageUrl+=imageModifiersParam;
            }
            return imageUrl;
        }
        return asset.getPath();
    }
    
}
