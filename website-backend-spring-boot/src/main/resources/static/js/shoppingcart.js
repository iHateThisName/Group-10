let imageApi = currentUrl.replace("store", "api/images/")

var cart = {
    // properties
    hPdt : null, // html products list
    hItems : null,    // html current cart
    items : {},       // current items in cart
    iURL : imageApi, // product image url folder

    // localstorage cart
    // save current cart into storage
    save : () => {
        localStorage.setItem("cart", JSON.stringify(cart.items));
    },

    // load cart from localstorage
    load : () => {
        cart.items = localStorage.getItem("cart");
        if (cart.items == null) { cart.items = {}; }
        else { cart.items = JSON.parse(cart.items); }
    },

    // empty entire cart
    nuke : () => {
        if (confirm("Empty cart?")) {
        cart.items = {};
        localStorage.removeItem("cart");
        cart.list();
    }},


    // initialize
    init : () => {
        // get html elements
        cart.hPdt = document.getElementById("cart-products");
        cart.hItems = document.getElementById("cart-items");

        // draw products list
        cart.hPdt.innerHTML = "";
        let template = document.getElementById("template-product").content,
            i, item, part;
        for (let id in storeproducts) {
            i = storeproducts[id];
            item = template.cloneNode(true);
            item.querySelector(".i-img").src = cart.iURL + i.img;
            item.querySelector(".i-name").textContent = i.name;
            item.querySelector(".i-desc").textContent = i.desc;
            item.querySelector(".i-price").textContent = i.price + ",-";
            item.querySelector(".i-add").onclick = () => { cart.add(id); };
            cart.hPdt.appendChild(item);
        }

        // load cart from previous session
        cart.load();

        // list current cart items
        cart.list();

    },


    // list current cart items in html
    list : () => {
        // reset
        cart.hItems.innerHTML = "";
        let item, part, pdt, empty = true;
        for (let key in cart.items) {
            if (cart.items.hasOwnProperty(key)) {
                empty = false;
                break; }
        }

        // cart is empty
        if (empty) {
            item = document.createElement("div");
            item.innerHTML = "Cart is empty";
            cart.hItems.appendChild(item);
        }

        // cart is not empty - list items
        else {
            let template = document.getElementById("template-cart").content,
                i, total = 0, subtotal = 0;
            for (let id in cart.items) {
                // product item
                i = storeproducts[id];
                item = template.cloneNode(true);
                item.querySelector(".c-del").onclick = () => { cart.remove(id); };
                item.querySelector(".c-name").textContent = i.name + " " + i.desc;
                item.querySelector(".c-qty").value = cart.items[id];
                item.querySelector(".c-qty").onchange = function () {
                    cart.change(id, this.value);
                };
                cart.hItems.appendChild(item);

                // subtotal
                subtotal = cart.items[id] * i.price;
                total += subtotal;
            }

            // total amount
            item = document.createElement("div");
            item.className = "c-total";
            item.id = "c-total";
            item.innerHTML ="TOTAL: " + total + "kr";
            cart.hItems.appendChild(item);

            // empty and checkout
            item = document.getElementById("template-cart-checkout").content.cloneNode(true);
            cart.hItems.appendChild(item);
        }
    },

    // add item into cart
    add : (id) => {
        if (cart.items[id] == undefined) {
            cart.items[id] = 1;
        }
        else {
            cart.items[id]++;
        }
        cart.save();
        cart.list();
    },


    // change quantity
    change : (pid, qty) => {
        // remove item
        if (qty <= 0) {
            delete cart.items[pid];
            cart.save();
            cart.list();
        }

        // update total only
        else {
            cart.items[pid] = qty;
            var total = 0;
            for (let id in cart.items) {
                total += cart.items[id] * products[id].price;
                document.getElementById("c-total").innerHTML ="TOTAL: kr" + total;
            }
        }
    },

    // remove item from cart
    remove : (id) => {
        delete cart.items[id];
        cart.save();
        cart.list();
    },

    // checkout
    checkout : () => {
        alert("Order has been placed.");
        cart.items = {};
        localStorage.removeItem("cart");
        cart.list();
    }

};
window.addEventListener("DOMContentLoaded", cart.init);
