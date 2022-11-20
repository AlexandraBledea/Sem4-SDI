package busManagement.domain.validators;

import busManagement.domain.Luggage;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class LuggageValidator implements Validator<Luggage>{

    @Override
    public void validate(Luggage entity){
        Predicate<Long> isIdNull = (l) -> (l == null || l < 0);
        boolean idNotValid = isIdNull.test(entity.getId());
        String idIsNotValidMessage = "Id mut not be null and greater than 0!";

        Predicate<Long> passengerIdIsNull = (l) -> (l == null || l < 0);
        boolean passengerIdNotValid = passengerIdIsNull.test(entity.getPassengerId());
        String passengerIdNotValidMessage = "PassengerId must not be null and greater than 0!";

        Predicate<Integer> weightIsNull = (i) -> (i == null || i <= 0);
        boolean weightNotValid = weightIsNull.test(entity.getWeight());
        String weightNotValidMessage = "Weight must not be null and greather than 0!";

        List<Pair<Boolean,String>> checkList = Arrays.asList(new Pair<>(idNotValid,idIsNotValidMessage),
                new Pair<>(passengerIdNotValid,passengerIdNotValidMessage), new Pair<>(weightNotValid, weightNotValidMessage));

        checkList.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresent(s -> {throw new ValidatorException(s);});
    }
}
