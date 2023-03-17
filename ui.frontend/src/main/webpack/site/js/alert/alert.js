/**
 * @desc Cross icon click event for hidding alerts and modal window.
 */
const initializeAlertsPrintDisableUtils = () => {

  const CLASS_CLOSE_BUTTON = document.querySelectorAll('a.close');
  for (let i = 0; i < CLASS_CLOSE_BUTTON.length; i += 1) {
    CLASS_CLOSE_BUTTON[i].addEventListener('click', (e) => {
      localStorage.setItem('wasVisited', 1);
      e.target.parentElement.classList.add('is-hidden');
      document.querySelector(".alert-notification").classList.add('alert-hide');
      document.querySelector(".alert-notification").classList.remove('alert-show');
    });
  }


  /**
   * @desc Add disable attribute to all disabled class button.
   */
  const CLASS_DISABLE_BUTTON = document.querySelectorAll('.disabled');
  if (CLASS_DISABLE_BUTTON.length !== 0) {
    for (let j = 0; j < CLASS_DISABLE_BUTTON.length; j += 1) {
      CLASS_DISABLE_BUTTON[j].querySelector('button').disabled = true;
    }
  }

  /**
   * @desc Print page functionality with "js-print" class.
   */
  const CLASS_PRINT_BUTTON = document.querySelectorAll('.js-print');
  if (CLASS_PRINT_BUTTON.length !== 0) {
    for (let k = 0; k < CLASS_PRINT_BUTTON.length; k += 1) {
      CLASS_PRINT_BUTTON[k].addEventListener('click', () => {
        window.print();
      });
    }
  }
  $(document).ready(function () {
    document.querySelector(".alert-notification").classList.add('alert-show');
    let alertTimer = $("#alertTimer").attr("value");
    if(alertTimer) {
      setTimeout(function(){
        localStorage.setItem('wasVisited', 0);
      }, alertTimer);
      if (localStorage.getItem('wasVisited') == 1) {
        document.querySelector(".alert-notification").classList.add('alert-hide');
        document.querySelector(".alert-dismissible").classList.add('is-hidden');
        document.querySelector(".alert-notification").classList.remove('alert-show');
      } else {
        document.querySelector(".alert-notification").classList.remove('alert-hide');
        document.querySelector(".alert-notification").classList.add('alert-show');
        document.querySelector(".alert-dismissible").classList.remove('is-hidden');
      }
    }
    
  });
};
export default initializeAlertsPrintDisableUtils;