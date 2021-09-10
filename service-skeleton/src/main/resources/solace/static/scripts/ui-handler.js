var broker = new PubSubPlusBroker();
var userName = 'user' + Math.floor((Math.random() * 1000) + 1);

/***CALLBACK HANDLERS***/
//alert handler
function alertHandler(bResult, sMessage) {
    notify(sMessage);
}


//call back function to handle intial connections
function connectHandler(bResult, sMessage) {
    console.debug("In connectHandler");

    alertHandler(bResult, sMessage);
    if (bResult) {
        //we connected fine, now try and subscribe
        broker.subscribe(alertHandler);
        broker.onTopicMessage(messageHandler);
    }


}

//handles messages pulled from the broker
function messageHandler(sMessage) {
    console.debug("message handler called with text: " + sMessage);
    notify("Trade emitted: " + sMessage);
}

/***HELPER METHODS***/
//updates the chat window with new text
function notify(sText) {
    var currentTime = "<span class='time'>" + moment().format('HH:mm:ss.SSS') + "</span>";
    var element = $("<div>" + currentTime + " " + sText + "</div>");
    $('#console').prepend(element);
}


function connectToSolace() {
    //try to connect
    broker.connect(connectHandler);
}

/***Button functions***/
$(document).ready(function () {
    $(document).keydown(function (e) {
        if (e.keyCode == 13) {
            $('#send').click();
        }
    });


    /*
     *Called when a user sends a message
     */
    $("#send").click(function () {
        var $orderType = $('#orderType');
        var orderType = $orderType.val();
        var jsonObject = {account: userName, action: orderType, price: 1.0, quantity: 1};
        var orderString = JSON.stringify(jsonObject);
        notify("Placing order: " + orderString);

        console.debug(orderString);

        //attempt a publish to the broker topic
        broker.publish(orderString, alertHandler)
    })

    $("#replay").click(function () {
        console.log("starting replay!")
        broker.startReplay();
    })
});

