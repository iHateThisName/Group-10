const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("management", "api/products"))

function loadProducts() {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", productUrl)
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            let products = JSON.parse(xmlHttp.responseText);

            let tableTop = "<table> " +
                "<tr> + " +
                "<th>Id</th>" +
                "<th>Name</th>" +
                "<th>Description</th>" +
                "<th>Price</th> + " +
                "</tr>";


            let main = "";
            for (let i = 0; i < products.length; i++) {

                main += "<tr>" +
                    "<td>" + products[i].id + "</td>" +
                    "<td>" + products[i].name + "</td>" +
                    "<td>" + products[i].description + "</td>" +
                    "<td>" + products[i].price + "</td>" +
                    "</tr>";
            }

            const tableBottom = "</table>";
            const table = tableTop + main + tableBottom;
            document.getElementById("root").innerHTML = table;

        }
    };
    xmlHttp.send();
}
window.onload = function () {
    loadProducts();
}


