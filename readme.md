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
java client.ATM localhost 7777 inquiry 5628291 <sessionID>
java client.ATM localhost 7777 deposit 5628291 20 <sessionID>
```

