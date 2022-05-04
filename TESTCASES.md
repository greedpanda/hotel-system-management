# Test Case Document <!-- omit in toc -->

- [IDENTIFICATION INFORMATION SECTION](#identification-information-section)
    - [PRODUCT](#product)
    - [PROJECT DESCRIPTION](#project-description)
- [UNIT MANUAL TEST CASES](#unit-manual-test-cases)
  - [USER TEST CASES](#user-test-cases)
  - [ADMIN TEST CASES](#admin-test-cases)
  - [RECEPTION TEST CASES](#reception-test-cases)

## IDENTIFICATION INFORMATION SECTION

Test case document updated to Nitrogen Hotel Management System v3.0.0

### PRODUCT

- **Product Name:** N<sub>2</sub> Hotel Management System

### PROJECT DESCRIPTION

N<sub>2</sub> Hotel Management System is a product of Nitrogen Group.
The System was designed to enhance this process by providing rich functionality in an easy, user-friendly environment.
This document contains information that is specific to the Hotel Software. 
With N<sub>2</sub> Hotel Management System is it possible to perform staff, customer, room and booking management.

## UNIT MANUAL TEST CASES

### USER TEST CASES

| \#  | OBJECTIVE | DESCRIPTION | INPUT | EXPECTED RESULTS | STATUS (pass/fail) |
| --- | --------- | ----------- | ----- | ---------------- | ------------------ |
| 1   | Login | Verify response when a valid username and password are used. | 1) Run the application.<br>2) Enter valid username and password.<br>3) Click the login button.| Successful login | Pass |
| 2   | Logout | Verify response when a user logs out. | 1) While logged in click the Logout button. | Successful logout | Pass |
| 3   | Change screen name | Verify response when an appropriate new screen name is inserted. | 1) Click the button User Setting.<br>2) Click the button Edit screen name.<br>3) Enter valid screen name.<br>4) Click the OK button. | Successful screen name change | Pass |
| 4   | Change password | Verify response when an appropriate new password is inserted and verified. | 1) Click the button User Setting.<br>2) Click the button Edit password.<br>3) Enter valid previous password.<br>4) Enter valid new password.<br>5) Confirm the new password.<br> 6) Click the OK button. | Successful password change | Pass |
| 5   | Display login error message | Verify response when an invalid username and/or password is used. | 1) Run the application. <br>2) Enter an invalid username or an invalid password.<br>3) Click the login button. | Error message displayed | Pass |
| 6   | Display edit password failed error message | Verify response when the attempt of setting a new password fails. | 1) Click the button User Setting.<br>2) Click the button Edit password.<br>3) Enter invalid previous password and/or invalid new password and/or invalid password confirmation.<br> 4) Click the OK button. | Error message displayed, password not changed | Pass |

### ADMIN TEST CASES

| \#  | OBJECTIVE | DESCRIPTION | INPUT | EXPECTED RESULTS | STATUS (pass/fail) |
| --- | --------- | ----------- | ----- | ---------------- | ------------------ |
| 7   | Add user | Verify the response when a new user is added. | 1) While logged in as Admin click the button User Setting.<br>2) Click the button Add user.<br>3) Enter valid username.<br>4) Enter valid screen name.<br>5) Select a role.<br>6) Enter valid password.<br>7) Repeat the password.<br>8) Click the OK button. | New user added | Pass |
| 8   | Add room | Verify the response when a new room is added. | 1) While logged in as Admin click the button Room Options.<br>2) Click the button Add room.<br>3) Enter a valid room number.<br>4) Select the size.<br>5) Enter number of beds.<br>6) Select floor.<br>7) (optional) Enter a note.<br>8) Click the OK button. | New room added | Pass |
| 9   | Edit room | Verify the response when an existing room is edited. | 1) While logged in as Admin click the button Room Options.<br>2) Click the button Edit room.<br>3) Select the field to edit.<br>4) Change the value of the field and press enter.<br>5) Click the OK button. | Room edited | Pass | 
| 10   | Delete room | Verify the response when an existing room is deleted. | 1) While logged in as Admin click the button Room Options.<br>2) Click the button Delete room.<br>3) Select the room to delete.<br>4) Click the Delete button. | Room deleted | Pass |
| 11   | Display "User 'username' already exists." error message | Verify the response when an attempt to add a new user with an already existing username is made. | 1) While logged in as Admin click the button User Setting.<br>2) Click the button Add user.<br>3) Enter an already existing username.<br>4) Enter valid screen name.<br>5) Select a role.<br>6) Enter valid password.<br>7) Repeat the password.<br>8) Click the OK button. | Error message displayed, new user not added | Pass |
| 12   | Display "room 'number' already exists." error message | Verify the response when an attempt to add a new room that already exist is made. | 1) While logged in as Admin click the button Room Options.<br>2) Click the button Add room.<br>3) Enter an already used room number.<br>4) Select the size.<br>5) Enter number of beds.<br>6) Select floor.<br>7) (optional) Enter a note.<br>8) Click the OK button. | Error message displayed, new room not added | Pass |
| 13   | Verify the impossibility of inserting not accepted values in the edit field | Verify the response when an attempt to edit parameters of a room with incorrect values is made. | 1) While logged in as Admin click the button Room Options.<br>2) Click the button Edit room.<br>3) Select the field to edit.<br>4) Change the value of the field with an invalid one and press enter.<br>5) Click the OK button. | Impossibility to insert the value, box turned red, room not edited | Pass | 

### RECEPTION TEST CASES

| \#  | OBJECTIVE | DESCRIPTION | INPUT | EXPECTED RESULTS | STATUS (pass/fail) |
| --- | --------- | ----------- | ----- | ---------------- | ------------------ |
| 14   | Show room details | Verify the response when the details of a room is requested. | 1) While logged in as Reception click the button Room Options.<br>2) Click the button Room details. | Room overview displayed | Pass |
| 15  | Add customer | Verify the response when a new customer is added. | 1) While logged in as Reception click the button Customer Options.<br>2) Click the button Add customer.<br>3) Enter valid customer name.<br>4) Enter valid customer Public ID.<br>5) Enter valid customer address.<br>6) Select payment method.<br>7) Click the OK button. | New customer added | Pass |
| 16  | Edit customer | Verify the response when an existing customer is edited. | 1) While logged in as Reception click the button Customer Options.<br>2) Click the button Edit customer.<br>3) Select the field to edit.<br>4) Change the value of the field and press enter.<br>5) Click the OK button. | Customer edited | Pass |
| 17  | Show customer details | Verify the response when the details of a customer is requested. | 1) While logged in as Reception click the button Customer Options.<br>2) Click the button Customer details. | Customer overview displayed | Pass |
| 18  | Add booking | Verify the response when a new booking is added. | 1) While logged in as Reception click the button Booking Options.<br>2) Click the button Book room.<br>3) Select room type.<br>4) Select valid start date.<br>5) Select valid end date.<br>6) Select room.<br>7) Select customer.<br>8) Check if the room has been already paid.<br>9) Click the OK button. | New booking added | Pass |
| 19  | Edit booking | Verify the response when an existing booking is edited. | 1) While logged in as Reception click the button Booking Options.<br>2) Click the button Edit booking.<br>3) Select the booking to edit.<br>4) Make valid changes in the pop up window.<br>5) Click the OK button. | Booking edited | Pass |
| 20  | Show booking details | Verify the response when the details of a booking is requested. | 1) While logged in as Reception click the button Booking Options.<br>2) Click the button Booking details. | Booking overview displayed | Pass |
| 21  | Show dates available for a specific room | Verify the response when the availability of a room is requested. | 1) While logged in as Reception click the button Booking Options.<br>2) Click the button Booking details.<br>3) Select a room and click "view available dates" to open up a calendar with the available dates for the room selected. | Available dates displayed | Pass |
| 22  | Search booking in the table | Verify the response when the search field is used. | 1) While logged in as Reception click the button Booking Options.<br>2) Click the button Booking details.<br>3) Fill the search field with a valid room number or a valid date or a valid customer name. | Filtered table displayed | Pass |
| 23  | Show room details in a booking | Verify the response when the room field, in the booking details table, is clicked. | 1) While logged in as Reception click the button Booking Options.<br>2) Click the button Booking details.<br>3) Click on a room number in a booking row. | Room details pop up displayed | Pass |
| 24  | Show customer details in a booking | Verify the response when the customer field, in the booking details table, is clicked. | 1) While logged in as Reception click the button Booking Options.<br>2) Click the button Booking details.<br>3) Click on a customer name in a booking row. | Customer details pop up displayed | Pass |

