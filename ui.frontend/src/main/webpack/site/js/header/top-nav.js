const initializeTopNav = () => {
let userName = document.querySelector("a.topNav__userName");
function userDropdown(){
    console.log(userName);
 let userDropArrow = document.querySelector("span.topNav__arrow");
userDropArrow.classList.toggle("topNav__arrow-active");
}
if(userName){
    userName.addEventListener('click', userDropdown);
}
}
export default initializeTopNav;