# Robert-Maule-Project1

## Description
A banking application using java and connected to an amazon RDS. logging done using fluent d which is setup to connect to loki. 

## Technologies Used
* Java
* Maven
* Spring Boot
* Spring Data
* PostgreSQL
* Log4J
* FluentD
* Loki
* Grafana
* AWS RDS
* Docker
* Kubernetes


## Features
* Create user
* Get all users
* Get all employees
* Get all customers
* Get users by id
* Get customer by id
* Get employee by id
* Create account
* Get all savings acounts
* Get all checkings acounts
* Get checkings account by id
* Get savings account by id
* transfer funds
* grant a user acess to an existing account
* pay salary to employees checkings account

## How to Use
   * Send GET request method to “~/checkings_accounts” to get all checking accounts
   * Send GET request method to “~/checkings_accounts/{id}” to get checking account by ID
   * Send GET request method to “~/savings_accounts” to get all savings accounts
   * Send GET request method to “~/savings_accounts/{id}” to get savings account by ID


   * Send GET request method to “~/customers” to obtain a JSON collection of all users who are customers
   * Send GET request method to “~/employees” to obtain a JSON collection of all users who are employees
   * Send GET request method to “~/users” to obtain a JSON collection of all users 
   * Send GET request method to “~/users/{userid}” to obtain JSON representation of a user associated with the id passed in as ‘userid’


   * Send POST request method to “~/checkings_accounts/{id}” to insert new checking account into database
   * Send POST request method to “~/savings_accounts/{id}” to insert new savings account into database
   * Send POST request method to “~/accounts/transfer/{id1}/{id2}/{amount}”
   * Move ‘amount’ from account with ‘id1’ to account with ‘id2’
   * Send POST request method to “~/accounts/access/{actid}/{userid}”
   * Grant the user described by ‘userid’ access to the account found by ‘actid’
   * Send POST request method to “~/employees/{id}” to add an employee to the database
   * Send POST request method to “~/customers/{id}” to add a customer to the database
   * Send POST request method to “~/users” to save a user passed via JSON object in the request body
   * Send POST request method to “~/users/salary” to pay salary to users