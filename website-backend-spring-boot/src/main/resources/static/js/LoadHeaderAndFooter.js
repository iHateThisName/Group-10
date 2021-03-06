/**
 * JavaScript that uses jQuery to load html files in a html file
 * This is to prevent unnecessary HTML files on each page, when we use the same header and footer anyways.
 */

$(document).ready(function () {
    $(function(){
        $("#header").load("/api/header", function (response, status, xhr) {
            if (status === "error"){
                //It failed to receive from Get from "/api/Header"
                //Assume that it is because work on frontend with no backend support
                //Therefore able to load it like shown below.
                $("#header").load("Header.html");
            }
        });
        $("#footer").load("/api/footer", function (response, status, xhr) {
            if (status === "error") {
                //The same reason as the Header
                $("#footer").load("Footer.html");
            }
        })
    });
})
