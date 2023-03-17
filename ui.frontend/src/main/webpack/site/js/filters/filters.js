const initializeFilters = () => {


let filterText = document.querySelectorAll("li.filter-opt--item input");
filterText.forEach(item=>{
  item.addEventListener('click', event => {
    let label = item.parentNode;
    if(item.checked){
      label.style.fontWeight = "bold";
    }else{
      label.style.fontWeight = "normal"
    }
  })
})

//MOBILE-FILTER-START
let mobFilter = document.querySelector(".rsp-mobFilter");
function filterDrawer(){

 let optionsDrawer = document.querySelector(".side-filter--wrapper");
 optionsDrawer.classList.toggle("showFilter");
}
if(mobFilter){
  mobFilter.addEventListener('click', filterDrawer);
}

//MOBILE-FILTER-CLOSE
let closeFilter = document.querySelector(".side-filter--close");
console.log(closeFilter + "Close filter")
function filterClose(){

 let optionsDrawer = document.querySelector(".side-filter--wrapper");
 optionsDrawer.classList.remove("showFilter");
}
if(closeFilter){
  closeFilter.addEventListener('click', filterClose);
}

//MOBILE-FILTER-END

//GRID-LIST-END
let gridView = document.querySelector(".rsp-optGrid");
let listView = document.querySelector(".rsp-optList");
function showGrid(){
  let gridName = document.querySelector(".rsp-resultsContainer");
  if(gridName.classList.contains("result-listView")){
    gridName.classList.remove("result-listView");
  }
  gridName.classList.add("result-gridView");

 }
 function showList(){
  let listName = document.querySelector(".rsp-resultsContainer");
  if(listName.classList.contains("result-gridView")){
    listName.classList.remove("result-gridView");
  }
  listName.classList.add("result-listView");
 }
 if(gridView){
  gridView.addEventListener('click', showGrid);
 }
 if(listView){
  listView.addEventListener('click', showList);
 }

//GRID-LIST-END

//COLLAPSE-START
  var filterTitle = document.getElementsByClassName("filter-opt--title");

  for (let i = 0; i < filterTitle.length; i++) {
    filterTitle[i].addEventListener("click", function () {
      this.classList.toggle("arrow-animate");
      var content = this.nextElementSibling;
      if (content.style.display === "") {
        content.style.display = "none";
      }
      else if (content.style.display === "block") {
        content.style.display = "none";
      } else {
        content.style.display = "block";
      }
    });
  }

}

export default initializeFilters;