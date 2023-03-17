// Jquery
import $ from 'jquery';

// polyfills
import './js/vendor/polyfills/basic';
import './js/vendor/polyfills/picture-tag';

// components
import initializeFormValidation from './js/validation/validation.js';
import initializeFullCalendar from './js/calendar/calendar';
//import initializeHeader from './js/navigation/header-nav';
import initializeTopNav from './js/header/top-nav';
import initializeMainNav from './js/header/main-nav';
import initializeCalendarEvents from './js/calendar-events/calendar-events';
import initializeNewsCrousel from './js/news-carousel/news-carousel';
import initializeCardCrousel from './js/card-carousel/card-carousel';
import initializeAccordion from './js/accordion/accordion';
import initializeBreadcrumb from './js/breadcrumb/breadcrumb';
import initializeModalWindow from './js/modal/modal';
import initializeAlertsPrintDisableUtils from './js/alert/alert';
import initializeToggleShowMoreBtn from './js/more-less-text/more-less-text';
import initializePaymentTabularData from './js/datatable/datatable';
import initializePagination from './js/pagination/news-pagination';
import initializeContentList from './js/content-banner/content-banner';
import initializeSolrSearch from './js/solr-search/solr-search';
import initializeSideNav from './js/side-nav/side-nav';
import initializeFilters from './js/filters/filters';
import initializeAssetlist from './js/asset-list/asset-list';
import './js/search-results/search-results';
import intializePhotogallery from './js/photogallery/photo-gallery';
import intializePhotoPagination from './js/photogallery/pagination';

window.jQuery = $;
window.$ = $;

(() => {
  function initializePage() {
    // components
    initializeFormValidation();
    initializeFullCalendar();
    initializeTopNav();
    initializeMainNav();
    //initializeHeader();
    initializeCalendarEvents();
    initializeNewsCrousel();
    initializeCardCrousel();
    initializeAccordion();
    initializeBreadcrumb();
    initializeModalWindow();
    initializeAlertsPrintDisableUtils();
    initializeToggleShowMoreBtn();
    initializePaymentTabularData();
    initializePagination();
    initializeContentList();
    initializeSolrSearch();
    initializeSideNav();
    initializeFilters();
    initializeAssetlist();
    intializePhotogallery();
    intializePhotoPagination();
  }
  document.addEventListener('DOMContentLoaded', initializePage);
})();