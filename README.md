# Generic Data Visualization Framework

## Overview
In an organization, there are tons of data. When anyone wants to see / analyse the data, (s)he finds all hurdles and lean to a BI tool. So far there are few “satisfactory” BI tools are out there having their own limitations:

- You need to be a bit techy to understand the data storage structure (say, columns) and SQL-ing.
- You cannot extend the way of report making. The tool is not a framework so that you can reach your expectation.

We believe, with tons of data, we need tons of possibilities to see / analyse those data. With some techy way, it doesn’t reveal that much possibilities. A natural language searching would be great like, for a tour management company, we can query data by: _show types of persons travel in a year_, _show average tour cost in winter to X country_ etc. For example, http://simpleql.com/ . Well, with simplesql, we can serve our natural language searching but it comes with some limitations:

- You cannot search with synonym words nor you can customize the query processor to process your desired word with some data column. For example, “show persons having age less than 18” and “show persons that are not adult” should endup in same result.
- It's not free that you can modify to your desire.

To create tons of possibilities to see / analyse data, we need data owner controlled query management, developer friendly stuffs (say, an open source framework) so that world can create the tons of possibilities.

## Goals
- Let user search with natural language having suggestions. This suggestions are 1 of the major core part. Other than suggestions, no user can find wanted data easily. If someone tries with 10 natural queries and didn’t get the result, then its high chance that the user will mark the tool as “unusable” in his / her brain.

  The suggestions can be derived from some pre-inputted (by the data owner) queries and the queries that people are searching. The system can be machine-learnable.

  Showing who is searching with what in realtime will make the user adopt the querying process.

- Let the data owner control the relation between natural language and the data language (i.e., SQL). A data owner knows best which natural word means what data. Our system should not control it.

  This is important for revealing the possibilities to search same result with different sentences.

- Present the data in a desired visual format and let the user control the visual. An intelligence is needed for choosing most relevant graph-type (bar / PI etc) for a query. This can be derived through machine learning for every user’s activities.

- Support versatile types of SQL-ers (like mysql, postgresql, mongo, spark etc)
Make the whole implementation modular, act like a framework, so that anyone can customize it.
