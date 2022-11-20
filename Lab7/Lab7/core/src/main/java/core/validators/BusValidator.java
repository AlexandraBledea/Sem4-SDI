package core.validators;

import core.domain.Bus;
import core.exceptions.ValidatorException;
import core.utils.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


@Component
public class BusValidator implements Validator<Bus> {

    public BusValidator() {

    }

    @Override
    public void validate(Bus entity) throws ValidatorException {

        Predicate<Long> idIsNullOrNegative = (l) -> (l == null || l < 0);
        final Pair<Boolean, String> resultId =
                new Pair<>(idIsNullOrNegative.test(entity.getId()), "Id must not be null or negative!\n");

        Predicate<String> modelNameIsNull = (s) -> (s == null || s.equals(""));
        final Pair<Boolean, String> resultModelName =
                new Pair<>(modelNameIsNull.test(entity.getModelName()), "Bus must have a non-null and non-empty ModelName!\n");

        Predicate<String> fuelIsNull = (s) -> (s == null || s.equals(""));
        final Pair<Boolean, String> resultFuel =
                new Pair<>(fuelIsNull.test(entity.getFuel()), "Bus fuel must have a non-null and non-empty fuel!\n");

        Predicate<Integer> capacityIsNull = (i) -> (i <= 0);
        final  Pair<Boolean, String> resultCapacity =
                new Pair<>(capacityIsNull.test(entity.getCapacity()), "Bus capacity must be greater than 0!\n");


        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(resultId);
        testList.add(resultModelName);
        testList.add(resultFuel);
        testList.add(resultCapacity);

        testList.stream()
                .filter((p) -> p.getFirst().equals(true))
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresent((msg) -> {throw new ValidatorException(msg);});
    }
}
