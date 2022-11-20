# Requirements


    - continue to work on the generated repository from the previous lab
    - convert your previous project by replacing RPC with REST services using Spring RestTemplate 
    - use Spring DI and Spring Data JPA (Hibernate) --- xml config forbidden 

    - log messages using SLF4J 
    - project structure: 

        1. A core module containing services, repositories, model classes 
        2. A web module containing controllers exposed as RESTful Web Services 
        3. A client module containing a console-based ui that accesses the RESTful Web Services using RestTemplate.

    - implement all CRUD operations as well as statistics/reports that aggregate information from several related entities 

    - for maximum number of points: filter and sorting have to take place at the repository level without explicitly writing any specific code for such features (this is a self study task – see readme for documentation); so, for example, at the service level it should be possible to call a method like repository.findStudentsByName(“Ana”), which returns all students with the name “Ana”, without providing an implementation (Java code) for this method. 
