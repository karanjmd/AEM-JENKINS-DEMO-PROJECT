const initializeSolrSearch = () => {
	var hostUrl = window.location.protocol + "//" + window.location.host;
	//var hostUrl = "http://localhost";
	var searchTerm = $("#rsp-searchInput").val();
	let searchResultsUrl = $("#searchResultsUrl").attr("href");
	let searchResultsSize = $("#searchResultsSize").attr("href");
	let loadMoreButton = $("#loadMoreButton");
	let searchStart = 0;
	var result1 = [];
	//let searchFilterresult = $("").
	

	$(document).ready(function() {
		let hrefData = $(".search-site-url").attr("id");
		$(".search-site-url").attr("href", hrefData);
		$("#rsp-fetchResult").click(function () {
			$("#resultsContainer").empty();
			$("#resultCount").empty();
			$("#displayedResult").empty();
			result1=[];	
			if ($("#rsp-searchInput").val() == "") {
				alert("please enter the search term");
			} else {
				searchTerm = $("#rsp-searchInput").val();

				let searchsiteurl = $(".search-site-url").attr("id");
				let changedurl = searchsiteurl + "?s=" + searchTerm;
				$(".search-site-url").attr("href", changedurl);
                searchStart=0;
				ajaxCall(searchTerm, searchResultsSize, searchStart);
			}
			$("#searchbar-button").click(function() {
				if ($("#searchbar-box").val() == "") {
					alert("please enter the search term");
				}
				else if ($("#search-filter").val() !== "Internal Site") {
					alert("please select Internal Site to search");
				}
				else {
					let tempUrl = searchResultsUrl + "?searchTerm=" + $("#searchbar-box").val();
					window.location = tempUrl;
				}
			});
		});
		if (window.location.href.includes('searchTerm')) {
			$("#rsp-searchInput").val(document.location.href.split("?")[1].split("=")[1]);
			$("#rsp-fetchResult").click();
		}
	});
	// Template
	const templateResult = (result) => {
		const resultContainerEl = document.querySelector('#resultsContainer');
		const resultDom = result?.map(element=>{
			return `<div class='rsp-resultCard' style="display: flex"><div class='rsp-resultImg'><img src='${element.imageLink}' /></div><div class='rsp-resultDetail'><h2> <a href='${element.link}'>${element.title}</a></h2><p>${element.description}</p></div></div>`
		}).join(' ');
		resultContainerEl.innerHTML = resultDom;
	}
	//checkbox string
	function ajaxCall(searchTerm, searchResultsSize, searchStart) {
	const filterChecboxes = [];
	$(".filter-checkbox:checked").each(function(){
		filterChecboxes.push($(this).val());
	});
	let filters = filterChecboxes.join('=').toString();
		$.ajax({
			type: "GET",
			url: hostUrl + "/content.solrsearch.json?searchText=" + searchTerm + "&start=" + searchStart + "&rows=" + searchResultsSize + "&fq=" + filters + "&wt=json" ,
			dataType: "json",
			success: function(result, status, xhr, index) {
				if (result.length == 0) {
					$("#resultCount").empty();
					$("#displayedResult").empty();
					$("#resultCount").append("NO MATCHING RESULTS");
				} else {
					if (result.length == 1) {
						$("#resultCount").empty();
						$("#displayedResult").empty();
						$("#resultCount").append(result[0]["totalNumber"] + " RESULT");
					} else {
						
						$("#resultCount").empty();
						$("#displayedResult").empty();
						$("#resultCount").append(result[0]["totalNumber"] + " RESULTS");
					}
                                 
                    
					$.each(result, function(index) {
						
							result1.push(result[index]);
					});
					// result1 = [...result1, result]
					
					templateResult(result1);
					

					//LOAD-MORE
					//var resultCount = 5;
					var resultCount = searchResultsSize;
					var serchSecTerm = parseInt(searchStart) + parseInt(searchResultsSize);

					var totalItems = $('.rsp-resultCard').length;
					if (result[0]["totalNumber"] <= serchSecTerm) {
						$("#displayedResult").append("(1 - " + result[0]["totalNumber"] + " Displayed" + ")");
					} else {
						$("#displayedResult").append("(1 - " + serchSecTerm + " Displayed" + ")");
					}



					// $(".result-listView .rsp-resultCard").slice(0, resultCount).css('display', 'flex');
					// $(".result-gridView .rsp-resultCard").slice(0, resultCount).css('display', 'block');


					if (result[0]["totalNumber"] <= serchSecTerm) {
						//if (result.length <= resultCount) {
						$("#loadResults").css('display', 'none');
					} else {
						$("#loadResults").show();
					}

				}
			},
			error: function(xhr, status, error) {
				console.log("Result: " + status + " " + error + " " + xhr.status + " " + xhr.statusText)
			}
		});
	}
	
	$("#loadResults").click(function() {
		$("#resultsContainer").empty();
		$("#resultCount").empty();
		$("#displayedResult").empty();
		if ($("#rsp-searchInput").val() == "") {
		alert("please enter the search term");
		} else {
		searchTerm = $("#rsp-searchInput").val();
		searchStart = parseInt(searchStart) + parseInt(searchResultsSize);
		ajaxCall(searchTerm, searchResultsSize, searchStart);
		}

	});
	

	$(".fiter-opt--reset").click(function(){		
		$('input[type=checkbox]').each(function(index, checkbox){
			if(index != 0){
				checkbox.checked =false;
 			}
		});
	});
}
export default initializeSolrSearch;