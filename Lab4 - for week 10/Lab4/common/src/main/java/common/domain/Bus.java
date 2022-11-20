package common.domain;


public class Bus extends BaseEntity<Long> {

    private String modelName;
    private String fuel;
    private int capacity;

    public Bus(Long id, String modelName, String fuel, int capacity) {

        this.setId(id);
        this.modelName = modelName;
        this.fuel = fuel;
        this.capacity = capacity;
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
