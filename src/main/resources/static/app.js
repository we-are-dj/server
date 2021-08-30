var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/ws_stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({
        "access_token" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJXRV9BUkVfREoiLCJleHAiOjE2MzAxODYwMjYsIm1lbWJlcklkIjoiMzMifQ.szQbUCwR0pDYG3JcrBo2pN0-9Xvc0dNNuVVhAeVSkgw"
    }, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/sub/room', function (greeting) {
            console.log(greeting)
            showGreeting(JSON.parse(greeting.body).message);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/pub/chat/message", {
        "access_token" : "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJXRV9BUkVfREoiLCJleHAiOjE2MzAxODYwMjYsIm1lbWJlcklkIjoiMzMifQ.szQbUCwR0pDYG3JcrBo2pN0-9Xvc0dNNuVVhAeVSkgw"
    }, JSON.stringify({
        "type":"JOIN",
        "roomId":"5947ee76-d5c1-4cb6-9a9e-e68f1fefdc74",
        "sender":"123",
        "message":"123123"
    }));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});