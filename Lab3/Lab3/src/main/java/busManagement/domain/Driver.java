package busManagement.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Driver extends Person {
    private Integer monthsActive;

    public Driver(String firstName, String lastName, LocalDate dateOfBirth, Integer monthsActive) {
        super(firstName, lastName, dateOfBirth);
        this.monthsActive = monthsActive;
    }

    public Integer getMonthsActive() {
        return monthsActive;
    }

    public void setMonthsActive(Integer newMonthsActive) {
        monthsActive = newMonthsActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Driver)) return false;

        return Objects.equals(((Driver) o).getId(), this.getId()) &&
                Objects.equals(((Driver) o).getFirstName(), this.getFirstName()) &&
                Objects.equals(((Driver) o).getLastName(), this.getLastName()) &&
                ((Person) o).getDateOfBirth().compareTo(this.getDateOfBirth()) == 0 &&
                Objects.equals(((Driver) o).getMonthsActive(), monthsActive);
    }

    @Override
    public String toString() {
        String initial = super.toString();

        StringBuilder builder = new StringBuilder(initial);
        builder.deleteCharAt(builder.length() - 1);
        builder.replace(0, 7, "Driver{");
        builder.append(", Months Active=");
        builder.append(monthsActive);
        builder.append("}");

        return builder.toString();
    }
}
