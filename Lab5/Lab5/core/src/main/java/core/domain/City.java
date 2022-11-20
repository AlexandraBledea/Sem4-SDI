package core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "city")
public class City extends BaseEntity<Long>{

    @Column(name = "name")
    private String name;

    @Column(name = "population")
    private int population;

    @OneToMany(mappedBy = "city")
    Set<BusStation> stations;

    public City(){

    }

    public City(Long id, String name, int population){

        this.setId(id);
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

        return obj instanceof City
                && getName().equals(((City) obj).getName())
                && getPopulation() == ((City) obj).getPopulation();

    }

    @Override
    public String toString(){
        return "City{" +
                "name='" + name + '\'' +
                ", population=" + population
                +"}" + super.toString();
    }

}
