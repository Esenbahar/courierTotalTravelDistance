# Courier Total Travel Distance Calculation

This project a restful web application with Java.
The geolocations of the couriers is taken as basis.

## Requirements

For building and running the application you need:

- Git
- JDK 8 or later
- Maven 3.0 or later

## Clone

```python
mvn spring-boot:run
```

## Running the app locally
### Info
> Edit "stores.json" file path according to you so that json data can be read. In this service : com.migros.migrosproject.service.impl.MigrosCourierTrackingServiceImpl

>Edit "couriers.json" file path according to you so that json data can be read. In this service : com.migros.migrosproject.service.impl.MigrosCourierTrackingServiceImpl

There are several ways to run a Spring Boot application on your local machine. One way is to execute the main method in the com.migros.migrosproject.CourierTrackingProjectApplication.class from your IDE.

Alternatively you can use the Spring Boot Maven plugin like so:
```python
mvn spring-boot:run
```
## Access

| Link | Example Link | Result |
| -----|--------------|--------|
| http://localhost:8080/check-courier?courier=YourCourierName&lat=YourCourierLat&lng=YourCourierLng | http://localhost:8080/check-courier?courier=Esen&lat=40.9932317&lng=29.1244229 | "Check Courier Distance !!" |
| http://localhost:8080//travel-distance?courier=YourCourierName| http://localhost:8080//travel-distance?courier=Esen | "Courier Total Travel Distance :601.1197734445115" |

## Contributing
Pull requests are welcome.

## License
This project is licensed under the terms of the MIT license.

