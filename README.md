# WELCOME TO HOTEL BOOKING WEB REPOSITORY


## You will find in this repo the following stuff:
```
* The java source code used to demonstrate a simple hotel booking website
Technique
* Java JSP and servlet
* Maven is used to built this website
* This website is built on the MVC2 model
* The .gitignore & README.md files to manage this repo itseft
* ...
```
## Functional :
```
### 1. Login
```
- In order to booking, an authentication is required.
- If the user has not authenticated, the system redirects to the registration page.
- The actor enters userID and password, the function checks if the userID with the password is in the 
available user list, then grant the access permission. If not, a message would appear no notify that user is 
not found.
- Login function is required for shopping.
Screen short
![Image of Yaktocat](https://github.com/tannvv/hotel-web/images/login-page.png)
```

### 2. Display- Search
```
- List all hotel which has an available room in the system.
- Each hotel has some kind of room: single, double, family, …
- Each room type has different price.
- User can find the room based on hotel name or hotel area and check in date and check out date and 
amount of room. 
- All users can use this function (login is not required)
Screen short
![Image of Yaktocat](https://github.com/tannvv/hotel-web/images/index-page.png)
```

### 3.Registration
```
- Register new user: email as ID, phone, name, address, create date.
- Create date is current date.
- The default status of new user is active.
- Password must be encrypted using SHA-256 before store in database.
Screen short
![Image of Yaktocat](https://github.com/tannvv/hotel-web/images/register-page.png)
```

### 4. Booking
```
- All users can use this function except admin role (login is required)
- Add the selected room to booking cart.
- Each user can book any available room in the list (not limit the amount room want to book)
- User can view the selected room in the cart. For each room: hotel name, room type, amount, price, 
total. The screen must show the total amount of money of this cart.
- User can remove the room from the cart. The confirm message will show before delete action.
- User can update amount of each room in cart.
- Click the Confirm button to store the booking to database (must store the buy date time). The warning 
message will show if the selected room is out of stock.
Screen short (select room type)
![Image of Yaktocat](https://github.com/tannvv/hotel-web/images/detail-page.png)
Screen short (list cart in session)
![Image of Yaktocat](https://github.com/tannvv/hotel-web/images/cart-page.png)
```

### 5. Booking history
```
- User can take over the booking history: list of booking order by booking date.
- Support search function: search by name or booking date
Screen short
![Image of Yaktocat](https://github.com/tannvv/hotel-web/images/history-page.png)
```
### 6. Booking confirm by email
```
Screen short
![Image of Yaktocat](https://github.com/tannvv/hotel-web/images/email-page.png)
```
```





