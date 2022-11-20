package core.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "busstation")
public class BusStation extends BaseEntity<Long>{

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "cityid")
    City city;

    @OneToMany(mappedBy = "station")
    Set<BusStop> stops;


    public BusStation(){

    }

    public BusStation(Long id, String name, City city){
        this.setId(id);
        this.name = name;
        this.city = city;
    }

    public City getCity(){return this.city;}

    public void setCity(City city){this.city = city;}

    public Long getCityId(){
        return this.city.getId();
    }

    public void setCityId(Long newCityId){
        this.city.setId(newCityId);
    }

    public String getName(){
        return this.name;
    }

    public void setName(String newName){
        this.name = newName;
    }

    @Override
    public boolean equals(Object obj){

        return obj instanceof BusStation
                && getName().equals(((BusStation) obj).getName())
                && getCityId().equals(((BusStation) obj).getCityId());
    }

    @Override
    public String toString(){
        return "BusStation{" +
                "cityId=" + city.getId() +
                ", name=" + name
                + "}" + super.toString();
    }

}