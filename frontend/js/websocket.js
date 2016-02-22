
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
      var chartData = null;
      var selectedChart =  "bar";
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
           if (responseData.suggestions) {
             for (var i =0 ; i < responseData.suggestions.length; i++) {
               avaialableSuggestions.push(responseData.suggestions[i].text);
               suggestionsMapper[avaialableSuggestions[i].toLowerCase().trim()] = responseData.suggestions[i];
             }
            populateAutocompleteSuggestions(responseData.suggestions);
        }
        else {
          window.gdvf.hisCom.newMessage(responseData);
        }

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
              resetUI();
              $('.loader').css('display', 'block');
              sendPostRequest(ui.item.object);
          }
      });
    }

    function initChartTooltip(data) {
        if (data.length > 20) {
          $(".chartIcon").attr("title", "Chart can't be created with this data!");
          return false;
        }
        else {
          $(".barChart").attr("title", "Bar Chart");
          $(".pieChart").attr("title", "Pie Chart");
          $(".donutChart").attr("title", "Donut Chart");
        }
        return true;
    }

    function sendPostRequest (suggestionObject) {
      if (suggestionObject) {
        $('.loader').css('display', 'block');
        $(".no-records").css("display" , "none");
        $.ajax({
          type: "POST",
          url: "http://169.45.107.190:5000/queryhandler/",
          contentType: "application/json",
          data: JSON.stringify(suggestionObject),
          success: function(responseData) {
            console.log(responseData);
            $('.loader').css('display', 'none');
            // $(".no-records").css("display" , "none");
            if (responseData.success && responseData.data) {
              chartData = responseData.data;
              var isChartDrawPossible =  initChartTooltip(chartData);
              if (isChartDrawPossible) {
                drawChart("bar", responseData.data);
              }
              createTable(responseData.data);
            }
            else {
              $(".no-records").css("display" , "block");
            }
          },
          error: function(err){
            $('.loader').css('display', 'none');
            console.log('Callback Error: ' + err);
          },
          dataType: "json"
        });
      }
    }

    $("#barChart").click(function() {
      selectedChart = "bar";
      if (chartData) createBarChart(chartData);
    });

    $("#donutChart").click(function() {
      selectedChart = "donut";
      if (chartData && chartData.length <=20 ) createPieChart(chartData, false);
    });

    $("#pieChart").click(function() {
      selectedChart = "pie";
      if (chartData && chartData.length <= 20) createPieChart(chartData, true);
    });

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
