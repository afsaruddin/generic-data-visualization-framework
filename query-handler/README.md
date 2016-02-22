#  GDVF - Query Handler

This module is primarily for BFF (Backend For Frontend). This module is working as a bridge between Query processing engine and Frontend. It also publish result to the query history dispatcher module so that users can see other people's query at run time.



## Development Strategy
[flask-restful](http://flask-restful-cn.readthedocs.org/en/0.3.4/) is used to build the api end points. As a data source  [Postgres]( http://www.postgresql.org) is used as it is free. 

This project needs on the following dependencies,

- [Falsk](http://flask.pocoo.org)
- [Flask-RESTful](http://flask-restful-cn.readthedocs.org/en/0.3.4/)
- [psycopg2](http://initd.org/psycopg/)
- [Flask-CORS](https://flask-cors.readthedocs.org/en/latest/)

## Api end-points

Api end point is stated below ,

- Url: http://169.45.107.190:5000/queryhandler/ ( Example endpoint; Deployed in [Koding VM](https://koding.com) )
- Method: POST
- Example:

```bash
    tareqsust@tareq:query-handler$ curl -i -H "Content-Type: application/json" -X POST -d '{"text":"tour cost employee"}' http://169.45.107.190:5000/queryhandler/
    Content-Type: application/json
    Content-Length: 109708
    Server: Werkzeug/0.11.4 Python/2.7.6
    Date: Mon, 22 Feb 2016 06:19:01 GMT
    {
        "data": [
            {
                "age": 71,
                "costperperson": 6900.0,
            },
            ..........
        ]
    }
```

Here is an sample of the Ajax request, 

```javascript

    $.ajax({
        type: "POST",
        url: "http://169.45.107.190:5000/queryhandler/",
        contentType: "application/json",
        data: JSON.stringify({"text":"tour cost employee"}),
        dataType: "json",
        success: function(responseData) {
            console.log(responseData);
        },
        error: function(err) {
            console.log(err);
        }
    });

```


