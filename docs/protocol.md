# Command Protocol
These are concrete examples of the expected format and protocol that commands
will follow between the server and the client. 

### Terminology
- **Client Commands** refers to commands _for_ the client, commands that the server
    sends to the client.
- **Server Commands** refers to commands _for_ the server, commands that the client
    sends to the server.

## HTTP Endpoint and Methods

Commands will be sent to and from the `/command` endpoint on the backend.

The only command that will be processed without an `Authorization` header are
the `Register` and `Login` commands when sent to the `/register` endpoint.

|  Endpoint  | HTTP Method | Request Body                | Response Body               | Description                    |
|------------|-------------|-----------------------------|-----------------------------|--------------------------------|
| `/command` | `POST`      | A server command            | An array of client commands | Endpoint used to send commands. Must have `Authorization` header |
| `/command` | `GET`       | N/A                         | An array of client commands | Generic endpoint for polling   |
| `/register`| `POST`      | A login/register command    | An array of client commands (same format as `/command` endpoint response) | Endpoint used for authentication. No `Authorization` header present. |

| Response Code     | Reason                                                                                                     |
|-------------------|------------------------------------------------------------------------------------------------------------|
| `200 OK`          | The server was able to process the `POST`ed commands and able to transmit any pending client commands      |
| `400 BAD REQUEST` | The server was unable to process the `POST`ed commands due to a syntax error                               |
| `401 UNAUTHORIZED`| The client tried to `POST` a command to `/command` without an auth token                                   |
| `501 NOT IMPLEMENTED` | The client tried to `POST` a command the server doesn't understand yet                                 |
| `500 INTERNAL ERROR`  | Something went wrong serverside                                                                        |

### Example `POST` to `/command` or `/register` endpoint
`Authorization: {token}` (if `/command`)
```json
{
    "command": "login",
    "username": "uname",
    "password": "password"
}

```

### Example response from server to any request

`200 OK`
```json
{
    "commands": [
        {
            "command": "loginResult",
            "error": "That username is already taken"
        },
        {
            "command": "displayGameList",
            "games": [
                {
                    "id": "KDD39-KDF42",
                    "name": "Bob's Epic Game",
                    "players": // etc
                }
            ]
        }
    ]
}
```
