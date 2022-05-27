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
        cart2.hItems2 = document.getElementById("cart-items2");


        // draw products list
        cart2.hPdt2.innerHTML = "";
        let template = document.getElementById("template-product").content,
            p2, item, part;
        for (let id in productsmen) {
            p2 = productsmen[id];
            item = template.cloneNode(true);
            item.querySelector(".p-img").src = cart2.iURL + p2.img;
            item.querySelector(".p-name").textContent = p2.name;
            item.querySelector(".p-desc").textContent = p2.desc;
            item.querySelector(".p-price").textContent = p2.price + ",-";
            item.querySelector(".p-add").onclick = () => { cart2.add(id); };
            cart2.hPdt2.appendChild(item);
        }


        // load cart from previous session
        cart2.load();

        // list current cart items
        cart2.list();
    },


};
window.addEventListener("DOMContentLoaded", cart2.init);
