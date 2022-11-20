package busManagement.domain.validators;

import busManagement.domain.Ticket;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class TicketValidator implements Validator<Ticket>{

    public TicketValidator(){

    }

    @Override
    public void validate(Ticket entity) throws ValidatorException {

        Predicate<Long> idIsNullOrNegative = (l) -> (l == null || l < 0);
        final Pair<Boolean, String> resultId =
                new Pair<>(idIsNullOrNegative.test(entity.getId()), "Id must not be null or negative!\n");

        Predicate<Long> passagerIdIsNullOrNegative = (l) -> (l == null || l < 0);
        final Pair<Boolean, String> resultPassagerId =
                new Pair<>(passagerIdIsNullOrNegative.test(entity.getPassengerId()), "PassengerId must not be null or negative!\n");

        Predicate<Long> busIdIsNullOrNegative = (l) -> (l == null || l < 0);
        final Pair<Boolean, String> resultBusId =
                new Pair<>(busIdIsNullOrNegative.test(entity.getBusId()), "BusId must not be null or negative!\n");

        Predicate<LocalTime> boardingTimeNullOrInvalid = (i) -> (i == null);
        final Pair<Boolean, String> resultBoardingTime =
                new Pair<>(boardingTimeNullOrInvalid.test(entity.getBoardingTime()), "Ticket must have a non-null BoardingTime!\n");

        Predicate<Long> priceNullOrNegative = (i) -> (i == null || i < 0);
        final Pair<Boolean, String> resultPrice =
                new Pair<>(priceNullOrNegative.test(entity.getPrice()), "Ticket must have a non-null and not negative Price!\n");

        List<Pair<Boolean, String>> testList = new ArrayList<>();

        testList.add(resultId);
        testList.add(resultPassagerId);
        testList.add(resultBusId);
        testList.add(resultBoardingTime);
        testList.add(resultPrice);

        testList.stream()
                .filter((p) -> p.getFirst().equals(true))
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresent((msg) -> {throw new ValidatorException(msg);});
    }

}