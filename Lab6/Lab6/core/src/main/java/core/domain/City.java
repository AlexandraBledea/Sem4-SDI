package core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "city")
public class City extends BaseEntity<Long>{

    @Column(name = "name")
    private String name;

    @Column(name = "population")
    private int population;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    Set<BusStation> stations;

    public void deleteStation(BusStation station){
        this.stations.remove(station);
        station.setCity(null);
    }

    public void addStation(BusStation station, City city){
        this.stations.add(station);
        station.setCity(city);
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
