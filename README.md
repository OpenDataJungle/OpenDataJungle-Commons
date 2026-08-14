# OpenDataJungle Commons

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/projects/jdk/25/)
[![CI Pipeline](https://github.com/OpenDataJungle/OpenDataJungle-Commons/actions/workflows/ci.yml/badge.svg)](https://github.com/OpenDataJungle/OpenDataJungle-Commons/actions/workflows/ci.yml)

Shared Spring Boot building blocks for OpenDataJungle projects.

## Features

- OAuth2/JWT resource server security, with a permissive profile for `test`/`local`
- Configurable CORS
- Global exception handler with a consistent JSON error payload
- Request tracing via MDC (correlation ID, user, IP, duration...)
- Preconfigured `RestClient` HTTP client
- Common utilities (`StringUtils`, `CollectionUtils`, `DateUtils`, `UserUtils`)

## Installation

```xml
<dependency>
    <groupId>com.opendatajungle</groupId>
    <artifactId>opendatajungle-commons</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Build

```bash
./mvnw verify
```

## Contributing

Issues and pull requests are welcome: https://github.com/OpenDataJungle/OpenDataJungle-Commons

## License

[GNU General Public License v3.0](LICENSE)

## Contact

**LEMAIRE Alexandre** — [OpenDataJungle](https://opendatajungle.com/) — contact@opendatajungle.com
