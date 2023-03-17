package com.adobe.aem.lacounty.dpss.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.lacounty.dpss.core.models.SearchResultModel;
import com.adobe.aem.lacounty.dpss.core.models.impl.SearchResultModelImpl;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
public class SearchResultModelImplTest {

	private final AemContext aemContext = new AemContext();
	SearchResultModel searchResultModel = null;

	@BeforeEach
	void setUp() {
		aemContext.addModelsForClasses(SearchResultModelImpl.class);
		aemContext.load().json("/com/adobe/aem/lacounty/dpss/core/models/searchresult.json", "/content");
		aemContext.currentResource("/content/searchresult");
		searchResultModel = aemContext.request().adaptTo(SearchResultModel.class);

	}

	@Test
	void getSearchPlaceHolder() {
		String expected = "Search all of my DPSS";
		String actual = searchResultModel.getSearchPlaceHolder();
		assertEquals(expected, actual);
	}

	@Test
	void getResultpage() {
		String expected = "/content/dpss/en/Homepage/ResultPage";
		String actual = searchResultModel.getResultpage();
		assertEquals(expected, actual);
	}

	@Test
	void getResultsSize() {
		String expected = "20";
		String actual = searchResultModel.getResultsSize();
		assertEquals(expected, actual);
	}

	@Test
	void getShowResultCount() {
		String expected = "30";
		String actual = searchResultModel.getShowResultCount();
		assertEquals(expected, actual);
	}

	@Test
	void getDefaultSort() {
		String expected = "@jcr:content/jcr:title";
		String actual = searchResultModel.getDefaultSort();
		assertEquals(expected, actual);
	}

	@Test
	void getDefaultSortDirection() {
		String expected = "asc";
		String actual = searchResultModel.getDefaultSortDirection();
		assertEquals(expected, actual);
	}

	@Test
	void getLoadMoreText() {
		String expected = "Load more data";
		String actual = searchResultModel.getLoadMoreText();
		assertEquals(expected, actual);
	}

	@Test
	void getNoResultText() {
		String expected = "No data found";
		String actual = searchResultModel.getNoResultText();
		assertEquals(expected, actual);
	}
	@Test
	void getSortOptions() {
		int expected = 1;
		assertEquals(expected, searchResultModel.getSortOptions().size());
		assertEquals("option1", searchResultModel.getSortOptions().get(0).getOption());
		assertEquals("option2", searchResultModel.getSortOptions().get(0).getOptionValue());		
	}
	@Test
	void getSortDirections() {
		int expected = 1;
		assertEquals(expected, searchResultModel.getSortDirections().size());
		assertEquals("direction1", searchResultModel.getSortDirections().get(0).getSortDirection());
		assertEquals("direction2", searchResultModel.getSortDirections().get(0).getSortDirectionValue());		
	}
	
}
