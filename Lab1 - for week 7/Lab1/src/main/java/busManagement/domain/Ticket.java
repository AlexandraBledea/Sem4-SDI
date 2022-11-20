package busManagement.domain;

public class Ticket extends BaseEntity<Long> {

    private Long passagerId;
    private Long busId;
    private Long boardingTime;
    private Long price;

    public Ticket() { }

    public Ticket(Long passagerId, Long busId, Long boardingTime, Long price) {
        this.passagerId = passagerId;
        this.busId = busId;
        this.boardingTime = boardingTime;
        this.price = price;
    }

    public void setPassengerId(Long passagerId) {
        this.passagerId = passagerId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public void setBoardingTime(Long boardingTime) {
        this.boardingTime = boardingTime;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getPassengerId() {
        return passagerId;
    }

    public Long getBusId() {
        return busId;
    }

    public Long getBoardingTime() {
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
        return passagerId == ticket.passagerId && busId == ticket.busId && boardingTime == ticket.boardingTime && price == ticket.price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "passagerId = " + this.passagerId + '\n' +
                "busId = " + this.busId + '\n' +
                "boardingTime = " + this.boardingTime + '\n' +
                "price = " + this.price + '\n' +
                '}' + super.toString();
    }
}
