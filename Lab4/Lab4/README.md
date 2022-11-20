# Requirements

- Links to an external site. and work on the generated github repository, on the same problem from the previous lab; only a many-to-many and a one-to-many relationship are needed from the previous lab/problem, the entities that are not involved in these relationships may be dropped
- implement all crud operations, including operations on the relations, i.e., that involve aggregating data from several related entities
- use a client-server architecture with sockets
- simulate an RPC server (messages sent between the client and the server must be handled in a unitary manner (simulate RPC calls) ) 
- the server must be concurrent
- use Java 8 language features; threads with ExecutorService 
- only DB persistence is needed
- for the DB persistence, use JdbcTemplate (Spring)
- use Gradle for dependency management 
- XML configuration for Spring is not allowed; annotations and Java Config classes only 
- there should be three modules: client, server, common; the client and server modules depend on the common module; the module dependencies should be implemented in gradle
- a layered architecture should be used (ui, service, repository)
