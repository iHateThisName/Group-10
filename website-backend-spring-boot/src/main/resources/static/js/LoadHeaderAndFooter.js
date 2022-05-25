
// JavaScript that uses jQuery to load html files in a html file

$(document).ready(function () {
    $(function(){
        $("#header").load("/api/Header", function (response, status, xhr) {
            if (status === "error"){
                //It failed to receive from Get from "/api/Header"
                //Assume that it is because work on frontend with no backend support
                //Therefore able to load it like shown below.
                $("#header").load("Header.html");
            }
        });
        $("#footer").load("/api/Footer", function (response, status, xhr) {
            if (status === "error") {
                //The same reason as the Header
                $("#footer").load("Footer.html");
            }
        })
    });
})
