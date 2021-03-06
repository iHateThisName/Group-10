const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("management", "api/products"))
const imageUrl = new URL(currentUrl.replace("management", "api/images/"))


let previewImageElement = null;
let chosenImage = null;


/**
 * We wait for the window to load before we load the products.
 */
window.onload = function () {

    loadProducts();
}

/**
 * We load the products
 */
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
                if (products[i].images.length > 0) {
                    image.src = imageUrl + products[i].images[0].imageId;
                } else {
                    image.src = "";
                }

                //Title
                let title = clonedCard.querySelector(".productCardName");
                title.innerHTML = products[i].name;

                //Price
                let price = clonedCard.querySelector(".productCardPrice");
                price.innerHTML = products[i].price + " kr";

                //Description
                let description = clonedCard.querySelector(".productCardDescription");
                description.innerHTML = products[i].description;

                //Delete button
                let delButton = clonedCard.querySelector(".actionButtonDelete");
                delButton.title = ("Delete " + products[i].name + " with the id " + products[i].id).toString()

                //Edit button
                let editButton = clonedCard.querySelector(".actionButtonEdit");
                editButton.title = ("Edit " + products[i].name + "with the id " + products[i].id);

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

/**
 * OnClick event listener for the plus button.
 * It will create the new card called the addProductCard.
 * On this card the user can provide information on a new product.
 * When it is finished it will call the method setUpEventForProductCardEditable.
 */
function addClick() {

    //Retrieve the template that is hidden.
    let addCard = document.querySelector(".productAddCard");

    //We make a clone of the .productAddCard element
    let clonedAddCard = addCard.cloneNode(true);
    //We change the display style so it is visible
    clonedAddCard.style.display = "inherit";
    //We change the id, so it should be easier to retrieve later
    clonedAddCard.id = "displayedAddProduct"
    //We add the "AddCard" before the plus button
    document.querySelector("#root")
        .insertBefore(clonedAddCard, (document.getElementById("addProductCard")));

    //Hiding the plus button
    document.getElementById("addProductCard").style.display = "none"

    let newAddCard = document.getElementById("displayedAddProduct");

    //Hide the save button
    newAddCard.querySelector(".save-button-add-production-card").style.display = "none"

    //Setting up event listener for the 3 input areas and the save button
    setUpEventForProductCardEditable(newAddCard);

}

/**
 * OnClick event listener for the "Choose image" button.
 * The method will provide a preview of the image the user selects on the add product card.
 * @param event
 */
//Letting the user view a preview
function inputImage(event) {

    let addCard = document.getElementById("displayedAddProduct");

    if (event.target.files.length > 0) {
        chosenImage = event.target.files[0];
        let src = URL.createObjectURL(chosenImage)

        previewImageElement = document.createElement("img")
        previewImageElement.src = src;
        previewImageElement.style.display = "block";

        let previewDiv = addCard.querySelector(".image-editor-preview")

        while (previewDiv.firstChild) {
            previewDiv.removeChild(previewDiv.lastChild)
        }

        previewDiv.appendChild(previewImageElement);

    }

}

function setUpEventForProductCardEditable(newAddCard) {

    let element = newAddCard.querySelector(".product-card-content");

    let textName = false;
    let textPrice = false;
    let textDesc = false;

    //Setting up event listener for the save button
    eventSaveProduct()


    let nameInput = element.querySelector("#name-input");
    nameInput.oninput = function () {

        if (nameInput.value.length > 0) {
            textName = true
            showSaveButton()
        }
    }

    let priceInput = element.querySelector("#price-input");
    priceInput.oninput = function () {

        if (priceInput.value.length > 0) {
            textPrice = true;
            showSaveButton()
        }
    }


    let desInput = element.querySelector("#description-input")
    desInput.oninput = function () {

        if (desInput.value.length > 0) {
            textDesc = true;
            showSaveButton()
        }
    }


    function showSaveButton() {

        if (textName && textPrice && textDesc) {

            newAddCard.querySelector(".product-card-content").style.marginRight = "0";
            newAddCard.querySelector(".save-button-add-production-card").style.display = "block"

        }
    }

    /**
     * This is setting up an event listener
     * for the save button on the add product card.
     */

    function eventSaveProduct() {

        let saveButton = newAddCard.querySelector(".save-button-add-production-card");

        saveButton.onclick = function () {

            //Check if the save button is visible
            if (newAddCard.querySelector(".save-button-add-production-card").style.display === "block") {

                console.log("Saving product")

                let http = new XMLHttpRequest();
                http.open("POST", productUrl);

                http.setRequestHeader("Accept", "application/json");
                http.setRequestHeader("Content-Type", "application/json");

                let productData;

                if (chosenImage != null) {
                    const imageExtension = chosenImage.name.split(".").pop();
                    const imageType = "image/" + imageExtension;

                    productData = {

                        name: nameInput.value,
                        description: desInput.value,
                        price: priceInput.value,
                        categories: ["All"],
                        images: [{
                            extension: imageExtension.toString(),
                            contentType: imageType.toString()
                        }]
                    }
                } else {

                    productData = {

                        name: nameInput.value,
                        description: desInput.value,
                        price: priceInput.value,
                        categories: ["All"],
                    }
                }
                http.send(JSON.stringify(productData));

                http.onreadystatechange = function () {
                    if (http.readyState === 4 && http.status === 200) {

                        //removing the add card
                        newAddCard.remove();
                        //displaying the + button
                        document.getElementById("addProductCard").style.display = "block"

                        //Get a collection of the product cards
                        let productCards = document.querySelectorAll(".productCard");

                        let keepFirstElement = true;
                        //deleting every productCard, but not the first one
                        productCards.forEach(value => {

                            if (!keepFirstElement) {
                                value.remove()
                            } else {
                                keepFirstElement = false;
                            }
                        })

                        loadProducts();

                        console.log("Product is stored")
                    }
                }
            }
        }
    }
}

function handleClickDelete (title) {

    console.log(title.replace("Delete", "Deleting"));

    //the id is the last char in the string
    let productId = title.charAt(title.length - 1);
    //the api address to delete a product
    let deleteProductString = currentUrl.replace("management", "api/products/");
    //adding the product id
    deleteProductString = deleteProductString + productId;

    let http = new XMLHttpRequest();
    http.open("DELETE", deleteProductString);
    http.send();

    location.reload();

}

function handleClickEdit(title) {

    console.log(title.replace("Edit", "Editing"));

    alert("We are sorry to inform you that the edit function are not implemented")
}












