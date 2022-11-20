# General Requirements
Lab 2

    Create two teams in each lab group; each team should join one of the existing groups from Canvas (ask your lab instructor for details)
    Choose a problem that is NOT listed in the lab-problems file (from the google drive); there should be at least one domain entity for each team member and at least two of the domain entities should be in a many-to-many relationship; it should be possible to perform operations on the many-to-many relationship, e.g., assign a grade to a student at the certain discipline.

 

    Create a GitHub account (if you don't already have one)

        Accept the assignment: https://classroom.github.com/a/AOR7aYa9

    At some point, you will be asked to CREATE or JOIN a team; one of the team members from the lab will CREATE a team; the other team members from the lab will JOIN the team name that his/her colleague has already created.

        Clone the generated repository
        The project for lab2x should be created inside this repository.

 

In order to deliver the lab, submit the link to your github repository. This should only be done by one of the team members.

 
General requirements

    design the solution for your problem using a CASE tool (use cases, class diagram, sequence diagram for each use case)

    use feature driven development

    layered architecture

    data validation

    all functions will be documented and tested

    use Java 8 features (lambda expressions, streams etc); the program should be written without if statements and loops

    persistence: ‘in memory’, text files, xml, db (jdbc); you may use any RDBMS, but we only offer support for PostgreSQL; MS SQL Server is forbidden

Links to an external site.Details:

The first example for lab2x is: catalog1_I1_inmemory_infile.zip

You will have to use the Repository interface from the example (no changes allowed there). Everything else is up to you (considering the grading scheme).

The following iteration plan contains the minimal features such that the starting grade (points) for each iteration is maximum:

I1 (deadline week 2):

    two features (e.g: addStudent and printAllStudents)

    java doc in html format (see the example from the group - project_root/doc/index.html - repository interface)

    only inmemory repository is enough

!!! focus on working with git - working on only one branch (master/develop) is enough

→ the project should be of type gradle

I2 (deadline week 3):

    three features

!!! focus on working with git - feature branches must be used

→ the project must be of type gradle

I3 (deadline week 4):

    all features

Grading scheme (out of 10p)

Architecture and coding style: 5.5p

Functionality: 4.5p

Functionality (4.5p):

    CRUD operations: 3p

    filter: 1p

    report: 0.5p

Architecture and coding style (5.5p):

    layered architecture; proper operation/responsibility separation between layers; single responsibility principle etc

    using Java 8 language features - where applicable; note that there are some redundant ifs and loops in my example (actually there are some other issues as well); the application should be written without any ifs and loops (using any if or loop will have to be justified for each occurrence)

    guard against null - where applicable

    data validation

    custom exceptions

    proper exception handling: exceptions should be wrapped in custom exception classes, thrown further, caught in the ui, where a message is presented to the user; the application should never crash, it should always resume; exceptions may also be only logged (for now ex.printStanckTrace() would do), but this decision will have to be justified for each occurrence; having more than one exception in a method signature will have to be justified for each occurrence

    using try-with-resource where applicable

    using NIO.2 where applicable

    using custom generic classes

    the application must be structured as in the example: src/main/java, src/test/java - mixing the tests with the actual code is forbidden

    using java doc comments for the important parts of the application; generate java doc (intellij idea: tools -> generate java doc).

-> there will be a 0.5p penalty of each type of mistake (only applicable starting from I2 (week 3)).

Only in week 4 (I3 - the last iteration):

    if the file persistence is missing: 1p penalty

    if the xml persistence is missing: 2p penalty

    if the db persistence is missing: 3p penalty.

Week 2:

You will probably encounter difficulties in working with git. If that is the case, don't worry it's normal at first; soon enough, it will be ok.

Next week, I would like to focus on these issues at the lab and on any possible difficulties that you foresee in meeting the requirements for lab2x.

Please note that we will approach the xml and db topics at the lecture and detailed examples on the group regarding these topics will follow.

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
