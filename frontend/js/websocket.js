
    if (!("WebSocket" in window)) {
        $('#chatLog, input, button, #examples').fadeOut("fast");
        $('<p>Oh no, you need a browser that supports WebSockets. How about <a href="http://www.google.com/chrome">Google Chrome</a>?</p>').appendTo('#container');
    }
    else {
      var suggestionsMapper = {};
      var webSocket;
      var host = "ws://169.44.61.115:9090/ws";
      // var host = "ws://192.168.1.50:9090/ws";
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
             suggestionsMapper[avaialableSuggestions[i].toLowerCase().trim()] = responseData.suggestions[i];
           }
          populateAutocompleteSuggestions(responseData.suggestions);
       }
      }

    function populateAutocompleteSuggestions(data) {
      $("#searchBox").autocomplete({
          source: function (request, response) {
            response($.map(data, function (el) {
                return {
                    label: el.text,
                    value: el.text,
                    object: el
                };
            }));
          },
          select: function (event, ui) {
              // Prevent value from being put in the input:
              this.value = ui.item.label;
              console.log('value: ' , this.value);
              // Set the next input's value to the "value" of the item.
              $(this).next("#searchBox").val(ui.item.value);
              event.preventDefault();
              $(".tableData").empty();
              $('#totalRows').empty();
              $('.loader').css('display', 'block');
              sendPostRequest(ui.item.object);
          }
      });
    }

    function sendPostRequest (suggestionObject) {
      if (suggestionObject) {
        $.ajax({
          type: "POST",
          url: "http://169.45.107.190:5000/queryhandler/",
          contentType: "application/json",
          data: JSON.stringify(suggestionObject),
          success: function(responseData) {
            if (responseData.data) {
              drawChart("donut", responseData.data);
              createTable(responseData.data);
            }
          },
          error: function(err){
            console.log('Callback Error: ' + err);
          },
          dataType: "json"
        });
      }
    }

    function drawChart(chartType, data){
      if(chartType.toLowerCase().trim() == "pie") createPieChart(data, true);
      else if(chartType.toLowerCase().trim() == "donut") createPieChart(data, false);
      else if(chartType.toLowerCase().trim() == "bar") createBarChart(data);
    }

     function getSuggestionObject(queryString) {
        var searchKey = queryString.toLowerCase().trim();
        if(suggestionsMapper.length && suggestionsMapper[searchKey]) {
          return suggestionsMapper[searchKey];
        }
        else null;
     }
  }
