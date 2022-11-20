package core.validators;



import core.domain.BusStop;
import core.exceptions.ValidatorException;
import core.utils.Pair;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


@Component
public class BusStopValidator  implements Validator<BusStop>{
    @Override
    public void validate(BusStop entity) throws ValidatorException {
        Predicate<Pair<Long, Long>> idIsNullOrNegative = (l) -> (l == null || l.getFirst() < 0 || l.getSecond() < 0);
        final Pair<Boolean, String> resultId =
                new Pair<>(idIsNullOrNegative.test(new Pair<>(entity.getBusId(), entity.getStationId())), "Components of Id must not be null or negative!\n");

        Predicate<String> modelNameIsNull = Objects::isNull; // why is this a thing
        final Pair<Boolean, String> resultStopTime =
                new Pair<>(modelNameIsNull.test(entity.getStopTime()), "Bus Stop must have a non-null and greater than zero Stop Time!\n");

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(resultId);
        testList.add(resultStopTime);

        testList.stream()
                .filter((p) -> p.getFirst().equals(true))
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresent((msg) -> {throw new ValidatorException(msg);});

    }
}
