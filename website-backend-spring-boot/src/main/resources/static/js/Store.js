const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("store", "api/products"))
let imageUrl = new URL(currentUrl.replace("store", "api/images/"))

let products = null;

/**
 * We want to wait for the window to first load
 */
window.onload = function () {
    getProducts()
}

/**
 *
 */
function getProducts() {
    let httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", productUrl)
    httpRequest.send();

    httpRequest.onreadystatechange = function () {

        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            products = JSON.parse(httpRequest.responseText);
            nextStep();
        }
    }
}

function nextStep() {
    const cloneAll = ".p-item"
    const templateMen = ".p-men"
    const templateWomen = ".p-women"

    const forAll ="All"
    const forMen = "Men";
    const forWomen = "Women";

    const templateAll = "#template-product2"
    addProducts(cloneAll, forAll, templateAll)
    addProducts(".p-men", "Men", "#template-product3")
    addProducts(".p-women", "Women", "#template-product4")
}

function addProducts(CloneName, nameOfCategory, templateName) {

    let template = document.querySelector(CloneName)
    for (let i = 0; i < products.length; i++ && i < 20) {

        let categories = products[i].categories

        for (let categoryNumber = 0; categoryNumber < categories.length; categoryNumber++) {

            if (categories[categoryNumber] === nameOfCategory) {


                //We make a clone of the .productCard element
                let clonedItemCard = template.cloneNode(true);

                //Image
                let image = clonedItemCard.querySelector(".p-img");
                if (products[i].images.length > 0) {
                    image.src = (imageUrl + products[i].images[0].imageId);
                } else {
                    image.src = "";
                }

                //Name
                let name = clonedItemCard.querySelector(".p-name");
                name.innerHTML = products[i].name;

                //Description
                let description = clonedItemCard.querySelector(".p-desc");
                description.innerHTML = products[i].description;

                //Price
                let price = clonedItemCard.querySelector(".p-price");
                price.innerHTML = products[i].price + " kr";

                //button
                let button = clonedItemCard.querySelector(".p-add");
                button.name = products[i].id

                //Checks if is the first loop
                if (i === 0) {
                    //we want to replace the cloned element
                    //so there is no unused element.
                    template.replaceWith(clonedItemCard);
                }
                //Adds the cloned element
                document.querySelector(templateName).appendChild(clonedItemCard)
            }
        }

    }


}


