// Defines the authentication token:
var AUTHC_TOKEN = null;


// Retrieves the version of the application and the api, and shows them.
function showContent () {
    $.ajax({
        url: "api/version",
        headers: {
            "Authorization": "Token " + AUTHC_TOKEN
        },
        dataType: "json",
        success: function (data) {
            $("#content").html("<div class=lead>APP v" + data.app + ", API v" + data.api + "</div>");
        },
        error: function () {
            alert("Problem while retrieving the version.");
        }
    })
}


// Logs in the user and calls the callback function on successful login.
function login (username, password, callback) {
    $.ajax({
        url: "api/login",
        headers: {
            "Authorization": "Basic " + btoa(username + ":" + password)
        },
        dataType: "json",
        success: function (data) {
            AUTHC_TOKEN = data.authctoken;
            callback();
        },
        error: function () {
            alert("Cannot login. Please check your credentials or contact system administrator.");
        }
    })
}


// Document onload functionality:
$(document).ready(function () {
    $("button").click(function () {
        login($("#username").val(), $("#password").val(), showContent);
    });
});
