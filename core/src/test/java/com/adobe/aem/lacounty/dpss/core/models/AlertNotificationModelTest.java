package com.adobe.aem.lacounty.dpss.core.models;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.lacounty.dpss.core.models.impl.AlertNotificationModelImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class AlertNotificationModelTest {
	
	private final AemContext aemContext = new AemContext();

    @BeforeEach
    void setUp() {
        aemContext.addModelsForClasses(AlertNotificationModelImpl.class);
        aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/alert.json", "/content");
    }

    @Test
    void getAlertOrNotification() {
        aemContext.currentResource("/content/alert");
        AlertNotification anm = aemContext.request().adaptTo(AlertNotification.class);
        String expected = "alert";
        String actual = anm.getAlertOrNotification();
        assertEquals(expected, actual);
    }
    @Test
    void getAlertNotificationContent() {
        aemContext.currentResource("/content/alert");
        AlertNotification anm = aemContext.request().adaptTo(AlertNotification.class);
        String expected = "<p>This is alert and notifications</p>\r\n";
        String actual = anm.getAlertNotificationContent();
        assertEquals(expected, actual);
    }
    @Test
    void getExportedType() {
        aemContext.currentResource("/content/alert");
        AlertNotification anm = aemContext.request().adaptTo(AlertNotification.class);
        String expected = "dpss/components/content/alert-notification/v1/alert-notification";
        String actual = anm.getExportedType();
        assertEquals(expected, actual);
    }
    @Test
    void getTimer() {
        aemContext.currentResource("/content/alert");
        AlertNotification anm = aemContext.request().adaptTo(AlertNotification.class);
        String expected = "30000";
        String actual = anm.getTimer();
        assertEquals(expected, actual);
    }

}
