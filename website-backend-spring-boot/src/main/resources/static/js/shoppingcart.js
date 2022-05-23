// variables

const cartBtn = document.querySelector('.cart-btn')
const closeCartBtn = document.querySelector('.close-cart')
const clearCartBtn = document.querySelector('.clear-cart')
const cartDOM = document.querySelector('.cart')
const cartOverlay = document.querySelector('.cart-overlay')
const cartItems = document.querySelector('.cart-items')
const cartTotal = document.querySelector('.cart-total')
const cartContent = document.querySelector('.cart-content')
const productsDOM = document.querySelector('.products-center')

// shopping cart
let cart = [];

// responsible for getting the products
class Products {
    getProducts() {
        fetch('products.json')
    }
}

// display products - responsible for getting all the items that are being returned from the products and displaying them
class UI {

}

// local storage
class Storage {

}

// creates instances of the classes
document.addEventListener("DOMContentLoaded", ()=>{
    const ui = new UI();
    const products = new Products();
})