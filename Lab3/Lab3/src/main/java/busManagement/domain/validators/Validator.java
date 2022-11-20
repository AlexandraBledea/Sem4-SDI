package busManagement.domain.validators;

import busManagement.domain.exceptions.ValidatorException;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
