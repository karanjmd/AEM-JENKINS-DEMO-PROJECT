package com.adobe.aem.lacounty.dpss.core.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.lacounty.dpss.core.models.SearchFilterModel;


import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class SearchFilterModelImplTest {
	private final AemContext aemContext = new AemContext();
	SearchFilterModel searchFilterModel = null;

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(SearchFilterModelImpl.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/model/impl/searchfilter.json", "/content");
		aemContext.currentResource("/content/filter");
		searchFilterModel = aemContext.request().adaptTo(SearchFilterModel.class);

	}

	@Test
	void getFilterSection() {
		String expected = "Header ";
		String actual = searchFilterModel.getFilterSection();
		assertEquals(expected, actual);
	}
	@Test
	void getResetLabel() {
		String expected = "Reset";
		String actual = searchFilterModel.getResetLabel();
		assertEquals(expected, actual);
	}
	@Test
	void getFilters() {	
		final int exp = 2;
		int actual = searchFilterModel.getFilters().size();
		assertEquals(exp, actual);
		assertEquals("filter1", searchFilterModel.getFilters().get(0).getFilterType());
		assertEquals("filterA", searchFilterModel.getFilters().get(0).getFilterList().get(0).getFilterName());
		assertEquals("filterValueA", searchFilterModel.getFilters().get(0).getFilterList().get(0).getFilterValue());
		assertEquals("filterA", searchFilterModel.getFilters().get(0).getFilterList().get(0).getFilterId());
	}



}
