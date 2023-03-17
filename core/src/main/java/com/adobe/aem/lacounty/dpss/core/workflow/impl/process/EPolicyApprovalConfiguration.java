/**
 * 
 */
package com.adobe.aem.lacounty.dpss.core.workflow.impl.process;

import org.apache.sling.caconfig.annotation.Configuration;
import org.apache.sling.caconfig.annotation.Property;

/**
 * @author vikas-ku
 *
 */
@Configuration(label = "E-Policy Approval Configuration", description = "E-Policy Approval Configuration")
public @interface EPolicyApprovalConfiguration {

	
	@Property(label = "Contributors", description = "Contributors")
	String contributors() default " ";
	
	@Property(label = "Reviewers", description = "Reviewers")
	String[] reviewers() default " ";
	
	@Property(label = "Section", description = "Section")
	String[] section() default " ";
	
	@Property(label = "Branch", description = "Branch")
	String[] branch() default " ";
	
	@Property(label = "Division", description = "Division")
	String[] division() default " ";
	
	@Property(label = "Bureau", description = "Bureau")
	String[] bureau() default " ";

}
