const currentUrl = window.location.href;
const productUrl = new URL(currentUrl.replace("management", "api/products"))



function loadProducts() {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", productUrl)
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {

            let products = JSON.parse(xmlHttp.responseText);
            let table = document.getElementById("table")

            for (let i = 0; i < products.length; i++) {

                //making a new row in the table
                //inserting the new row after the exising
                const row = table.insertRow(table.rows.length)

                //making the cells for the table
                const idCell = row.insertCell(0);
                const nameCell = row.insertCell(1);
                const descriptionCell = row.insertCell(1);
                const priceCell = row.insertCell(1);

                //inserting information in the cells
                idCell.innerHTML = products[i].id;
                nameCell.innerHTML = products[i].name;
                descriptionCell.innerHTML = products[i].description;
                priceCell.innerHTML = products[i].price;
            }
        }
    };
    xmlHttp.send();
}
window.onload = function () {
    loadProducts();
}


