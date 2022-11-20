package common.validators;


import common.domain.BusStation;
import common.exceptions.ValidatorException;
import common.utils.Pair;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class BusStationValidator implements Validator<BusStation>{


    @Override
    public void validate(BusStation entity) throws ValidatorException {
        Predicate<Long> idIsNull = (l)->(l == null || l < 0);
        boolean idNotValid = idIsNull.test(entity.getId());
        String idIsNotValidMessage = "Id must not be null and greater than 0! ";

        Predicate<Long> cityIdIsNull = (l) -> (l == null || l < 0);
        boolean cityIdNotValid = cityIdIsNull.test(entity.getCityId());
        String cityIdNotValidMessage = "CityId must not be null and greater than 0! ";

        Predicate<String> nameIsNull = (s) -> (s == null || s.equals(""));
        boolean nameNotValid = nameIsNull.test(entity.getName());
        String nameNotValidMessage = "BusStation must have a name! ";

        List<Pair<Boolean, String>> checkList = Arrays.asList(new Pair<>(idNotValid, idIsNotValidMessage),
                new Pair<>(cityIdNotValid, cityIdNotValidMessage), new Pair<>(nameNotValid, nameNotValidMessage));

        checkList.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresent(s -> {throw new ValidatorException(s);});

    }
}
