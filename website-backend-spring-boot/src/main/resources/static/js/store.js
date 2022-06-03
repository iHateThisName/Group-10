const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("store", "api/products"))
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
    console.log(products[0].name)
}




