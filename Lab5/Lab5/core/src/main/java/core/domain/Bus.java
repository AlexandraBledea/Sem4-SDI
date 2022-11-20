package core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "bus")
public class Bus extends BaseEntity<Long> {


    @Column(name = "modelname")
    private String modelName;

    @Column(name = "fuel")
    private String fuel;

    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "bus")
    Set<BusStop> stops;

    public Bus(Long id, String modelName, String fuel, int capacity) {
        this.id = id;
        this.setId(id);
        this.modelName = modelName;
        this.fuel = fuel;
        this.capacity = capacity;
    }

    public Bus() {

    }

    public String getModelName() {
        return modelName;
    }

    public String getFuel(){return fuel;}

    public int getCapacity(){return capacity;}

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setFuel(String fuel){this.fuel = fuel;}

    public void setCapacity(int capacity){this.capacity = capacity;}

    @Override
    public boolean equals(Object o) {

        return o instanceof Bus &&
                getModelName().equals(((Bus) o).getModelName())
                && getFuel().equals(((Bus) o).getFuel())
                && getCapacity() == ((Bus) o).getCapacity();
    }

    @Override
    public String toString() {
        return "Bus{" +
                "modelName=" + modelName +
                ", fuel=" + fuel +
                ", capacity='" + capacity + '\'' +
                '}' + super.toString();
    }
}