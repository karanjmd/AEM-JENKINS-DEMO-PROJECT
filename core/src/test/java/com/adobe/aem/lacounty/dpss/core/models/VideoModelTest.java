package com.adobe.aem.lacounty.dpss.core.models;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class VideoModelTest {
	
	private final AemContext aemContext = new AemContext();

	@BeforeEach
	void setUp() throws Exception {
		aemContext.addModelsForClasses(VideoModel.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/video.json", "/content");
	}
	
	@Test
    void getTitle() {
        aemContext.currentResource("/content/video");
        VideoModel video = aemContext.request().adaptTo(VideoModel.class);
        String expected = "video";
        String actual = video.getTitle();
        assertEquals(expected, actual);
    }
	@Test
    void getVideoCode() {
        aemContext.currentResource("/content/video");
        VideoModel video = aemContext.request().adaptTo(VideoModel.class);
        String expected = "This is video embed code";
        String actual = video.getVideoCode();
        assertEquals(expected, actual);
    }
}
