[![Build Status](https://travis-ci.com/roqmarcelo/wishlist-api.svg?branch=master)](https://travis-ci.com/roqmarcelo/wishlist-api)
[![Code Coverage](https://codecov.io/gh/roqmarcelo/wishlist-api/coverage.svg)](https://codecov.io/gh/roqmarcelo/wishlist-api)

# Wishlist API

Simple API to test my capabilities as a Back-End Engineer.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Java 11
* Maven
* Git
* Docker

### Installing

Clone the repository

```
git clone https://github.com/roqmarcelo/wishlist-api.git
```

Go to the project folder

```
cd wishlist-api
```

Build the project

```
mvn clean package
```

And then run with Docker

```
docker-compose up
```

## Running the tests

To run only the tests

```
mvn test
```

This will run all unit tests and produce a coverage report located at **target/jacoco-report/index.html**

## Resources

### Customers
Customers are identified by their ids, which are unique integers, and live under ```/customers/<id>```.

* Find Customer By Id
  * GET: http://localhost:8080/customers/1/
  
* Create Customer
  * POST: http://localhost:8080/customers/
  * Body:
  ```
  {
    "name": "Roque Santos",
    "email": "roque.santos@luizalabs.com.br"
  }
  ```

* Update Customer
  * PUT: http://localhost:8080/customers/1/
  * Body:
  ```
  {
    "name": "Roque Santos",
    "email": "roque@luizalabs.com.br"
  }
  ```
  
* Delete Customer
  * DELETE: http://localhost:8080/customers/1/
  
## Wishlist
Wishlists are identified by their ids, which are unique uuid, and live under ```/customers/<id>/wishlist/<wishlistid>```.

* Find Wishlist By Customer Id
  * GET: http://localhost:8080/customers/1/wishlist/
  
* Find Product in Wishlist By Customer And Product Id
  * GET: http://localhost:8080/customers/1/wishlist/958ec015-cfcf-258d-c6df-1721de0ab6ea/
  
* Add Product to Wishlist
  * POST: http://localhost:8080/customers/1/wishlist/58ec015-cfcf-258d-c6df-1721de0ab6ea/
  
* Delete Product from Wishlist
  * DELETE: http://localhost:8080/customers/1/wishlist/58ec015-cfcf-258d-c6df-1721de0ab6ea/

## Authors

* **Roque Santos** - [LinkedIn](https://www.linkedin.com/in/roqmarcelo/)