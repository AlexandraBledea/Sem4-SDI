package busManagement.domain.validators;

import busManagement.domain.Driver;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DriverValidator implements Validator<Driver> {
    public DriverValidator() {}

    @Override
    public void validate(Driver entity) throws ValidatorException {
        PersonValidator validator = new PersonValidator();
        validator.validate(entity);

        Predicate<Integer> monthsAreNullOrNegative = (monthsActive) -> (monthsActive == null || monthsActive < 0);
        Pair<Boolean, String> monthsTestPair = new Pair<>(
                monthsAreNullOrNegative.test(entity.getMonthsActive()),
                "Months active must be non null and positive!\n"
        );

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(monthsTestPair);

        testList.stream().
                filter(Pair::getFirst).
                map(Pair::getSecond).
                reduce(String::concat).
                ifPresent(message -> {
                    throw new ValidatorException(message);
                });
    }
}
