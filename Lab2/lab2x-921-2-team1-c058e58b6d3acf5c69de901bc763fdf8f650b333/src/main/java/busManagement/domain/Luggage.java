package busManagement.domain;

import java.util.Objects;

public class Luggage extends BaseEntity<Long>{

    private Long passengerId;
    private Integer weight;

    public Luggage() {
    }

    public Luggage(Long passengerId, int weight) {
        this.passengerId = passengerId;
        this.weight = weight;
    }

    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass() ) return false;

        Luggage luggage = (Luggage) obj;
        if(!Objects.equals(weight,luggage.getWeight()))return false;
        return Objects.equals(passengerId,luggage.getPassengerId());
    }

    @Override
    public String toString() {
        return "Luggage{" +
                "passengerId=" + passengerId +
                ", weight=" + weight +
                '}' + super.toString();
    }
}
