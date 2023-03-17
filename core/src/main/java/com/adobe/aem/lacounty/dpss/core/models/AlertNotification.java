package com.adobe.aem.lacounty.dpss.core.models;

import org.jetbrains.annotations.NotNull;
import org.osgi.annotation.versioning.ConsumerType;

import com.adobe.cq.export.json.ComponentExporter;

@ConsumerType
public interface AlertNotification extends ComponentExporter {
	default String getAlertOrNotification() {
		throw new UnsupportedOperationException();
	}

	default String getAlertNotificationContent() {
		throw new UnsupportedOperationException();
	}

	@NotNull
	@Override
	default String getExportedType() {
		throw new UnsupportedOperationException();
	}

	default String getTimer() {
		throw new UnsupportedOperationException();
	}
}
