package busManagement.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Passenger extends Person{
    private Integer timesTravelled;

    public Passenger(String firstName, String lastName, LocalDate dateOfBirth, Integer timesTravelled) {
        super(firstName, lastName, dateOfBirth);
        this.timesTravelled = timesTravelled;
    }

    public Integer getTimesTravelled(){
        return timesTravelled;
    }

    public void setTimesTravelled(Integer timesTravelled) {
        this.timesTravelled = timesTravelled;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Passenger))
            return false;
        if (this == o)
            return true;

        Passenger passenger = (Passenger) o;
        return Objects.equals(this.getId(), passenger.getId()) &&
                Objects.equals(this.getLastName(), passenger.getLastName()) &&
                Objects.equals(this.getDateOfBirth(), passenger.getDateOfBirth()) &&
                Objects.equals(this.timesTravelled, passenger.getTimesTravelled());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());

        builder.replace(0, 6, "Passenger");
        builder.deleteCharAt(builder.length() - 1);
        builder.append(", TimesTravelled=").append(timesTravelled).append("}");

        return builder.toString();
    }
}
