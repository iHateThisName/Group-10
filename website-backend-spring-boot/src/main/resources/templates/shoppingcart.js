/*const currentURL = window.location.href;
const productURL = new URL(currentURL.replace("store", "api/products"))
productURL.toJSON()*/

var cart = {
    // properties
    hPdt : null, // html products list
    hItems : null,    // html current cart
    items : {},       // current items in cart
    iURL : "/website-backend-spring-boot/src/main/resources/static/images/products/", // product image url folder

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
            p, item, part;
        for (let id in products) {
            p = products[id];
            item = template.cloneNode(true);
            item.querySelector(".p-img").src = cart.iURL + p.img;
            item.querySelector(".p-name").textContent = p.name;
            item.querySelector(".p-desc").textContent = p.desc;
            item.querySelector(".p-price").textContent = p.price + ",-";
            item.querySelector(".p-add").onclick = () => { cart.add(id); };
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
                p, total = 0, subtotal = 0;
            for (let id in cart.items) {
                // product item
                p = products[id];
                item = template.cloneNode(true);
                item.querySelector(".c-del").onclick = () => { cart.remove(id); };
                item.querySelector(".c-name").textContent = p.name + " " + p.desc;
                item.querySelector(".c-qty").value = cart.items[id];
                item.querySelector(".c-qty").onchange = function () {
                    cart.change(id, this.value);
                };
                cart.hItems.appendChild(item);

                // subtotal
                subtotal = cart.items[id] * p.price;
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
