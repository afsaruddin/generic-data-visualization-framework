#  GDVF - Query Handler

This module is primarily for BFF (Backend For Frontend). This module is working as a bridge between query processing engine and Frontend. It also publish result to the query history dispatcher module so that users can see other peoples query at run time.



## Development Strategy
[flask-restful](http://flask-restful-cn.readthedocs.org/en/0.3.4/) is used to build the api end points. As a data source  [Postgres]( http://www.postgresql.org) is used as it is free.

This project needs on the following dependencies,

- [Falsk](http://flask.pocoo.org)
- [Flask-RESTful](http://flask-restful-cn.readthedocs.org/en/0.3.4/)
- [psycopg2](http://initd.org/psycopg/)
- [Flask-CORS](https://flask-cors.readthedocs.org/en/latest/)

## Api end-points

Api end point is stated below.

- http://169.45.107.190:5000/queryhandler/

It only takes POST requests other GET/PUT/DELETE requests will be discarded.

    curl -H "Content-Type: application/json" -X POST -d '{"text":"tour cost employee"}' http://169.45.107.190:5000/queryhandler/


Here is the Ajax sample

```javascript

        $.ajax({
          type: "POST",
          url: "http://169.45.107.190:5000/queryhandler/",
          contentType: "application/json",
          data: JSON.stringify(suggestionObject),
          success: function(responseData) {

          },
          error: function(err){

          },
          dataType: "json"
        });

```

 
