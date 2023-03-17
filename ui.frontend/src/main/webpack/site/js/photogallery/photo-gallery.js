import PhotoSwipeLightbox from "photoswipe/dist/photoswipe-lightbox.esm.js";
import PhotoSwipe from "photoswipe/dist/photoswipe.esm.js";

import PhotoSwipeDynamicCaption from "photoswipe-dynamic-caption-plugin/photoswipe-dynamic-caption-plugin.esm.js";
import PhotoSwipeDynamicTitle from "../../js/titleplugin/photoswipe-dynamic-title-plugin.esm";
//import $ from "jquery";


const initializephotogallery= () => {
    const GALLERY_CMP_SELECTOR = ".cmp-photo-gallery";
    const GALLERY_GRID_WRAP_CLS = ".cmp-photo-gallery-grid";
    const GALLERY_PAGINATION_BTN = ".cmp-photo-gallery__photos-pagination__button";
    const photoGalleryStore = {};
    const galleryUrlHistory = {};
    const isMobileView =  "true";






        const lightbox = new PhotoSwipeLightbox({
            gallery: GALLERY_GRID_WRAP_CLS,
            children: 'a[data-folder-type="false"]',
            pswpModule: PhotoSwipe,
            initialZoomLevel: "fit",
            padding: { top: 80, bottom: 50, left: 0, right: 0 },
            // set background opacity
            bgOpacity: 0.7,
            wheelToZoom: true,
            imageClickAction: "close",
            showHideAnimationType: "zoom"
        });
        lightbox.on('uiRegister', function() {
            lightbox.pswp.ui.registerElement({
              name: 'download-button',
              order: 8,
              isButton: true,
              tagName: 'a',

              // SVG with outline
              html: {
                isCustomSVG: true,
                inner: '<path d="M20.5 14.3 17.1 18V10h-2.2v7.9l-3.4-3.6L10 16l6 6.1 6-6.1ZM23 23H9v2h14Z" id="pswp__icn-download"/>',
                outlineID: 'pswp__icn-download'
              },

              // Or provide full svg:
              // html: '<svg width="32" height="32" viewBox="0 0 32 32" aria-hidden="true" class="pswp__icn"><path d="M20.5 14.3 17.1 18V10h-2.2v7.9l-3.4-3.6L10 16l6 6.1 6-6.1ZM23 23H9v2h14Z" /></svg>',

              // Or provide any other markup:
              // html: '<i class="fa-solid fa-download"></i>'

              onInit: (el, pswp) => {
                el.setAttribute('download', '');
                 el.setAttribute('target', '_blank');
                // el.setAttribute('rel', 'noopener');

                pswp.on('change', () => {
                  console.log('change');
                  el.href = pswp.currSlide.data.src;
                });
              }
            });
          });


        //for download button



        //Append caption to each grid
        const captionPlugin = new PhotoSwipeDynamicCaption(lightbox, {
            // Plugins options, for example:
            type: "below",
        });

        //Append caption to each grid
       const titlePlugin = new PhotoSwipeDynamicTitle(lightbox, {
            // Plugins options, for example:
           type: "above",
       });
        lightbox.init();


};

export default initializephotogallery;