# Products API
*Since most of the projects I built with Spring Boot were for companies I worked with, this API was built only to showcase a bit some of my experience with this framework and tries to emulate some of the use cases I already had to deal with.

The Products API helps supermarket chains to manage product records from their branches and also manage price changes requests that come from each branch manager, when a product is about to go bad and needs to be sold quickly.

The main flow is:
- *Managers* request a price change 
- The system checks if the store has budget to accommodate that price request
	- if this is true, the price request changes its status to approved and the price is altered in the database.
	- if not, the request stays pending until an analyst reviews it.
 - The *Analyst* can deny the request or approve it.
 - Once a Request gets a response, an email has to be sent to the *Manager*


We have the following entities:
- Product *(name, price)*
- Branch *(name, budget)*
- User *(name, role=Analyst, Manager, email)*
- Request *(userId, createdAt, updatedAt, status, price)*

The main endpoints here are:
Requests
 - *GET /requests*
 - *GET /requests/{id}*
 - *POST /requests with branchId, productId, price*
 - *PUT /requests with requestId, status*
 - *DELETE /requests/{id}*

Products
 - *GET /products* [Done]
 - *GET /products/{id}* [Done]
 - *POST /products with name, price* [Done]
 - *PUT /products with name, price* [Done]
 - *DELETE /products/{id}* [Done]

Users
 - *GET /users* 
 - *GET /users/{id}*
 - *POST /users with name, role*
 - *PUT /users*
 - *DELETE /users/{id}*

Branches
 - *GET /branches*
 - *GET /branches/{id}*
 - *POST /branches with name, budget*
 - *PUT /branches/{id}*
 - *DELETE /branches/{id}*



