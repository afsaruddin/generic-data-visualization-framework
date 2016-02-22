var key = "" , value = "";
var getTooltipHTML = function (value) {
  console.log(value);
  var tooltipHTML = '<table><tr><td>' + value + '</td></tr></table>';

  return tooltipHTML;
};

$('.recent-query-icon').click(function () {
  if ($('.recent-query').is(':visible')) {
    $('#page-wrapper').css('-webkit-transform', 'translate(0%,0)');
    $( ".recent-query" ).hide( 500 );
    // $('#page-wrapper').css('margin', '0 auto');
  //  $('.recent-query').css('display', 'none');
  } else {
    $('#page-wrapper').css('-webkit-transform', 'translate(-20%,0)');
    $( ".recent-query" ).show( 500 );
    // $('#page-wrapper').css('margin-left', '30px');
  //  $('.recent-query').css('display', 'block');
  }

});

var resize = function () {
  if (selectedChart === "bar") {
    createBarChart(selectedChartData);
  }
  else if (selectedChart === "pie") {
    createPieChart(chartData, true);
  }
  else if (selectedChart === "donut") {
    createPieChart(chartData, false);
  }
};

d3.select(window).on('resize', resize);

function createBarChart(data) {

  d3.selectAll("svg").remove();
  if(!initKeyValue(data[0])) return;
  selectedChartData = data;
  var margin = {top: 20, right: 20, bottom: 30, left: 40},
    width = $('#page-wrapper').width() - margin.left - margin.right,
    height = 320 - margin.top - margin.bottom;

  var x = d3.scale.ordinal()
      .rangeRoundBands([0, width], .1);

  var y = d3.scale.linear()
      .range([height, 0]);

  var xAxis = d3.svg.axis()
      .scale(x)
      .tickFormat(function (d ) { return d.substring(0,2); })
      .orient("bottom");

  var yAxis = d3.svg.axis()
      .scale(y)
      .orient("left")
      .ticks(5);

  var svg = d3.select(".chart").append("svg")
      .attr("width", width + margin.left + margin.right)
      .attr("height", height + margin.top + margin.bottom)
    .append("g")
      .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

      x.domain(data.map(function(d) { return d[key]; }));
      y.domain([0, d3.max(data, function(d) { return d[value]; })]);

      svg.append("g")
          .attr("class", "x axis")
          .attr("transform", "translate(0," + height + ")")
          .call(xAxis);

      svg.append("g")
          .attr("class", "y axis")
          .call(yAxis);
        // .append("text")
        //   .attr("transform", "rotate(-90)")
        //   .attr("y", 6)
        //   .attr("dy", ".71em")
        //   .style("text-anchor", "end")
        //   .text("Frequency");

      svg.selectAll(".bar")
          .data(data)
        .enter().append("rect")
          .attr("class", "bar")
          .attr("x", function(d) { return x(d[key]); })
          .attr("width", x.rangeBand())
          .attr("y", function(d) { return y(d[value]); })
          .attr("height", function(d) { return height - y(d[value]); })
          .on('mouseover', function (d) {
          d3.select('.tooltip').transition()
             .duration(200)
             .style('opacity', '1');
              d3.select('.tooltip').html(
                getTooltipHTML(d[key])
              )
              .style('left', (d3.event.pageX + 5) + 'px')
              .style('top', (d3.event.pageY - 30) + 'px');
         })
        .on('mouseout', function (d) {
          d3.select('.tooltip').transition()
            .duration(500)
            .style('opacity', '0');
        });
}

function initKeyValue(data) {
  key = "" , value = "";
  for (var _key in data) {
    if (typeof data[_key] === 'number') {
      value = _key;
    }
    else if (typeof data[_key] === 'string' ) {
      key = _key;
    }
    if (key && value) {
      $('.other-charts').css('display', 'block');
      return true;
    }
  }
  $('.other-charts').css('display', 'none');
  return false;
}

function resetUI() {
  $(".tableData").empty();
  $('#totalRows').empty();
  $('.other-charts').css('display', 'none');
  d3.selectAll("svg").remove();
}

function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}

function createPieChart(data, isPieChart) {

  d3.selectAll("svg").remove();
  if(!initKeyValue(data[0])) return;
  var width = $('#page-wrapper').width(),
    height = 320,
    radius = Math.min(width, height) / 2;
  var colors = ['#ff2b00', '#9f6079', '#245edb', '#2492db', '#bcb5c4', '#1de2c4', '#5b68a4', '#000000', '#0909f6', '#7d73a0', '#e65e19', '#4bb4a1', '#30cfc1', '#73de21', '#ade01f', '#21b2de', '#33cc42', '#bd424a', '#a6bf40', '#59a674', '#fa055f', '#346ecb', '#e99116', '#2dd282', '#1be4de', '#ed5012', '#e41b8d', '#5bcf30', '#6a9495', '#665ea1', '#6828d7', '#a450af', '#dd0e56', '#280df2', '#e71848', '#ac6953', '#ccb833', '#679698', '#818b74', '#f708db', '#98b04f', '#9355aa', '#d12e39', '#388bc7', '#21de9f'];
  var decrementInner = 70;
  if (isPieChart) decrementInner = radius;
  var color = d3.scale.ordinal()
      .range(colors);

  var arc = d3.svg.arc()
      .outerRadius(radius - 10)
      .innerRadius(radius - decrementInner);

  var labelArc = d3.svg.arc()
      .outerRadius(radius - 40)
      .innerRadius(radius - 40);

  var pie = d3.layout.pie()
      .sort(null)
      .value(function(d) { return d[value]; });

  var svg = d3.select(".chart").append("svg")
      .attr("width", width)
      .attr("height", height)
    .append("g")
      .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

  var g = svg.selectAll(".arc")
        .data(pie(data))
        .enter().append("g")
        .attr("class", "arc")
        .on('mouseover', function (d) {
        d3.select('.tooltip').transition()
           .duration(200)
           .style('opacity', '1');
            d3.select('.tooltip').html(
              getTooltipHTML(d.data[value])
            )
            .style('left', (d3.event.pageX + 5) + 'px')
            .style('top', (d3.event.pageY - 30) + 'px');
       })
      .on('mouseout', function (d) {
        d3.select('.tooltip').transition()
          .duration(500)
          .style('opacity', '0');
      });

      g.append("path")
        .attr("d", arc)
        .style("fill", function(d) { return color(getRandomArbitrary(0, 20)); });

      g.append("text")
        .attr("transform", function(d) { return "translate(" + labelArc.centroid(d) + ")"; })
        .attr("dy", ".35em")
        .text(function(d) { return d.value; });
}


function createConceptMapChart(data){

      var outer = d3.map();
      var inner = [];
      var links = [];

      var outerId = [0];

      data.forEach(function(d){

      	if (d == null)
      		return;

      	i = { id: 'i' + inner.length, name: d[0], related_links: [] };
      	i.related_nodes = [i.id];
      	inner.push(i);

      	if (!Array.isArray(d[1]))
      		d[1] = [d[1]];

      	d[1].forEach(function(d1){

      		o = outer.get(d1);

      		if (o == null)
      		{
      			o = { name: d1,	id: 'o' + outerId[0], related_links: [] };
      			o.related_nodes = [o.id];
      			outerId[0] = outerId[0] + 1;

      			outer.set(d1, o);
      		}

      		// create the links
      		l = { id: 'l-' + i.id + '-' + o.id, inner: i, outer: o }
      		links.push(l);

      		// and the relationships
      		i.related_nodes.push(o.id);
      		i.related_links.push(l.id);
      		o.related_nodes.push(i.id);
      		o.related_links.push(l.id);
      	});
      });

      data = {
      	inner: inner,
      	outer: outer.values(),
      	links: links
      }

      // sort the data -- TODO: have multiple sort options
      outer = data.outer;
      data.outer = Array(outer.length);


      var i1 = 0;
      var i2 = outer.length - 1;

      for (var i = 0; i < data.outer.length; ++i)
      {
      	if (i % 2 == 1)
      		data.outer[i2--] = outer[i];
      	else
      		data.outer[i1++] = outer[i];
      }

      console.log(data.outer.reduce(function(a,b) { return a + b.related_links.length; }, 0) / data.outer.length);


      // from d3 colorbrewer:
      // This product includes color specifications and designs developed by Cynthia Brewer (http://colorbrewer.org/).
      var colors = ["#a50026","#d73027","#f46d43","#fdae61","#fee090","#ffffbf","#e0f3f8","#abd9e9","#74add1","#4575b4","#313695"]
      var color = d3.scale.linear()
          .domain([60, 220])
          .range([colors.length-1, 0])
          .clamp(true);

      var diameter = 960;
      var rect_width = 40;
      var rect_height = 14;

      var link_width = "1px";

      var il = data.inner.length;
      var ol = data.outer.length;

      var inner_y = d3.scale.linear()
          .domain([0, il])
          .range([-(il * rect_height)/2, (il * rect_height)/2]);

      mid = (data.outer.length/2.0)
      var outer_x = d3.scale.linear()
          .domain([0, mid, mid, data.outer.length])
          .range([15, 170, 190 ,355]);

      var outer_y = d3.scale.linear()
          .domain([0, data.outer.length])
          .range([0, diameter / 2 - 120]);


      // setup positioning
      data.outer = data.outer.map(function(d, i) {
          d.x = outer_x(i);
          d.y = diameter/3;
          return d;
      });

      data.inner = data.inner.map(function(d, i) {
          d.x = -(rect_width / 2);
          d.y = inner_y(i);
          return d;
      });


      function get_color(name)
      {
          var c = Math.round(color(name));
          if (isNaN(c))
              return '#dddddd';	// fallback color

          return colors[c];
      }

      // Can't just use d3.svg.diagonal because one edge is in normal space, the
      // other edge is in radial space. Since we can't just ask d3 to do projection
      // of a single point, do it ourselves the same way d3 would do it.


      function projectX(x)
      {
          return ((x - 90) / 180 * Math.PI) - (Math.PI/2);
      }

      var diagonal = d3.svg.diagonal()
          .source(function(d) { return {"x": d.outer.y * Math.cos(projectX(d.outer.x)),
                                        "y": -d.outer.y * Math.sin(projectX(d.outer.x))}; })
          .target(function(d) { return {"x": d.inner.y + rect_height/2,
                                        "y": d.outer.x > 180 ? d.inner.x : d.inner.x + rect_width}; })
          .projection(function(d) { return [d.y, d.x]; });


      var svg = d3.select(".chart").append("svg")
          .attr("width", diameter)
          .attr("height", diameter)
        .append("g")
          .attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");


      // links
      var link = svg.append('g').attr('class', 'links').selectAll(".link")
          .data(data.links)
        .enter().append('path')
          .attr('class', 'link')
          .attr('id', function(d) { return d.id })
          .attr("d", diagonal)
          .attr('stroke', function(d) { return get_color(d.inner.name); })
          .attr('stroke-width', link_width);

      // outer nodes

      var onode = svg.append('g').selectAll(".outer_node")
          .data(data.outer)
        .enter().append("g")
          .attr("class", "outer_node")
          .attr("transform", function(d) { return "rotate(" + (d.x - 90) + ")translate(" + d.y + ")"; })
          .on("mouseover", mouseover)
          .on("mouseout", mouseout);

      onode.append("circle")
          .attr('id', function(d) { return d.id })
          .attr("r", 4.5);

      onode.append("circle")
          .attr('r', 20)
          .attr('visibility', 'hidden');

      onode.append("text")
      	.attr('id', function(d) { return d.id + '-txt'; })
          .attr("dy", ".31em")
          .attr("text-anchor", function(d) { return d.x < 180 ? "start" : "end"; })
          .attr("transform", function(d) { return d.x < 180 ? "translate(8)" : "rotate(180)translate(-8)"; })
          .text(function(d) { return d.name; });

      // inner nodes

      var inode = svg.append('g').selectAll(".inner_node")
          .data(data.inner)
        .enter().append("g")
          .attr("class", "inner_node")
          .attr("transform", function(d, i) { return "translate(" + d.x + "," + d.y + ")"})
          .on("mouseover", mouseover)
          .on("mouseout", mouseout);

      inode.append('rect')
          .attr('width', rect_width)
          .attr('height', rect_height)
          .attr('id', function(d) { return d.id; })
          .attr('fill', function(d) { return get_color(d.name); });

      inode.append("text")
      	.attr('id', function(d) { return d.id + '-txt'; })
          .attr('text-anchor', 'middle')
          .attr("transform", "translate(" + rect_width/2 + ", " + rect_height * .75 + ")")
          .text(function(d) { return d.name; });

      // need to specify x/y/etc

      d3.select(self.frameElement).style("height", diameter - 150 + "px");

      function mouseover(d)
      {
      	// bring to front
      	d3.selectAll('.links .link').sort(function(a, b){ return d.related_links.indexOf(a.id); });

          for (var i = 0; i < d.related_nodes.length; i++)
          {
              d3.select('#' + d.related_nodes[i]).classed('highlight', true);
              d3.select('#' + d.related_nodes[i] + '-txt').attr("font-weight", 'bold');
          }

          for (var i = 0; i < d.related_links.length; i++)
              d3.select('#' + d.related_links[i]).attr('stroke-width', '5px');
      }

      function mouseout(d)
      {
          for (var i = 0; i < d.related_nodes.length; i++)
          {
              d3.select('#' + d.related_nodes[i]).classed('highlight', false);
              d3.select('#' + d.related_nodes[i] + '-txt').attr("font-weight", 'normal');
          }

          for (var i = 0; i < d.related_links.length; i++)
              d3.select('#' + d.related_links[i]).attr('stroke-width', link_width);
      }
}

function createTable(data) {

    $(".tableData").empty();
    $('#totalRows').empty();
    $('.loader').css('display', 'none');

    if (data.length >=2 ) {
      $("#totalRows").text("Records found: " + data.length);
    }
    else {
      $("#totalRows").text("No records found for query!");
      return;
    }
    var columns = [];
    for (var key in data[0]) columns.push(key);

    var table = d3.select('.tableData').append('table').attr("class" , "table table-striped table-bordered table-hover")
    var thead = table.append('thead')
    var	tbody = table.append('tbody');

  	thead.append('tr')
  	.selectAll('th')
  	.data(columns).enter()
  	.append('th')
  	.text(function (column) { return column.toUpperCase(); });

    var rows = tbody.selectAll('tr')
      .data(data)
      .enter()
      .append('tr');


    var cells = rows.selectAll('td')
      .data(function (row) {
        return columns.map(function (column) {
          return {column: column, value: row[column]};
        });
      })
      .enter()
      .append('td')
      .text(function (d) { return d.value; });
}
