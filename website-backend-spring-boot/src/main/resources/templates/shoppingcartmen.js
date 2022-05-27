var cart2 = {
    // properties
    hPdt2 : null, // html products list
    hItems : null,    // html current cart
    items : {},       // current items in cart
    iURL : "/website-backend-spring-boot/src/main/resources/static/images/products/", // product image url folder

    // localstorage cart
    // save current cart into storage
    save : () => {
        localStorage.setItem("cart", JSON.stringify(cart2.items));
    },

    // load cart from localstorage
    load : () => {
        cart2.items = localStorage.getItem("cart");
        if (cart2.items == null) { cart2.items = {}; }
        else { cart2.items = JSON.parse(cart2.items); }
    },

    // empty entire cart
    nuke : () => {
        if (confirm("Empty cart?")) {
            cart2.items = {};
            localStorage.removeItem("cart");
            cart2.list();
        }},


    // initialize
    init : () => {
        // get html elements
        cart2.hPdt2 = document.getElementById("cart-products2");
        cart2.hItems = document.getElementById("cart-items");

        // draw products list
        cart2.hPdt2.innerHTML = "";
        let template = document.getElementById("template-product").content,
            p, item, part;
        for (let id in productsmen) {
            p = productsmen[id];
            item = template.cloneNode(true);
            item.querySelector(".p-img").src = cart2.iURL + p.img;
            item.querySelector(".p-name").textContent = p.name;
            item.querySelector(".p-desc").textContent = p.desc;
            item.querySelector(".p-price").textContent = p.price + ",-";
            item.querySelector(".p-add").onclick = () => { cart2.add(id); };
            cart2.hPdt2.appendChild(item);
        }


        // load cart from previous session
        cart2.load();

        // list current cart items
        cart2.list();
    },

    // list current cart items in html
    list : () => {
        // reset
        cart2.hItems.innerHTML = "";
        let item, part, pdt, empty = true;
        for (let key in cart2.items) {
            if (cart2.items.hasOwnProperty(key)) {
                empty = false;
                break; }
        }

        // cart is empty
        if (empty) {
            item = document.createElement("div");
            item.innerHTML = "Cart is empty";
            cart2.hItems.appendChild(item);
        }

        // cart is not empty - list items
        else {
            let template = document.getElementById("template-cart").content,
                p, total = 0, subtotal = 0;
            for (let id in cart2.items) {
                // product item
                p = productsmen[id];
                item = template.cloneNode(true);
                item.querySelector(".c-del").onclick = () => { cart2.remove(id); };
                item.querySelector(".c-name").textContent = p.name + " " + p.desc;
                item.querySelector(".c-qty").value = cart2.items[id];
                item.querySelector(".c-qty").onchange = function () { cart2.change(id, this.value); };
                cart2.hItems.appendChild(item);

                // subtotal
                subtotal = cart2.items[id] * p.price;
                total += subtotal;
            }

            // total amount
            item = document.createElement("div");
            item.className = "c-total";
            item.id = "c-total";
            item.innerHTML ="TOTAL: " + total + "kr";
            cart2.hItems.appendChild(item);

            // empty and checkout
            item = document.getElementById("template-cart-checkout").content.cloneNode(true);
            cart2.hItems.appendChild(item);
        }
    },

    // add item into cart
    add : (id) => {
        if (cart2.items[id] == undefined) { cart2.items[id] = 1; }
        else { cart2.items[id]++; }
        cart2.save(); cart2.list();
    },

    // change quantity
    change : (pid, qty) => {
        // remove item
        if (qty <= 0) {
            delete cart2.items[pid];
            cart2.save(); cart2.list();
        }

        // update total only
        else {
            cart2.items[pid] = qty;
            var total = 0;
            for (let id in cart2.items) {
                total += cart2.items[id] * productsmen[id].price /*|| productsmen[id].price*/;
                document.getElementById("c-total").innerHTML ="TOTAL: kr" + total;
            }
        }
    },

    // remove item from cart
    remove : (id) => {
        delete cart2.items[id];
        cart2.save();
        cart2.list();
    },

    // checkout
    checkout : () => {
        alert("Order has been placed.");
        cart2.items = {};
        localStorage.removeItem("cart");
        cart2.list();
    }


};
window.addEventListener("DOMContentLoaded", cart2.init);
