# SafeLog

A Minecraft server side Fabric mod that redacts IP addresses in logs for privacy and compliance purposes.

## Places redacted

* Player login: `[Server thread/INFO] (Minecraft) Notch[/[0:0:0:0:0:0:0:1%0]:56566] logged in with entity id 50 at (5.5, 64.0, 0.5)`
* Authentication error: `[Server thread/INFO] (Minecraft) /127.0.0.1:43832 lost connection: Failed to verify username!`

## Effect

IP addresses will be MD5 hashed with an optional and customizable salt. Leading slashes and port numbers are preserved.

`[Server thread/INFO] (Minecraft) Notch[/[0:0:0:0:0:0:0:1%0]:56566] logged in with entity id 50 at (5.5, 64.0, 0.5)` will become `[Server thread/INFO] (Minecraft) Notch[/fec2cae1c0333d98b453058e9f6c99d9:56566] logged in with entity id 50 at (5.5, 64.0, 0.5)`.

`[Server thread/INFO] (Minecraft) /127.0.0.1:43832 lost connection: Failed to verify username!` will become `[Server thread/INFO] (Minecraft) /f528764d624db129b32c21fbca0cb8d6:43832 lost connection: Failed to verify username!`.

## Salting

Edit `config/safelog.properties`:

```properties
salt = 114514
```

SafeLog will append real IP addresses after the salt before hashing. Default salt is empty.

Warning: SafeLog will only hash raw IP addresses, so make sure to set a salt to prevent hash cracking.

## Building

Tested on Gradle 7.2.

`gradle jar`

## License

GPL v2 only