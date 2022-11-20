package busManagement.domain.validators;

import busManagement.domain.BusCompany;
import busManagement.domain.BusStation;
import busManagement.domain.Feedback;
import busManagement.domain.exceptions.ValidatorException;
import busManagement.utils.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class FeedbackValidator  implements Validator<Feedback> {

    @Override
    public void validate(Feedback entity) throws ValidatorException {

        Predicate<Pair<Long,Long>> idIsNull = (id) -> (id.getFirst() == null || id.getFirst() < 0 ||
                id.getSecond() == null || id.getSecond() < 0 );
        boolean idNotValid = idIsNull.test(entity.getId());
        String idIsNotValidMessage = "Ids must not be null and greater than 0! ";

        Predicate<String> reviewIsNull = (s) -> (s == null || s.equals(""));
        boolean reviewNotValid = reviewIsNull.test(entity.getReview());
        String reviewNotValidMessage = "The review cannot be empty";

        List<Pair<Boolean, String>> checkList = Arrays.asList(new Pair<>(idNotValid,idIsNotValidMessage),
                new Pair<>(reviewNotValid,reviewNotValidMessage));

        checkList.stream()
                .filter(Pair::getFirst)
                .map(Pair::getSecond)
                .reduce(String::concat)
                .ifPresent(s -> {throw new ValidatorException(s);});
    }

}
