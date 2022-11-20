package busManagement.domain.validators;

import busManagement.domain.BusCompany;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class BusCompanyValidator implements Validator<BusCompany> {

    @Override
    public void validate(BusCompany entity) throws ValidatorException {
        Predicate<Long> isIdNull = (id) -> (id == null || id < 0);
        boolean idNotValid = isIdNull.test(entity.getId());
        String idNotValidMessage = "Id must not be null and greater than 0! ";

        Predicate<String> isNameNull = (s)->(s==null || s.equals(""));
        Boolean nameNotValid = isNameNull.test(entity.getName());
        String nameNotValidMessage = "Bus Company must have a name! ";

        List<Pair<Boolean, String>> checkList = Arrays.asList(
                new Pair<>(idNotValid, idNotValidMessage),
                new Pair<>(nameNotValid, nameNotValidMessage));

        checkList.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .reduce((a,b)->(a + b))
                .ifPresent(s -> {throw new ValidatorException(s);});
    }
}
