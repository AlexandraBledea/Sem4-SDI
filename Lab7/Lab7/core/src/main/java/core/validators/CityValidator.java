package core.validators;




import core.domain.City;
import core.exceptions.ValidatorException;
import core.utils.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


@Component
public class CityValidator implements Validator<City>{
    @Override
    public void validate(City entity) throws ValidatorException {
        Predicate<Long> idIsNull = (l)->(l == null || l < 0);
        boolean idNotValid = idIsNull.test(entity.getId());
        String idIsNotValidMessage = "Id must not be null and greater than 0! ";

        Predicate<String> nameIsNull = (s)->(s==null || s.equals(""));
        boolean nameNotValid = nameIsNull.test(entity.getName());
        String nameNotValidMessage = "City must have a name! ";

        Predicate<Integer> populationOutOfBoundaries = (i) -> (i == null || i <= 0);
        boolean populationNotValid = populationOutOfBoundaries.test(entity.getPopulation());
        String populationNotValidMessage = "Population must be greater than 0! ";

        List<Pair<Boolean, String>> checkList = Arrays.asList(new Pair<>(idNotValid, idIsNotValidMessage),
                new Pair<>(nameNotValid, nameNotValidMessage),
                new Pair<>(populationNotValid, populationNotValidMessage));

        checkList.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .reduce((a,b)->(a + b))
                .ifPresent(s -> {throw new ValidatorException(s);});
    }
}
