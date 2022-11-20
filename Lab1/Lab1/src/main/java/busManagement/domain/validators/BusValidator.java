package busManagement.domain.validators;
import busManagement.domain.Bus;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class BusValidator implements Validator<Bus> {

    public BusValidator() {

    }

    @Override
    public void validate(Bus entity) throws ValidatorException {

        Predicate<Long> idIsNullOrNegative = (l) -> (l == null || l < 0);
        final Pair<Boolean, String> resultId =
                new Pair<>(idIsNullOrNegative.test(entity.getId()), "Id must not be null or negative!\n");

        Predicate<Long> companyIdIsNullOrNegative = (l) -> (l == null || l < 0);
        final Pair<Boolean, String> resultCompanyId =
                new Pair<>(companyIdIsNullOrNegative.test(entity.getCompanyId()), "CompanyId must not be null or negative!\n");

        Predicate<Long> driverIdIsNullOrNegative = (l) -> (l == null || l < 0);
        final Pair<Boolean, String> resultDriverId =
                new Pair<>(driverIdIsNullOrNegative.test(entity.getDriverId()), "DriverId must not be null or negative!\n");

        Predicate<String> modelNameIsNull = (s) -> (s == null || s.equals(""));
        final Pair<Boolean, String> resultModelName =
                new Pair<>(modelNameIsNull.test(entity.getModelName()), "Bus must have a non-null and non-empty ModelName!\n");

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(resultId);
        testList.add(resultCompanyId);
        testList.add(resultDriverId);
        testList.add(resultModelName);

        testList.stream()
                .filter((p) -> p.getFirst().equals(true))
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresent((msg) -> {throw new ValidatorException(msg);});

    }
}
