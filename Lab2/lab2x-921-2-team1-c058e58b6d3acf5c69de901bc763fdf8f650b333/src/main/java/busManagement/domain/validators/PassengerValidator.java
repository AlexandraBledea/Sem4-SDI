package busManagement.domain.validators;

import busManagement.domain.Passenger;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class PassengerValidator implements Validator<Passenger> {
    public PassengerValidator(){}

    @Override
    public void validate(Passenger entity) throws ValidatorException {
        PersonValidator validator = new PersonValidator();
        validator.validate(entity);

        Predicate<Integer> timesTravelledNullOrNegative = (timesTravelled) -> (timesTravelled == null || timesTravelled < 0);
        Pair<Boolean, String> timesTravelledPair = new Pair<>(
                timesTravelledNullOrNegative.test(entity.getTimesTravelled()),
                "Times travelled must be non null and positive!\n"
        );

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(timesTravelledPair);

        testList.stream().
                filter(Pair::getFirst).
                map(Pair::getSecond).
                reduce(String::concat).
                ifPresent(message -> {
                    throw new ValidatorException(message);
                });
    }
}
