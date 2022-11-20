package busManagement.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class Person extends BaseEntity<Long> {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    public Person(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    public void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public void setDateOfBirth(LocalDate newDateOfBirth) {
        dateOfBirth = newDateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Person)) return false;

        return Objects.equals(((Person) o).getId(), this.getId()) &&
                Objects.equals(((Person) o).getFirstName(), firstName) &&
                Objects.equals(((Person) o).getLastName(), lastName) &&
                ((Person) o).getDateOfBirth().compareTo(dateOfBirth) == 0;
    }

    @Override
    public String toString() {
        String first = String.format("Person{" +
                "Id=%d, " +
                "First Name=%s, " +
                "Last Name=%s, Date of Birth=", getId(), firstName, lastName);
        String time = dateOfBirth.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
        return first + time + "}";
    }
}
