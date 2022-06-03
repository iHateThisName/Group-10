var cart2 = {
    // properties
    hPdt : null, // html products list
    hItems : null,    // html current cart
    items : {},       // current items in cart
    iURL : "/website-backend-spring-boot/src/main/resources/static/images/products/", // product image url folder


    // localstorage cart
    // save current cart into storage
    save : () => {
        localStorage.setItem("cart2", JSON.stringify(cart.items));
    },

    // load cart from localstorage
    load : () => {
        cart.items = localStorage.getItem("cart2");
        if (cart.items == null) { cart.items = {}; }
        else { cart.items = JSON.parse(cart.items); }
    },

    // initialize
    init : () => {
        // get html elements
        cart2.hPdt = document.getElementById("cart-products2");
        cart2.hItems = document.getElementById("cart-items");

        // draw products list
        cart2.hPdt.innerHTML = "";
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
            cart2.hPdt.appendChild(item);
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
        let item2, part, pdt, empty = true;
        for (let key in cart.items) {
            if (cart.items.hasOwnProperty(key)) {
                empty = false;
                break; }
        }

        // cart is empty
        if (empty) {
            item2 = document.createElement("div");
            item2.innerHTML = "Cart is empty";
            cart.hItems.appendChild(item2);
        }

        // cart is not empty - list items
        else {
            let template = document.getElementById("template-cart").content,
                p, total = 0, subtotal = 0;
            for (let id in cart.items) {
                // product item
                p = productsmen[id];
                item2 = template.cloneNode(true);
                item2.querySelector(".c-del").onclick = () => { cart2.remove(id); };
                item2.querySelector(".c-name").textContent = p.name + " " + p.desc;
                item2.querySelector(".c-qty").value = cart2.items[id];
                item2.querySelector(".c-qty").onchange = function () { cart2.change(id, this.value); };
                cart.hItems.appendChild(item2);

                // subtotal
                subtotal = cart2.items[id] * p.price;
                total += subtotal;
            }

            // total amount
            item2 = document.createElement("div");
            item2.className = "c-total";
            item2.id = "c-total";
            item2.innerHTML ="TOTAL: " + total + "kr";
            cart.hItems.appendChild(item2);

            // empty and checkout
            item2 = document.getElementById("template-cart-checkout").content.cloneNode(true);
            cart.hItems.appendChild(item2);
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
            delete cart2.items[pid];
            cart2.save(); cart2.list();
        }

        // update total only
        else {
            cart2.items[pid] = qty;
            var total = 0;
            for (let id in cart2.items) {
                total += cart2.items[id] * productsmen[id].price || productsmen[id].price;
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
        localStorage.removeItem("cart2");
        cart2.list();
    }


};
window.addEventListener("DOMContentLoaded", cart2.init);
