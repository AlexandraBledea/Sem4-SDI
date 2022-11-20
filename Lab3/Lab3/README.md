# Requirements

- continue to work in the same teams from Lab 2 and on the same repository

- add DB repositories; the DB repositories will directly implement the Repository interface from the example, i.e., the DB repositories will not extend the InMemoryRepository; see the documentation of each function from the Repository interface - the implementations MUST be semantically the same; you may use any RDBMS except Microsoft SQLServer

- use feature branches: there should be a default master/main branch, a develop branch, feature branches (one branch for each feature); feature branches start from develop and are merged back into develop.

 

In order to deliver the lab, submit the link to your github repository. This should only be done by one of the team members.

# Bus Management Application
---
The application is managing some bus companies. Each company has more than one bus. Each bus belongs to one bus company.
Each bus has one driver, more bus stops and more tickets. Each driver belongs to only one bus.
Each bus stop has only one bus station, and each bus station belongs to exactly one city. Each city can have more bus stations, and each bus station can have more bus stops.
Each ticket belongs to only one passager and each passager can have more than one ticket. Each passager can have more luggages and each luggage belongs to only one passager. 
Each passager is exactly one person and each driver is exactly one person. 
Each passager can give more feedbacks to the bus company and each feedback belongs to only one passager. 
---
Functionalities:
---
## View:
- View all luggages
- View all passagers
- View all drivers
- View all tickets
- View all buses
- View all feedbacks
- View all bus companies
- View all bus stops
- View all bus stations
- View all cities
---
## Add:
- Add a luggage
- Add a passager
- Add a feedback
- Add a bus company
- Add a ticket
- Add a bus
- Add a driver
- Add a bus stop
- Add a bus station
- Add a city
---
## Update:
- Update a luggage
- Update a passager
- Update a feedback
- Update a bus company
- Update a ticket
- Update a bus
- Update a driver
- Update a bus stop
- Update a bus station
- Update a city
---
## Delete:
- Delete a luggage
- Delete a passager
- Delete a feedback
- Delete a bus company
- Delete a ticket
- Delete a bus
- Delete a driver
- Delete a bus stop
- Delete a bus station
- Delete a city
---
## Sort:
- Sort the cities alphabetically
- Sort the bus stops in ascending order of their stop time
- Sort bus companies alphabetically
---
## Filter:
- Filter all the busses based on the bus station
- Filter all the feedbacks based on the bus company
- Filter all the tickets based on the bus
---
## Database diagram
![databaseDiagram](https://user-images.githubusercontent.com/72063091/160889242-ddc09a99-8cca-4c4d-8266-a7f9996bad9f.png)
