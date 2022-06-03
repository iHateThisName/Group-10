const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("store", "api/products"))
let imageUrl = new URL(currentUrl.replace("store", "api/images/"))

let products = null;

window.onload = function () {
    getProducts()
}



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

    let template = document.querySelector(".p-item")
    for (let i = 0; i < products.length; i++) {

        //We make a clone of the .productCard element
        let clonedItemCard = template.cloneNode(true);

        //Image
        let image = clonedItemCard.querySelector(".p-img");
        image.src = (imageUrl + products[i].images[0].imageId);

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
        document.querySelector("#template-product").appendChild(clonedItemCard)

    }
}




