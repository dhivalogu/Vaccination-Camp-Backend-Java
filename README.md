# Vaccination Camp Management System

A java web application for Vaccination Camp Management System where people can register for vaccination,view/download e-certifcates and an admin panel to overview the data and install camps in cities and approve vaccination.

****Requirements****

**Functional Description**

1.People can be able to register in the system with their details and can book a vaccination slot on a chosen date at their preferred city/camp

2.Admin can be able to view/add city,camp

3.People can  download e-certificate after completing vaccination and and also they can download booking receipt after booking

4.Admin can view overall summary city wise and can also update the stock of the city

5.Admin can approve the vaccination after the registered person gets vaccinated.

**Constraints**

1.There should be 45 days gap between doses

2.If the person has completed all the doses then registration should not be allowed

3.One user can have only 1 upcoming booking at a time

4.While registration a person's age must be >=18 years

5.If the city doesn't have any  camps/have fewer stock then the particular city should not allow further registrations

6.Only 10 bookings per slot should be allowed

7.As of now only 2 doses will be allowed for  each person
Use Case Diagram:
<img width="510" alt="Use Case Diagram" src="https://user-images.githubusercontent.com/46838309/198826765-8b9b1ff5-e0b6-4fa3-b01f-bb9bd5ae7c31.png">

Database Design:
![VACCINATION-ER-DIAGRAM](https://user-images.githubusercontent.com/46838309/198826726-ca76ef76-0123-4ab4-abf1-ee706377bb07.png)

****API****  
**Structure**

| Method | URL | Description |
| --- | --- | --- |
| GET | /api/cities | Get overall data of all the cities |
| GET | /api/cities/1/camps | Get all the camps data in a given city |
| GET | /api/cities/1/camps/2 | Get details of the given camp in a given city |
| GET | /api/cities/1/camps/2/slots | Get all the slots data of the given camp |
| GET | /api/cities/1/camps/2/slots/1 | Get the data of the given camp |
| GET | /api/cities/1/camps/2/slots/1/bookings | Get all the bookings in the given camp |
| GET | /api/people/589276589087 | Get the data of the given user |
| GET | /api/people/589276589087/bookings | Get the bookings data of the given user |
| POST | /api/cities | Add a city |
| POST | /api/cities/1/camps | Add a camp |
| POST | /api/people | Add a user |
| POST | /api/cities/1/camps/2/slots/3/bookings | Add a booking |
| PUT | /api/cities/1/camps/2/slots/3/bookings/4 | Update booking details |
| PUT | /api/cities/1 | Update city details |
| GET | /api/authentication | verify the current user/session details |
| GET | /api/logout | invalidate the current session |
