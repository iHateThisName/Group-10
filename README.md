# Group-10
Repository for group projects in Application Development and Web Technologies

# Postman link
https://app.getpostman.com/join-team?invite_code=f344611aff4b8b9f7d5fef37d86dc716&target_code=5ed7980a27f1926308d9ee873d3ea0c0

this link might not be valid after delivering the project, therefore we will add the postman files
to the main folder of our project,(also in the hand in zip)

# Intention
The intention with our project is to finalize a website application for a Hiking Equipment website.
It should have the functionalities that a web shop usually has, where you can access a store with products, login, register,
add products to cart, checkout and so on.

# Setup instructions
The way to launch the project, is that you launch the WebsiteApplication-class within the project. It will start the website
application, so that you can reach a functioning website at localhost:8080 in a browser.
If not, you can go to the domain of our web server, which is at https://gr10.appdev.cloudns.ph/.

What needs to be configured:

# Functions on the site

the site have back-end support, but the front-end dose not implement everything that the back-end provides.


### Navigation
The site has a lot of link in the Header,Footer and on some content pages. these links and buttons are
interacteble and takes you to other pages within the website. the buttons light up when hovering over them, indicating
that they are clickable. for the most part the pages are responsive, however some of them are not quite as responsive.

### Login
one can log in as a user,manager or admin. when logging in to the page you will get an access token
depending on what role that user has (admin, manager, user)

### register
when going to the register page(click top right of the header), you will be greeted with a form where
you can enter name, username and password. sadly the register tab is only for show and we cant add users from the
page. to access the site as a user, see the "USER'S ON THE SITE!!!" further down this document.

### Profile page
When you are logged in you get access to the profile page(click the icon in the top right in the header).
in the profile page it displays the user's name and username. there is also a tab that shows order history
sadly the order history is not implemented, but you will get greeted with a fake made up order just to show
how it would look if implemented.

Admins and managers also have access to the manager page where they can create, update and delete products
in the store, the manager or admin can access this page trough the profile page, the management tab will only
be visible for the admins and managers of the page.

When users want to log out of the website, simply click the "log out" button on the main profile page to log out.
when this happens the access token you got when logging in gets removed/deleted.

### Store page
the store page is unfortunetley lacking. and there is not much functionality from the back end, however we have added
products that are visible for the users, and it is possible to add them to a shopping cart. when added to the shopping
cart. it will display the item in the shopping cart tab(top right in the header). here we can either place order or
delete from shopping cart. the shopping cart does not actully do anything, it is just for show, to visualize how it would
work if it was implemented properly.

some of the products are delivered to the page through the back end of out program, while some are just for show to visualise the page.

# USER'S ON THE SITE!!!

this is quite important because we cant add users to the site via register. but we have created users of each role that you can use to
navigate our site. here they are

-------------------------------------------------
### Arnold - Super user (Has all roles)
#### username: arnold
#### password: 1234
-------------------------------------------------

### Jim - Admin
#### username: jim
#### password: 1234
-------------------------------------------------

### Will - Manager
#### username: will
#### password: 1234
-------------------------------------------------

### John - User
#### username: john
#### password: 1234
-------------------------------------------------
