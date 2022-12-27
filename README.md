# DataWarehouseReader

## Leverantör

Sundsvalls kommun

## Beskrivning

DataWarehouseReader är en tjänst som tillhandahåller information från olika data-källor.


## Tekniska detaljer

### Starta tjänsten

|Miljövariabel|Beskrivning|
|---|---|
|**Integration mot Stadsbackens datalager**|
|`spring.datasource.url`|JDBC URL till Stadsbackens datalager|
|`spring.datasource.username`|Användare till Stadsbackens datalager|
|`spring.datasource.password`|Lösenord till Stadsbackens datalager|
|**Integration mot Party**|
|`integration.party.url`|URL för endpoint till Party service i WSO2|
|`spring.security.oauth2.client.registration.party.client-id`|Klient-ID som ska användas mot Party service|
|`spring.security.oauth2.client.registration.party.client-secret`|Klient-secret som ska användas mot Party service|
|`spring.security.oauth2.client.provider.party.token-uri`|URI till endpoint för att förnya token mot Party service|

### Paketera och starta tjänsten
Applikationen kan paketeras genom:

```
./mvnw package
```
Kommandot skapar filen `api-service-datawarehousereader-<version>.jar` i katalogen `target`. Tjänsten kan nu köras genom kommandot `java -jar target/api-service-datawarehousereader-<version>.jar`.

### Bygga och starta med Docker
Exekvera följande kommando för att bygga en Docker-image:

```
docker build -f src/main/docker/Dockerfile -t api.sundsvall.se/ms-datawarehousereader:latest .
```

Exekvera följande kommando för att starta samma Docker-image i en container:

```
docker run -i --rm -p 8080:8080 api.sundsvall.se/ms-datawarehousereader
```

#### Kör applikationen lokalt

Exekvera följande kommando för att bygga och starta en container i sandbox mode:  

```
docker-compose -f src/main/docker/docker-compose-sandbox.yaml build && docker-compose -f src/main/docker/docker-compose-sandbox.yaml up
```

## 
Copyright (c) 2021 Sundsvalls kommun