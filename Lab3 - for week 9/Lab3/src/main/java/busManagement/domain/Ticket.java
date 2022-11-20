package busManagement.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Ticket extends BaseEntity<Long> {

    private Long passengerId;
    private Long busId;
    private LocalTime boardingTime;
    private Long price;

    public Ticket() { }

    public Ticket(Long passengerId, Long busId, LocalTime boardingTime, Long price) {
        this.passengerId = passengerId;
        this.busId = busId;
        this.boardingTime = boardingTime;
        this.price = price;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public void setBoardingTime(LocalTime boardingTime) {
        this.boardingTime = boardingTime;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public Long getBusId() {
        return busId;
    }

    public LocalTime getBoardingTime() {
        return boardingTime;
    }

    public Long getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(passengerId, ticket.passengerId) &&
                Objects.equals(busId, ticket.busId) &&
                boardingTime == ticket.boardingTime &&
                Objects.equals(price, ticket.price);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "passengerId = " + this.passengerId + '\n' +
                "busId = " + this.busId + '\n' +
                "boardingTime = " + this.boardingTime + '\n' +
                "price = " + this.price + '\n' +
                '}' + super.toString();
    }
}
