package busManagement.domain;

import  java.util.Objects;
import busManagement.utils.Pair;

public class Feedback extends BaseEntity<Pair<Long,Long>> {
    private String review;

    public Feedback(){

    }

    public Feedback(String review){
        this.review = review;
    }

    public String getReview() { return this.review; }

    public void setReview(String newReview) { this.review = newReview; }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        if(!Objects.equals(review, feedback.review)) return false;
        return Objects.equals(review, feedback.review);

    }

    @Override
    public String toString(){
        return "Feedback{" +
                "review='" + review + '\''
                +"}" + super.toString();
    }
}
