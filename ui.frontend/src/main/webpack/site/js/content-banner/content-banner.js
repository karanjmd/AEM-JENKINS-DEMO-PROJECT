const initializeContentList = () => {
    let listToggle = document.querySelector("div.content__listToggle");
    let contentList = document.querySelector(".mobile-contentList");
    let body = document.querySelector("body");
    console.log(listToggle);
    console.log(contentList);
    function toggleList() {
        listToggle.classList.toggle("toggle-active");
        contentList.classList.toggle("content-active");
        body.classList.toggle("scroll-lock");
    }
    if(listToggle){
        listToggle.addEventListener('click', toggleList);
    }
}
export default initializeContentList;