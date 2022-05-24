
// JavaScript that uses jQuery to load html files in a html file

$(document).ready(function () {
    $(function(){
        $("#header").load("/api/Header");
        $("#footer").load("/api/Footer");
    });
})

//We use This so we can work with the html files without using the backend
//Todo delete this when done
$(document).ready(function () {
    $(function(){
        $("#header").load("Header.html");
        $("#footer").load("Footer.html");
    });
})

