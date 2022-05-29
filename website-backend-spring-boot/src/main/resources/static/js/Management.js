const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("management", "api/products"))
const imageUrl = new URL(currentUrl.replace("management", "api/images/"))

window.onload = function () {
    loadProducts();
}

function loadProducts() {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", productUrl)
    xmlHttp.send();

    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {

            let products = JSON.parse(xmlHttp.responseText);
            let card = document.querySelector(".productCard");

            for (let i = 0; i < products.length; i++) {

                //We make a clone of the .productCard element
                let clonedCard = card.cloneNode(true);

                //Image
                let image = clonedCard.querySelector(".productCardImage");
                image.src = imageUrl + products[i].imageId;

                //Title
                let title = clonedCard.querySelector(".productCardName");
                title.innerHTML = products[i].name;

                //Price
                let price = clonedCard.querySelector(".productCardPrice");
                price.innerHTML = products[i].price + " kr";

                //Description
                let description = clonedCard.querySelector(".productCardDescription");
                description.innerHTML = products[i].description;

                //Checks if is the first loop
                if (i === 0) {
                    //we want to replace the cloned element
                    //so there is no unused element.
                    card.replaceWith(clonedCard);
                }
                //Adds the cloned element
                let addButton = document.getElementById("addProductCard");
                //we want to add the cloned element after the add button
                document.querySelector("#root").insertBefore(clonedCard, addButton)

            }
        }
    };
}






