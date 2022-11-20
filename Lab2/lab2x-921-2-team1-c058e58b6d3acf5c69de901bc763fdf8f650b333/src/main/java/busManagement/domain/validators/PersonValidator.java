package busManagement.domain.validators;

import busManagement.domain.Person;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PersonValidator implements Validator<Person> {
    public PersonValidator() {}

    @Override
    public void validate(Person entity) throws ValidatorException {
        Predicate<Long> idIsNullOrNegative = (id) -> (id == null || id < 0);
        Pair<Boolean, String> idTestPair = new Pair<>(
                idIsNullOrNegative.test(entity.getId()),
                "Id must be non null and positive!\n"
        );

        Predicate<String> nameNonEmpty = (name) -> (name == null || name.equals(""));
        Pair<Boolean, String> firstNameTestPair = new Pair<>(
                nameNonEmpty.test(entity.getFirstName()),
                "First name must be non null and non empty!\n"
        );

        Pair<Boolean, String> lastNameTestPair = new Pair<>(
                nameNonEmpty.test(entity.getLastName()),
                "Last name must be non null and non empty!\n"
        );

        Predicate<LocalDate> dateValid = (date) -> (date == null || date.isAfter(LocalDate.now()));
        Pair<Boolean, String> dateTestPair = new Pair<>(
                dateValid.test(entity.getDateOfBirth()),
                "Date must be non null and before the current date!\n"
        );

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(idTestPair);
        testList.add(firstNameTestPair);
        testList.add(lastNameTestPair);
        testList.add(dateTestPair);

        testList.stream().
                filter(Pair::getFirst).
                map(Pair::getSecond).
                reduce(String::concat).
                ifPresent(message -> {
                    throw new ValidatorException(message);
                });
    }
}
