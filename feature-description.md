## Chat Service (1-to-1)

Enables private conversations between two users.

- Creates a chat room between two people
- Stores messages sent between users
- Keeps track of who sent each message and when
- Figures out who the receiver is based on the sender


## Authentication Service

It protects the app from unauthorised users, acts like a guard for our app or in more simmple words this service checks whether a user is allowed to use the app or not.

- It checks if the user is already logged in or not
- It verifies wheter the user is a valid user or not
- It allows access to the app only if the user is trusted and has a token(jwt token, used for authorization)
- It blocks the user from accessing the app if the user is not recognized or the token mismatches
- It simply protects the app from unauthorized usage