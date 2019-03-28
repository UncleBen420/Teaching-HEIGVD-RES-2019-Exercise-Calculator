### Rémy Vuagniaux et Erwan Moreira

## What transport protocol do we use?

We use TCP/IP

## How does the client find the server (addresses and ports)?

He has the address and the port in a configuration file.


## Who speaks first?

The client speak first.

## What is the sequence of messages exchanged by the client and the server?

The server accept an incoming connection

The server create a socket for that connection

The client write a calcul

The server read, calcul and return the response.

The client read the response

The server close the socket

## What happens when a message is received from the other party?

The client send a aquitement response.

## What is the syntax of the messages? How we generate and parse them?

With a socket on each side of the connection and with writer reader


## Who closes the connection and when?

The client, when the response to the calcul has been reseve.

And the server when he has send the response.
