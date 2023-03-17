import axios from 'axios';
const initializeAssetlist = () => {
	let isToggle = false,
		showMoreText,
		showLesstext,
		moreItemsContainerEl,
		showAllContainerEl,
		showMoreContainerEl,
		showMoreButtonEl,
		showMoreIconEl,
		maxNoOfItems,
		requestUri,
		assetList;
	const init = (assetListEl) => {
		showLesstext = assetListEl.dataset.showLess;
		maxNoOfItems = assetListEl.dataset.maxItems;
		requestUri = assetListEl.dataset.requestUri;
		getAssetList(assetListEl);
	}

	const getAssetList = async (assetListEl) => {
		const infoBlock = assetListEl.querySelector(".assetlist__info");
		const loader = infoBlock.querySelector(".assetlist__info--loader");
		const noDataFound = infoBlock.querySelector(".assetlist__info--not-found");
		const arrow = assetListEl.querySelector(".showmore__border--btn");
		try {
			const response = await axios.get(requestUri);
			assetList = response.data;
			loader.classList.add("is-hidden");
			arrow.classList.add("is-hidden");
			if (assetList.length > 0) {
				const defaultItemArray = assetList.slice(0, 10);
				const defaultItem = buildItemsList(defaultItemArray);
				const defaultItemsBlock = assetListEl.querySelector(".assetlist__items");
				defaultItemsBlock.innerHTML = defaultItem;

				if (assetList.length > 10) {
					showMoreButtonHandler(assetListEl);
				}

			} else {
				noDataFound.classList.remove("is-hidden");
			}
		} catch (error) {
			loader.classList.add("is-hidden");
			noDataFound.classList.remove("is-hidden");
			console.error(error);
		}
	}
	const toggleShowMore = (event) => {
		let showLesstext = event.currentTarget.parentElement.dataset.showLess;
		let showMoreText = event.currentTarget.parentElement.dataset.showMore;

		if (isToggle) {
			isToggle = false;
			event.currentTarget.querySelector(".more-text").innerHTML = showMoreText;
			moreItemsContainerEl.classList.add("is-hidden");
			if (assetList.length > maxNoOfItems) {

				event.currentTarget.nextElementSibling.classList.add("is-hidden");

			}
			event.currentTarget.querySelector(".fa").classList.remove("fa-chevron-up");

		} else {
			isToggle = true;
			event.currentTarget.querySelector(".more-text").innerHTML = showLesstext;

			moreItemsContainerEl.classList.remove("is-hidden");
			if (assetList.length > maxNoOfItems) {

				event.currentTarget.nextElementSibling.classList.remove("is-hidden");

			}
			event.currentTarget.querySelector(".fa").classList.add("fa-chevron-up");

		}
	}
	const addMoreItems = (event) => {
		const sliceEndPos = maxNoOfItems;
		const moreItemsArray = assetList.slice(10, sliceEndPos);
		const moreItems = buildItemsList(moreItemsArray);
		moreItemsContainerEl.innerHTML = moreItems;
		toggleShowMore(event);
	}
	const showMoreButtonHandler = (assetListEl) => {
		showMoreText = assetListEl.dataset.showMore;
		if (assetList.length > 9) {
			showMoreContainerEl = assetListEl.querySelector(".showmore__border--btn");
			showMoreContainerEl.classList.remove("is-hidden");
			showMoreButtonEl = showMoreContainerEl.querySelector(".more-text");
			showMoreIconEl = showMoreContainerEl.querySelector(".fa");
			showAllContainerEl = assetListEl.querySelector(".showall__items");
			showMoreButtonEl.innerHTML = showMoreText;

			showMoreContainerEl.addEventListener("click", (event) => {
				event.stopPropagation();
				moreItemsContainerEl = assetListEl.querySelector(".assetlist__items-more");
				addMoreItems(event);
			});


		}
	}
	const buildItemsList = (itemsArray) => {
		let items = '';
		itemsArray.forEach((item, index) => {
			if (item) {
				items += `<a class="list-item" target="_blank" href=${item.path}>${item.title}</a>`;
			}
		});
		return items;
	}
	const assetListComponents = document.querySelectorAll(".dpssassetlist");
	const assetListArr = [...assetListComponents];
	if (assetListArr.length > 0) {
		assetListArr.forEach(el => {
			init(el);
		})
	}
}
export default initializeAssetlist;