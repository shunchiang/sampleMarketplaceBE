# backend

![Marketplace Database Layout](dbdiagram.png)
[Link to Db Diagram](https://dbdiagram.io/d/5f66b8767da1ea736e2e8623)

#USERS CRUD
Method | Endpoint | Description | Required Data
--- | --- | --- | ---
POST | /users/register | Creates a new user account | `username, password, email, firstname, lastname` -- All Strings
POST | /login | Logs in to account and fetches token | `username, password` -- both Strings
GET | /users | Returns a list of all Users
GET | /users/:userid | Returns an individual User object when searched by userid
GET | /username/:username | Returns an individual User object when searched by username
PUT | /changeuser/:userid | Updates/Changes an entire User | `username, password, email, firstname, lastname` -- All Strings
PUT | /users/:userid | Changes a specific field in User object | Whichever single String of `username, password, email, firstname, or lastname` needed
DELETE | /users/:userid | Delete User based on userid

## LISTINGS CRUD
Method | Endpoint | Description | Required Data
--- | --- | --- | ---
POST | /todos/u/:userid/t/:title | Creates a new Todo List category for a specific User
GET | /todos | Returns all Todo Lists and their associated Items
GET | /todos/:todoid | Returns a specific Todo List by todoid and it's own associated Items
PUT | /todos/:todoid/t/:title | Updates/Changes the title of an existing Todo List
DELETE | /todos/:todoid | Deletes a Todo List and it's associated Ite ms
## Items CRUD
Method | Endpoint | Description | Required Data
--- | --- | --- | ---
POST | /items/t/:todoid | Creates a new Item for a specific Todo List | `name, description, date, frequency` -- All Strings. Note: the date String must be in the format "yyyy-MM-dd" and it does not get saved, but instead coverts into the LocalDate object: duedate
GET | /items | Returns a list of all Items
GET | /items/:itemid | Returns information for a specific Item based on itemid
PUT | /changeitem/:itemid | Updates/changes an entire Item | `name, description, date, frequency` -- All Strings. See note above about String date.
PUT | /items/:itemid | Updates a single field in an existing Item | One of `name, description, date` -- All Strings. See note above about String date.
DELETE | items/:itemid | Deletes an Item
