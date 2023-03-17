package com.adobe.aem.lacounty.dpss.core.models;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class ErrorPageHandlerModelTest {
	private final AemContext aemContext = new AemContext();
	
	private final AemContext context = new AemContext();
	 
	 private static final String CONTENT_ROOT = "/content";
	 private static final String CURRENT_PAGE = "/content/errorpage";

	 private static final String ERROR_PAGE = CURRENT_PAGE + "/Homepage";

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(ErrorPageHandlerModel	.class);
        aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/errorpage.json", "/content");
       
    }
    
    
    @Test
    public void testErrorCode500() {
    	aemContext.currentResource("/content/errorpage");
     String expectedErrorPage = "/content/dpss/en/Homepage/Error/500";
     aemContext.request().setAttribute("errorCode", "500");
    // context.currentResource(ERROR_PAGE);
     aemContext.requestPathInfo().setResourcePath(ERROR_PAGE);
     ErrorPageHandlerModel model = aemContext.request().adaptTo(ErrorPageHandlerModel.class);
     assertEquals(expectedErrorPage, model.getErrorPage());
    }
    @Test
    public void testErrorCode404() {
    	aemContext.currentResource("/content/errorpage");
     String expectedErrorPage = "/content/dpss/en/Homepage/Error/404";
     aemContext.request().setAttribute("errorCode", "404");
    // context.currentResource(ERROR_PAGE);
     aemContext.requestPathInfo().setResourcePath(ERROR_PAGE);
     ErrorPageHandlerModel model = aemContext.request().adaptTo(ErrorPageHandlerModel.class);
     assertEquals(expectedErrorPage, model.getErrorPage());
    }
}
