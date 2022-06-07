const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("management", "api/products"))
const imageUrl = new URL(currentUrl.replace("management", "api/images/"))


let previewImageElement = null;
let chosenImage = null;



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

    let newAddCard = document.getElementById("displayedAddProduct");

    //Hide the save button
    newAddCard.querySelector(".save-button-add-production-card").style.display = "none"

    //Setting up event listener for the 3 input areas and the save button
    setUpEventForProductCardEditable(newAddCard);

}

//Letting the user view a preview
function inputImage(event) {

    let addCard = document.getElementById("displayedAddProduct");

    if (event.target.files.length > 0) {
        chosenImage = event.target.files[0];
        console.log(chosenImage.name);
        let src = URL.createObjectURL(chosenImage)

        console.log(src)


        console.log("Image changed");

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

            newAddCard.querySelector(".product-card-content").style.marginRight = 0;
            newAddCard.querySelector(".save-button-add-production-card").style.display = "block"

        }
    }

    function eventSaveProduct() {

        let saveButton = newAddCard.querySelector(".save-button-add-production-card");

        console.log(new Uint8Array(chosenImage));

        saveButton.onclick = function () {

            //Check if the save button is visible
            if (newAddCard.querySelector(".save-button-add-production-card").style.display === "block") {

                let http = new XMLHttpRequest();
                http.open("POST", productUrl);

                http.setRequestHeader("Accept", "application/json");
                http.setRequestHeader("Content-Type", "application/json");

                let data = {

                    name: nameInput.value,
                    description: desInput.value,
                    price: priceInput.value,
                    categories: ["All"],
                    images: [{
                        extension: chosenImage.name.split(".").pop(),
                        contentType: ("image/" + chosenImage.name.split(".").pop())
                    }]
                }
                http.send(JSON.stringify(data));
            }
        }
    }
}










