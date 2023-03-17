/**
 * PhotoSwipe Dynamic Title plugin v1.1.0
 * https://github.com/dimsemenov/photoswipe-dynamic-title-plugin
 * 
 * By https://dimsemenov.com
 */

const defaultOptions = {
  titleContent: '.pswp-title-content',
  type: 'auto',
  horizontalEdgeThreshold: 20,
  mobileTitleOverlapRatio: 0.3,
  mobileLayoutBreakpoint: 600,
};

class PhotoSwipeDynamicTitle {
  constructor(lightbox, options) {
    this.options = {
      ...defaultOptions,
      ...options
    };

    this.lightbox = lightbox;

    this.lightbox.on('init', () => {
      this.initPlugin();
    });
  }

  initPlugin() {
    this.pswp = this.lightbox.pswp;
    this.isTitleHidden = false;
    this.tempTitle = false;
    this.titleElement = false;

    this.pswp.on('uiRegister', () => {
      this.pswp.ui.registerElement({
        name: 'dynamic-title',
        order: 9,
        isButton: false,
        appendTo: 'root',
        html: '',
        onInit: (el) => {
          this.titleElement = el;
          this.initTitle();
        }
      });
    });
  }

  initTitle() {
    const { pswp } = this;

    pswp.on('change', () => {
      this.updateTitleHTML(); 
      this.updateCurrentTitlePosition();

      // make sure title is displayed after slides are switched
      this.showTitle();
    });

    pswp.on('calcSlideSize', (e) => this.onCalcSlideSize(e));

    // hide title if mainscroll is shifted (dragging)
    pswp.on('moveMainScroll', () => {
      if (!this.useMobileLayout()) {
        if (this.pswp.mainScroll.isShifted()) {
          this.hideTitle();
        } else {
          this.showTitle();
        }
      }
    });

    // hide title if zoomed
    pswp.on('zoomPanUpdate', () => {
      if (pswp.currSlide.currZoomLevel > pswp.currSlide.zoomLevels.initial) {
        this.hideTitle();
      } else {
        this.showTitle();
      }
    });

    pswp.on('beforeZoomTo', (e) => {
      const { currSlide } = pswp;

      if (currSlide.__dcAdjustedPanAreaSize) {
        if (e.destZoomLevel > currSlide.zoomLevels.initial) {
          currSlide.panAreaSize.x = currSlide.__dcOriginalPanAreaSize.x;
          currSlide.panAreaSize.y = currSlide.__dcOriginalPanAreaSize.y;
        } else {
          // Restore panAreaSize after we zoom back to initial position
          currSlide.panAreaSize.x = currSlide.__dcAdjustedPanAreaSize.x;
          currSlide.panAreaSize.y = currSlide.__dcAdjustedPanAreaSize.y;
        }
      }
    });
  }

  useMobileLayout() {
    const { mobileLayoutBreakpoint } = this.options;

    if (typeof mobileLayoutBreakpoint === 'function') {
      return mobileLayoutBreakpoint.call(this);
    } else if (typeof mobileLayoutBreakpoint === 'number') {
      if (window.innerWidth < mobileLayoutBreakpoint) {
        return true;
      }
    }
    
    return false;
  }

  hideTitle() {
    if (!this.isTitleHidden) {
      this.isTitleHidden = true;
      this.titleElement.classList.add('pswp__dynamic-title--faded');

      // Disable title visibility with the delay, so it's not interactable 
      if (this.titleFadeTimeout) {
        clearTimeout(this.titleFadeTimeout);
      }
      this.titleFadeTimeout = setTimeout(() => {
        this.titleElement.style.visibility = 'hidden';
        this.titleFadeTimeout = null;
      }, 400);
    }
  }

  showTitle() {
    if (this.isTitleHidden) {
      this.isTitleHidden = false;
      this.titleElement.style.visibility = 'visible';
      
      clearTimeout(this.titleFadeTimeout);
      this.titleFadeTimeout = setTimeout(() => {
        this.titleElement.classList.remove('pswp__dynamic-title--faded');
        this.titleFadeTimeout = null;
      }, 50);
    }
  }

  setTitlePosition(x, y) {
    const isOnHorizontalEdge = (x <= this.options.horizontalEdgeThreshold);
    this.titleElement.classList[
      isOnHorizontalEdge ? 'add' : 'remove'
    ]('pswp__dynamic-title--on-hor-edge');

    this.titleElement.style.left = x + 'px';
    this.titleElement.style.top = y + 'px';
  }

  setTitleWidth(titleEl, width) {
    if (!width) {
      titleEl.style.removeProperty('width');
    } else {
      titleEl.style.width = width + 'px';
    }
  }

  setTitleType(titleEl, type) {
    const prevType = titleEl.dataset.pswpTitleType;
    if (type !== prevType) {
      titleEl.classList.add('pswp__dynamic-title--' + type);
      titleEl.classList.remove('pswp__dynamic-title--' + prevType);
      titleEl.dataset.pswpTitleType = type;
    }
  }

  updateCurrentTitlePosition() {
    const slide = this.pswp.currSlide;

    if (!slide.dynamicTitleType) {
      return;
    }

    if (slide.dynamicTitleType === 'mobile') {
      this.setTitleType(this.titleElement, slide.dynamicTitleType);
      
      this.titleElement.style.removeProperty('left');
      this.titleElement.style.removeProperty('top');
      this.setTitleWidth(this.titleElement, false);
      return;
    }

    const zoomLevel = slide.zoomLevels.initial;
    const imageWidth = Math.ceil(slide.width * zoomLevel);
    const imageHeight = Math.ceil(slide.height * zoomLevel);

    
    this.setTitleType(this.titleElement, slide.dynamicTitleType);
    if (slide.dynamicTitleType === 'aside') {
      this.setTitlePosition(
        this.pswp.currSlide.bounds.center.x + imageWidth,
        this.pswp.currSlide.bounds.center.y
      );
      this.setTitleWidth(this.titleElement, false);
    } else if (slide.dynamicTitleType === 'below') {
      this.setTitlePosition(
        this.pswp.currSlide.bounds.center.x,
        this.pswp.currSlide.bounds.center.y + imageHeight
      );
      this.setTitleWidth(this.titleElement, imageWidth);
    }
    else if (slide.dynamicTitleType === 'above') {
        this.setTitlePosition(
          this.pswp.currSlide.bounds.center.x ,
          this.pswp.currSlide.bounds.center.y- 45
        );
        this.setTitleWidth(this.titleElement, imageWidth);
      }
  }

  /**
   * Temporary title is used to measure size for the current/next/previous titles,
   * (it has visibility:hidden)
   */
  createTemporaryTitle() {
    this.tempTitle = document.createElement('div');
    this.tempTitle.className = 'pswp__dynamic-title pswp__dynamic-title--temp';
    this.tempTitle.style.visibility = 'hidden';
    this.tempTitle.setAttribute('aria-hidden', 'true');
    // move title element, so it's after BG,
    // so that other controls can freely overlap it
    this.pswp.bg.after(this.titleElement); 
    this.titleElement.after(this.tempTitle);
  }

  onCalcSlideSize(e) {
    const { slide } = e;

    const titleHTML = this.getTitleHTML(e.slide);
    let useMobileVersion = false;
    let titleSize;

    if (!titleHTML) {
      slide.dynamicTitleType = false;
      return;
    }

    this.storeOriginalPanAreaSize(slide);

    slide.bounds.update(slide.zoomLevels.initial);
    
    if (this.useMobileLayout()) {
      slide.dynamicTitleType = 'mobile';
      useMobileVersion = true;
    } else {
      if (this.options.type === 'auto') {
        if (slide.bounds.center.x > slide.bounds.center.y) {
          slide.dynamicTitleType = 'aside';
        } else {
          slide.dynamicTitleType = 'below';
        }
      } else {
        slide.dynamicTitleType = this.options.type;
      }
    } 

    const imageWidth = Math.ceil(slide.width * slide.zoomLevels.initial);
    const imageHeight = Math.ceil(slide.height * slide.zoomLevels.initial);

    if (!this.tempTitle) {
      this.createTemporaryTitle();
    }

    this.setTitleType(this.tempTitle, slide.dynamicTitleType);

    if (slide.dynamicTitleType === 'aside') {
      this.tempTitle.innerHTML = this.getTitleHTML(e.slide);
      this.setTitleWidth(this.tempTitle, false);
      titleSize = this.measureTitleSize(this.tempTitle, e.slide);
      const titleWidth = titleSize.x;      

      const horizontalEnding = imageWidth + slide.bounds.center.x;
      const horizontalLeftover = (slide.panAreaSize.x - horizontalEnding);

      if (horizontalLeftover <= titleWidth) {
        slide.panAreaSize.x -= titleWidth;
        this.recalculateZoomLevelAndBounds(slide);
      } else {
        // do nothing, title will fit aside without any adjustments
      }
    } else if (slide.dynamicTitleType === 'below' || useMobileVersion) {
      this.setTitleWidth(
        this.tempTitle, 
        useMobileVersion ? this.pswp.viewportSize.x : imageWidth
      );
      this.tempTitle.innerHTML = this.getTitleHTML(e.slide);
      titleSize = this.measureTitleSize(this.tempTitle, e.slide);
      const titleHeight = titleSize.y;


      // vertical ending of the image
      const verticalEnding = imageHeight + slide.bounds.center.y;

      // height between bottom of the screen and ending of the image
      // (before any adjustments applied) 
      const verticalLeftover = slide.panAreaSize.y - verticalEnding;
      const initialPanAreaHeight = slide.panAreaSize.y;

      if (verticalLeftover <= titleHeight) {
        // lift up the image to give more space for title
        slide.panAreaSize.y -= Math.min((titleHeight - verticalLeftover) * 2, titleHeight);

        // we reduce viewport size, thus we need to update zoom level and pan bounds
        this.recalculateZoomLevelAndBounds(slide);

        const maxPositionX = slide.panAreaSize.x * this.options.mobileTitleOverlapRatio / 2;

        // Do not reduce viewport height if too few space available
        if (useMobileVersion 
            && slide.bounds.center.x > maxPositionX) {
          // Restore the default position
          slide.panAreaSize.y = initialPanAreaHeight;
          this.recalculateZoomLevelAndBounds(slide);
        }
      }

      
      
      // if (this.useMobileLayout && slide.bounds.center.x > 100) {
      //   // do nothing, title will overlap the bottom part of the image
      // } else if (verticalLeftover <= titleHeight) {
        
      // } else {
      //   // do nothing, title will fit below the image without any adjustments
      // }
    } 
    else if (slide.dynamicTitleType === 'above' || useMobileVersion) {
        this.setTitleWidth(
          this.tempTitle, 
          useMobileVersion ? this.pswp.viewportSize.x : imageWidth
        );
        this.tempTitle.innerHTML = this.getTitleHTML(e.slide);
        titleSize = this.measureTitleSize(this.tempTitle, e.slide);
        const titleHeight = titleSize.y;
        
        // vertical starting of the image
        const verticalStarting = slide.bounds.center.y;
  
  
        // height between top of the screen and start of the image
        // (before any adjustments applied) 
        const verticalLeftover = slide.panAreaSize.y +verticalStarting;
        const initialPanAreaHeight = slide.panAreaSize.y;
  
        if (verticalLeftover <= titleHeight) {
          // lift up the image to give more space for title
          slide.panAreaSize.y -= Math.min((titleHeight + verticalLeftover) * 2, titleHeight);
  
          // we reduce viewport size, thus we need to update zoom level and pan bounds
          this.recalculateZoomLevelAndBounds(slide);
  
          const maxPositionX = slide.panAreaSize.x * this.options.mobileTitleOverlapRatio / 2;
  
          // Do not reduce viewport height if too few space available
          if (useMobileVersion 
              && slide.bounds.center.x > maxPositionX) {
            // Restore the default position
            slide.panAreaSize.y = initialPanAreaHeight;
            this.recalculateZoomLevelAndBounds(slide);
          }
        }
  
        
        
        // if (this.useMobileLayout && slide.bounds.center.x > 100) {
        //   // do nothing, title will overlap the bottom part of the image
        // } else if (verticalLeftover <= titleHeight) {
          
        // } else {
        //   // do nothing, title will fit below the image without any adjustments
        // }
      }
    else {
      // mobile
    }

    this.storeAdjustedPanAreaSize(slide);

    if (slide === this.pswp.currSlide) {
      this.updateCurrentTitlePosition();
    }
  }

  measureTitleSize(titleEl, slide) {
    const rect = titleEl.getBoundingClientRect();
    const event = this.pswp.dispatch('dynamicTitleMeasureSize', {
      titleEl,
      slide,
      titleSize: {
        x: rect.width,
        y: rect.height
      }
    });
    return event.titleSize;
  }

  recalculateZoomLevelAndBounds(slide) {
    slide.zoomLevels.update(slide.width, slide.height, slide.panAreaSize);
    slide.bounds.update(slide.zoomLevels.initial);
  }

  storeAdjustedPanAreaSize(slide) {
    if (!slide.__dcAdjustedPanAreaSize) {
      slide.__dcAdjustedPanAreaSize = {};
    }
    slide.__dcAdjustedPanAreaSize.x = slide.panAreaSize.x;
    slide.__dcAdjustedPanAreaSize.y = slide.panAreaSize.y;
  }

  storeOriginalPanAreaSize(slide) {
    if (!slide.__dcOriginalPanAreaSize) {
      slide.__dcOriginalPanAreaSize = {};
    }
    slide.__dcOriginalPanAreaSize.x = slide.panAreaSize.x;
    slide.__dcOriginalPanAreaSize.y = slide.panAreaSize.y;
  }

  getTitleHTML(slide) {
    if (typeof this.options.titleContent === 'function') {
      return this.options.titleContent.call(this, slide);
    }

    const currSlideElement = slide.data.element;
    let titleHTML = '';
    if (currSlideElement) {
      const hiddenTitle = currSlideElement.querySelector(this.options.titleContent);
      if (hiddenTitle) {
        // get title from element with class pswp-title-content
        titleHTML = hiddenTitle.innerHTML;
      } else {
        const img = currSlideElement.querySelector('img');
        if (img) {
          // get title from alt attribute
          titleHTML = img.getAttribute('alt');
        }
      }
    }
    return titleHTML;
  }

  updateTitleHTML() {
    const titleHTML = this.getTitleHTML(pswp.currSlide);
    this.titleElement.style.visibility = titleHTML ? 'visible' :  'hidden';
    this.titleElement.innerHTML = titleHTML || '';
    this.pswp.dispatch('dynamicTitleUpdateHTML', { 
      titleElement: this.titleElement
    });
  }
}

export default PhotoSwipeDynamicTitle;
