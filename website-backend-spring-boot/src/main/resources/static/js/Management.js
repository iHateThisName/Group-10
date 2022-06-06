const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("management", "api/products"))
const imageUrl = new URL(currentUrl.replace("management", "api/images/"))
let card = document.querySelector(".productCard");


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

            for (let i = 0; i < products.length; i++) {

                //We make a clone of the .productCard element
                let clonedCard = card.cloneNode(true);

                //Image
                let image = clonedCard.querySelector(".productCardImage");
                image.src = imageUrl + products[i].images[0].imageId;

                //Title
                let title = clonedCard.querySelector(".productCardName");
                title.innerHTML = products[i].name;

                //Price
                let price = clonedCard.querySelector(".productCardPrice");
                price.innerHTML = products[i].price + " kr";

                //Description
                let description = clonedCard.querySelector(".productCardDescription");
                description.innerHTML = products[i].description;

                //Action Button
                let edit = clonedCard.querySelector(".actionButtonEdit");
                edit.addEventListener("click", editClick(this, products[i].id))

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

function editClick(event, productId) {

}

function addClick() {
    console.log("Add button was pressed")

    let addCard = document.querySelector(".productAddCard");

    //We make a clone of the .productAddCard element
    let clonedAddCard = addCard.cloneNode(true);
    clonedAddCard.style.display = "inherit";

    clonedAddCard.id = "displayedAddProduct"

    document.querySelector("#root")
        .insertBefore(clonedAddCard, (document.getElementById("addProductCard")));

    document.getElementById("addProductCard").style.display = "none"

}

function inputImage(event) {

    if (event.target.files.length > 0) {
        let chosenImage = event.target.files[0];
        let src = URL.createObjectURL(chosenImage)


        console.log("Image changed");

        let previewImageElement = document.createElement("img")
        previewImageElement.src = src;
        previewImageElement.style.display = "block";

        let addCard = document.getElementById("displayedAddProduct");
        let previewDiv = addCard.querySelector(".image-editor-preview")

        while (previewDiv.firstChild) {
            previewDiv.removeChild(previewDiv.lastChild)
        }

        previewDiv.appendChild(previewImageElement);


    }

}






