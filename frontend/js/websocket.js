
    if (!("WebSocket" in window)) {
        $('#chatLog, input, button, #examples').fadeOut("fast");
        $('<p>Oh no, you need a browser that supports WebSockets. How about <a href="http://www.google.com/chrome">Google Chrome</a>?</p>').appendTo('#container');
    }
    else {
      var suggestionsMapper = {};
      var webSocket;
      var host = "ws://192.168.1.50:9090/ws";
      var responseData = null;
      try {
          webSocket = new WebSocket(host);

          webSocket.onopen = function() {
            console.log("Connection is now open ....");
          }

          webSocket.onmessage = function(response) {
            responseData = response.data;
            console.log('Server respond with: ' + responseData);
            initSuggestion(responseData);

          }

          webSocket.onclose = function() {
            console.log('Connection closed ...');
          }

      } catch(exception) {
        alert('Error occured while connecting to ' + host + ' reason: ' + exception);
      }

      function showAlert() {
        if (webSocket.readyState == 0) alert("Connection has not yet been established!")
        else if (webSocket.readyState == 2) alert("Connection is going through the closing handshake!")
        else if (webSocket.readyState == 3) alert("connection has been closed or could not be opened!")
      }

      function sendData(data) {
          try {
              if (webSocket.readyState == 1) webSocket.send(data);
              else showAlert();
          } catch(exception) {
            alert('Error occured while sending data to ' + host + ' reason: ' + exception);
          }
      }

      function initSuggestion(responseData) {
        var avaialableSuggestions = [];
        responseData = JSON.parse(responseData);
        if (typeof responseData !== 'undefined') {
           for (var i =0 ; i < responseData.suggestions.length; i++) {
             avaialableSuggestions.push(responseData.suggestions[i].text);
             suggestionsMapper[avaialableSuggestions[i].toLowerCase()] = responseData.suggestions[i];
           }
           console.log("avaialableSuggestions: " , avaialableSuggestions);
           $("#searchBox").autocomplete({
               source: avaialableSuggestions
           });
       }
      }

     function getSuggestion(queryString){
        if(suggestionsMapper.length && suggestionsMapper[queryString.toLowerCase()]) return suggestionsMapper[queryString.toLowerCase()];
        else null; 
     }
  }
