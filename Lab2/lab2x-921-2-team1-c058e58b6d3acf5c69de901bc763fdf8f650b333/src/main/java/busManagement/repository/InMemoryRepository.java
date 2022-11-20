package busManagement.repository;

import busManagement.domain.validators.Validator;
import busManagement.domain.BaseEntity;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;
import busManagement.domain.exceptions.BusManagementException;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    private Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }


    protected void loadEntities(List<T> newEntities) {
        newEntities.forEach((e) -> entities.put(e.getId(), e));
    }


    /**
     * Find the entity with the given {@code id}.
     *
     * @param id
     *            must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> findOne(ID id) {


        Optional<ID> idOptional = Optional.ofNullable(id);

        idOptional.ifPresentOrElse(
                (io) -> {},

                () -> {throw new IllegalArgumentException("id must not be null");}
        );

        return Optional.ofNullable(entities.get(id));


//        if (id == null) {
//            throw new IllegalArgumentException("id must not be null");
//        }
//        return Optional.ofNullable(entities.get(id));
    }


    /**
     *
     * @return all entities.
     */
    @Override
    public Iterable<T> findAll() {
        return new HashSet<>(entities.values());
    }

    /**
     * Saves the given entity into the repository.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     * @throws BusManagementException
     *            if the id is not unique.
     */
    @Override
    public Optional<T> save(T entity) throws ValidatorException {

        Optional<T> entityOptional = Optional.ofNullable(entity);

        entityOptional.ifPresentOrElse(
                (eo) -> {},

                () -> {throw new IllegalArgumentException("entity must not be null");}
        );

        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));


//        if (entity == null) {
//            throw new IllegalArgumentException("id must not be null");
//        }
//        validator.validate(entity);
//        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id
     *            must not be null.
     * @return an {@code Optional} - null if there is no entity with the given id, otherwise the deleted entity.
     * @throws IllegalArgumentException
     *             if the given id is null.
     */
    @Override
    public Optional<T> delete(ID id) {


        Optional<ID> idOptional = Optional.ofNullable(id);

        idOptional.ifPresentOrElse(
                (io) -> {},

                () -> {throw new IllegalArgumentException("id must not be null");}
        );

        return Optional.ofNullable(entities.remove(id));

//        if (id == null) {
//            throw new IllegalArgumentException("id must not be null");
//        }
//
//        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates the given entity.
     *
     * @param entity
     *            must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise (e.g. id doesn't exist) returns the
     *         entity.
     * @throws IllegalArgumentException
     *             if the given entity is null.
     * @throws ValidatorException
     *             if the entity is not valid.
     */
    @Override
    public Optional<T> update(T entity) throws ValidatorException {

        Optional<T> entityOptional = Optional.ofNullable(entity);

        entityOptional.ifPresentOrElse(
                (eo) -> {},

                () -> {throw new IllegalArgumentException("entity must not be null");}
        );

        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));


//        if (entity == null) {
//            throw new IllegalArgumentException("entity must not be null");
//        }
//        validator.validate(entity);
//        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
