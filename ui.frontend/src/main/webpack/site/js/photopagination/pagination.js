const initializepagination = () =>{
const paginationNumbers = document.getElementById("pagination-numbers");
const paginatedList = document.getElementById("paginated-list");
const listItems = paginatedList.querySelectorAll(".pagin-list");
const nextButton = document.getElementById("next-button");
const prevButton = document.getElementById("prev-button");
//const leftsideValue = document.getElementsByClassName("left-side");

//const paginationLimit = 3;
let paginationLimit = 0;
let totalimages = 0;


let maxcolperpage = Number($("#paginated-list").attr("data-totalcol") || 0);
let maxrowperpage = Number($("#paginated-list").attr("data-rowlimit") || 0 );

let maxdesktoplimit = Number($("#paginated-list").attr("data-desktoplimit") || 0) ;
let maxmobilelimit = Number($("#paginated-list").attr("data-mobilelimit") || 0);

let totalitemdesktop = Number($(".cmp-photo-gallery").attr("data-maxdesktopitem") || 0);
let totalitemmobile = Number($(".cmp-photo-gallery").attr("data-maxmobileitem") || 0);

let gallerylayout = $("#paginated-list").attr("data-layout");





function isMobile(){
  if(window.screen.width < 767){
    return true;
  }
  else{
    return false;
  }
}

if(maxcolperpage==0 && maxrowperpage==0 && maxdesktoplimit==0 && maxmobilelimit==0 && totalitemdesktop==0 && totalitemmobile==0){
  $(".pagination-container").hide();
}

if(maxcolperpage > 0 && maxrowperpage > 0  && gallerylayout === "columns"){
  paginationLimit = maxcolperpage * maxrowperpage;
}

else if(gallerylayout ==="list" ||  gallerylayout ==="grid"){

   if(isMobile() && maxmobilelimit > 0){
    paginationLimit= maxmobilelimit;
   }
   else if( (!isMobile()) && maxdesktoplimit > 0){
    paginationLimit = maxdesktoplimit;
   }
   else{
    paginationLimit = (listItems.length);
   }
}

else{
  paginationLimit = (listItems.length);
}


//for  total value of gallery images

if((totalitemmobile > 0) &&  (isMobile() === true) )
{
 totalimages = totalitemmobile ;
}
else if((totalitemdesktop > 0) && (isMobile() === false)){
  totalimages = totalitemdesktop;
}
else{
  totalimages = (listItems.length);
}

if(totalimages > (listItems.length)){
    totalimages=listItems.length
}




const pageCount =  (totalimages > 0 && totalimages <= (listItems.length)) ?  (Math.ceil((totalimages) / paginationLimit)) : (Math.ceil((listItems.length) / paginationLimit));
//const pageCount = Math.ceil(listItems.length / paginationLimit);

let currentPage = 1;

const disableButton = (button) => {
  button.classList.add("disabled");
  button.setAttribute("disabled", true);
};

const enableButton = (button) => {
  button.classList.remove("disabled");
  button.removeAttribute("disabled");
};




const handlePageButtonsStatus = () => {
  if (currentPage === 1) {
    disableButton(prevButton);
  } else {
    enableButton(prevButton);
  }

  if (pageCount === currentPage) {
    disableButton(nextButton);
  } else {
    enableButton(nextButton);
  }
};

const handleActivePageNumber = () => {
  document.querySelectorAll(".pagination-number").forEach((button) => {
    button.classList.remove("active");
    const pageIndex = Number(button.getAttribute("page-index"));
    if (pageIndex == currentPage) {
      button.classList.add("active");
    }
  });
};

const appendPageNumber = (index) => {
  const pageNumber = document.createElement("button");
  pageNumber.className = "pagination-number";
  pageNumber.innerHTML = index;
  pageNumber.setAttribute("page-index", index);
  pageNumber.setAttribute("aria-label", "Page " + index);

  paginationNumbers.appendChild(pageNumber);
};

const getPaginationNumbers = () => {
  for (let i = 1; i <= pageCount; i++) {
    appendPageNumber(i);
  }
};

const setCurrentPage = (pageNum) => {
  currentPage = pageNum;

    handleActivePageNumber();
    handlePageButtonsStatus();

    const prevRange = (pageNum - 1) * paginationLimit;


    //let pagex = Math.ceil(prevRange / paginationLimit);

    let  currRange =pageNum * paginationLimit;

    if(currRange > totalimages){
      currRange =  ((pageNum - 1) * paginationLimit) +  (totalimages % paginationLimit);
      //console.log(`${currRange}`);
    }

    //const currRange = Math.Min((pageNum - 1) * paginationLimit + paginationLimit , totalimages );
   // const currRange = (pageNum  * paginationLimit) < (listItems.length) ? (pageNum * paginationLimit)  : (listItems.length);




    document.getElementById("left-side").innerHTML = `${prevRange + 1} to ${currRange} of ${totalimages} records`;



    listItems.forEach((item, index) => {
        item.classList.add("hidden");
        if (index >= prevRange && index < currRange) {
            item.classList.remove("hidden");
        }
    });
};




window.addEventListener("load", () => {
  getPaginationNumbers();
  setCurrentPage(1);

  prevButton.addEventListener("click", () => {
    setCurrentPage(currentPage - 1);
  });

  nextButton.addEventListener("click", () => {
    setCurrentPage(currentPage + 1);
  });

  document.querySelectorAll(".pagination-number").forEach((button) => {
    const pageIndex = Number(button.getAttribute("page-index"));

    if (pageIndex) {
      button.addEventListener("click", () => {
        setCurrentPage(pageIndex);
      });
    }
  });
});
}

export default initializepagination;