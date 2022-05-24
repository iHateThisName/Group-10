
// JavaScript that uses jQuery to load html files in a html file

$(document).ready(function () {
    $(function(){
        $("#header").load("/api/Header");
        $("#footer").load("/api/Footer");
    });
})

