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

## Properties

#### Oauth2/JWT

| Variable         | Description                              | Default                               |
|------------------|------------------------------------------|---------------------------------------|
| `JWT_ISSUER_URI` | OAuth2/OIDC issuer used to validate JWTs | `http://localhost:8090/realms/master` |
| `JWT_AUDIENCES`  | Expected JWT audience(s)                 | `account`                             |

#### CORS

| Variable                 | Description                        | Default                                                         |
|--------------------------|------------------------------------|-----------------------------------------------------------------|
| `CORS_ALLOWED_ORIGINS`   | Allowed origins                    | localhost dev ports                                             |
| `CORS_ALLOWED_METHODS`   | Allowed HTTP methods               | `GET,POST,PUT,PATCH,DELETE,OPTIONS`                             |
| `CORS_ALLOWED_HEADERS`   | Allowed headers                    | `Authorization,Content-Type,X-Requested-With,Accept,Origin,...` |
| `CORS_EXPOSED_HEADERS`   | Headers exposed to the client      | `Access-Control-Allow-Origin,Access-Control-Allow-Credentials`  |
| `CORS_ALLOW_CREDENTIALS` | Allow credentials                  | `false`                                                         |
| `CORS_MAX_AGE`           | Preflight cache duration (seconds) | `3600`                                                          |

#### HTTP client, Logging & serialization

| Variable                                                   | Description                                 | Default    |
|------------------------------------------------------------|---------------------------------------------|------------|
| `HTTP_CLIENT_CONNECT_TIMEOUT` / `HTTP_CLIENT_READ_TIMEOUT` | Timeouts for outbound calls (seconds)       | `5` / `10` |
| `LOGGING_LEVEL_OPENDATAJUNGLE`                             | Log level for `com.opendatajungle` packages | `INFO`     |
| `JACKSON_TIME_ZONE`                                        | Jackson timezone                            | `UTC`      |

## Contributing

Issues and pull requests are welcome: https://github.com/OpenDataJungle/OpenDataJungle-Commons

## Contact

- **Website:** [www.opendatajungle.com](https://www.opendatajungle.com)
- **Email:** [contact@opendatajungle.com](mailto:contact@opendatajungle.com)
- **Organization:** [github.com/OpenDataJungle](https://github.com/OpenDataJungle)

## License

Licensed under the [GNU General Public License v3.0](LICENSE).
