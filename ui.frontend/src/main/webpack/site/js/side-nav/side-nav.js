const initializeSideNav = () => {

  $(document).ready(function () {

    if ($('.epolicynavigation ul li:has(ul)')) {
      $('li.cmp-navigation__item').prepend('<span class="sideNav-arrow"></span>');
    }

    $('.epolicynavigation ul li').each(function () {
      if ($(this).children('ul').length === 0) {
        $(this).children('.sideNav-arrow').remove();
      }else {

        $(this).addClass("child-exist");

      }
    })

    $('li.cmp-navigation__item--active a[aria-current="page"]').addClass("activePage");
    $('li.cmp-navigation__item--active a[aria-current="page"]').closest("li").parents("ul").addClass("shown");
   $('li.cmp-navigation__item--active a[aria-current="page"]').closest("li").parents("li").children("span").addClass("arrow-animate");


  
    $(".sideNav-arrow").click(function () {
      $(this).toggleClass("arrow-animate");

      if ($(this).closest("li").children("ul").length) {
        $(this).closest("li").children("ul").toggleClass("shown");
      }
    })
  });
}
export default initializeSideNav;