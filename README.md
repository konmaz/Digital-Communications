This repository contains Java code for a simple Request Reply Messaging App. It isn't persistent (it doesn't save the messages to a file/database).
# Classes

## Server

This class represents a server, the server replies to request made by clients, it uses Java's RMI Protocol. Also parses the command line arguments.

## Client
This class represents a client, a client request from a server. Also parses the command line arguments.

## Message
This class represents a message, every message is private and can be seen only by the recipient.

## Account
This class represents an account, every account has a unique username and a private auth token.

**NOTE**: *In a proper implementation every auth token should be generated RANDOMLY to prevent a malicious user from guessing the auth token from another user.* 

## CommunicatorInterface
This interface class dictates which requests are available for the Client.

## RemoteCommunicator
This class implements the interface CommunicatorInterface, this class is responsible for the logic side of the server, it handles requests and has also a list of all Accounts.

# Usage
First start the server, and then make requests with the client.

## Server usage 
`java -jar Server.jar 5000`

## Client usage
And then you can make requests:

`java client <ip> <port number> <FN_ID> <args>`


For example the following request to the server `localhost` with port `5000` to create an account with username= `demousername`:

`java -jar Client.jar localhost 5000 1 demousername` 

The serve replies with the AUTH Token that will be used to authorize the requests

### Client Function ID Table

| `<FN_ID_>` | Description    | Usage                                                                     |
|------------|----------------|---------------------------------------------------------------------------|
| 1          | Create Account | `java Client <ip> <port number> 1 <username>`                             |
| 2          | Show Accounts  | `java Client <ip> <port number> 2 <authToken>`                            |
| 3          | Send Message   | `java Client <ip> <port number> 3 <authToken> <recipient> <message_body>` |
| 4          | Show Inbox     | `java Client <ip> <port number> 4 <authToken>`                            |
| 5          | ReadMessage    | `java Client <ip> <port number> 5 <authToken> <message_id>`               |
| 6          | DeleteMessage  | `java Cclient <ip> <port number> 6 <authToken> <message_id>`              |
