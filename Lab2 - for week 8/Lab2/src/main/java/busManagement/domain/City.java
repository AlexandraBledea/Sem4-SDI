package busManagement.domain;

import java.util.Objects;

public class City extends BaseEntity<Long>{

    private String name;
    private int population;

    /**
     * Constructor with no params
     */
    public City(){

    }

    public City(String name, int population){
        this.name = name;
        this.population = population;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    public int getPopulation(){
        return this.population;
    }

    public void setPopulation(int newPopulation){
        this.population = newPopulation;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        City city = (City) obj;

        if(!Objects.equals(name, city.name)) return false;
        return population == city.population;
    }

    @Override
    public String toString(){
        return "City{" +
                "name='" + name + '\'' +
                ", population=" + population
                +"}" + super.toString();
    }

}
