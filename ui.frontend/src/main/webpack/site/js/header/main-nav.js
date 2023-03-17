const initializeMainNav = () => {
let menuToggle = document.querySelector("div.mainNav__menuToggle");
let navList = document.querySelector("div.mainNav__listWrapper");
function toggleMenu(){
menuToggle.classList.toggle("toggle-active");
navList.classList.toggle("mobile-nav");
document.body.classList.toggle("scroll-lock");
}
if(menuToggle){
    menuToggle.addEventListener('click', toggleMenu);
}
}
export default initializeMainNav;