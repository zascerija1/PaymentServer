# Feature

> User registration

## Item

> Payment Dashboard Web App and Pay Mobile App users need to register their users with PaymentServer.

### Task

* Create a new branch and switch to it
* Create a model which represents database entity for application users
* Create a contoller class, response class and request class if needed
* Create a service that will provide CRUD operations with corresponding entities
* Create a route for users registration
* Commit and push on your branch
* Create a pull request




# Feature

> User login

## Item

> Payment Dashboard Web App and Pay Mobile App users need to login their users with PaymentServer to access the app, but only with correct user information.

### Task

* Create a new branch and switch to it
* Create a contoller class, response class and request class if needed
* Create service to provide search based on username/email
* Create a route for users login
* Commit and push on your branch
* Create a pull request




# Feature

> Authorization

## Item

> Payment Dashboard Web App and Pay Mobile App users can acces all the resources only with authorized information.

### Task

* Create a new branch and switch to it
* Create all the JWT authentication and Spring Security classes
* Implement role and privilege mechanism
* Create interfaces and classes to provide information of authenticated user
* Commit and push on your branch
* Create a pull request



# Feature

> Password change

## Item

> Payment Dashboard Web App and Pay Mobile App users can change users password with 2 factor authentication.

### Task

* Create a new branch and switch to it
* Create classes required to model a process of passoword change 
* Create a contoller class, response class and request class if needed
* Create a service that will provide CRUD operations and queries with corresponding entities
* Create routes for users password change 
* Commit and push on your branch
* Create a pull request



# Feature

> Password recovery, in case user forgot

## Item

> Payment Dashboard Web App and Pay Mobile App users can recover users password, in case he/she forgot, with 2 factor authentication.

### Task

* Create a new branch and switch to it
* Create a contoller class, response class and request class if needed
* Create utility class to provide generating of new password
* Create service to establish identity of person making request
* Create routes for users password recover
* Commit and push on your branch
* Create a pull request




# Feature

> Encrypted data

## Item

> Payment Dashboard Web App and Pay Mobile App users requre from Payment server to save their data encrypted.

### Task

* Create a new branch and switch to it
* Add class that provides encryption of sensitive data
* Embed its methods in existing system 
* Commit and push on your branch
* Create a pull request



# Feature

> Adding a bank account

## Item

> Payment Dashboard Web App and Pay Mobile App users can add users bank account, so that user can use that account on the web or with his phone. Payment server also validates all the accounts.

### Task

* Create a new branch and switch to it
* Create models that represent database entities and corresponding relations 
* Create a service to provide CRUD operations with entities used to model this process
* Create a contoller class, response class and request class if needed
* Create validation classes and interfaces 
* Create a route for adding a bank account
* Commit and push on your branch
* Create a pull request



# Feature

> Deleting a bank account

## Item

> Payment Dashboard Web App and Pay Mobile App users can delete users bank account, in case he/she closes it or for any other reason. This means that web and mobile app will show that users current bank account state.

### Task

* Create a new branch and switch to it
* Create a contoller class, response class and request class if needed
* Create a service to provide information required to proceed with deletion
* Create a route for deleting a bank account
* Commit and push on your branch
* Create a pull request


# Feature

> Filtering transactions

## Item

> Payment Dashboard Web App and Pay Mobile App users are able to get transactions data filtered: 
*by user ID (get users transactions),
*by bank account ID, 
*by transactions created time (last 24h), 
*by transactions created time (last month),
*by users service,
*by merchant name.

### Task

* Create a new branch and switch to it
* Create a response class and request class if needed
* Create a controller for transactions and all the rutes for filtering
* Commit and push on your branch
* Create a pull request




# Feature

> Was payment successful?

## Item

> Main Server and Pay Mobile App need to know if payment was successful. They need to get a response from Payment server.

### Task

* Create a new branch and switch to it
* Create a response class and request class if needed
* Create a controller for transactions and all the rutes 
* Receive request from Pay Mobile App for paying
* Create a new transaction and get money from users bank account if they have enough money
* Send response to Main Server and Pay Mobile App
* Commit and push on your branch
* Create a pull request



# Feature

> Paying with dynamic QR code

## Item

> Pay Mobile App need to be able to pay with dynamic QR code.

### Task

* Create a new branch and switch to it
* Create a response class and request class if needed
* Create a controller for paying/transactions and all the rutes 
* Receive request from Pay Mobile App for paying, with all the information they get from scanning the code
* Create a new transaction and get money from users bank account if they have enough money
* Send response to Main Server and Pay Mobile App
* Commit and push on your branch
* Create a pull request



# Feature

> Paying with static QR code

## Item

> Pay Mobile App need to be able to pay with static QR code.

### Task

* Create a new branch and switch to it
* Create a response class and request class if needed
* Create a controller for paying/transactions and all the rutes 
* Receive request from Pay Mobile App for paying, with all the information they get from scanning the code (cash register ID and businessName)
* Send a request to Main Server to get all the other information we need to continue payment
* Send response to Pay App to see if they want to continue
* Create a new transaction and get money from users bank account if they have enough money
* Send response (about payment status) to Main Server and Pay Mobile App 
* Commit and push on your branch
* Create a pull request

