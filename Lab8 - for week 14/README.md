# Requirements

- continue to work on the repository from the previous lab

- all associations must be lazily loaded

- after switching to Lazy fetching, check if the LazyInitializationException actually appears before trying to ‘handle' it (in case you are using Spring Boot, some settings might be needed in this sense - otherwise everything is fetched eagerly)

- query the entities using: Spring Queries with Named Entity Graphs,  JPQL, Criteria API, Native SQL

- in each repository (e.g: BookRepository, ClientRepository etc) there should be at least two methods using NamedEntityGraphs

- for each repository (e.g: BookRepository, ClientRepository etc), in the corresponding fragment/customized interface, there should be at least two additional methods; these  additional methods should have three different implementations with: JPQL, CriteriaAPI, NativeSql

- in the services only the 'main' repositories should be used (e.g: BookRepository and ClientRepository, not the fragment/customized ones)

- the application should work alternatively with all of the following configurations: EntityGraphs + JPQL, EntityGraphs + CriteriaAPI, EntityGraphs + NativeSql. The configuration switch should be possible by changing annotations or property files, but not java code

- write integration tests for your repositories and services; use DbUnit, xml datasets

- write unit tests for your controllers using Mockito 
