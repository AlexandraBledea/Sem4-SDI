package common.domain;


import java.util.Objects;

public class BusStation extends BaseEntity<Long>{

    private Long cityId;
    private String name;

    public BusStation(){

    }

    public BusStation(Long id, Long cityId, String name){
        this.setId(id);
        this.cityId = cityId;
        this.name = name;
    }

    public Long getCityId(){
        return this.cityId;
    }

    public void setCityId(Long newCityId){
        this.cityId = newCityId;
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
                "cityId=" + cityId +
                ", name=" + name
                + "}" + super.toString();
    }

}

