# Distibuted Systems Banking Application

## Usage

```
cd bin
rmiregistry 7777 &
java server.Bank 7777
```

```
cd bin
java client.ATM localhost 7777 login User1 pass1
```

Copy SessionID and use in all future commands

```
java client.ATM localhost 7777 login <Username> <password>
java client.ATM localhost 7777 inquiry <accountNumber> <sessionID>
java client.ATM localhost 7777 deposit <accountNumber> <amount> <sessionID>
java client.ATM localhost 7777 withdraw <accountNumber> <amount> <sessionID>
java client.ATM localhost 7777 statement <accountNumber> <start Date> <end Date> <sessionID>
```

