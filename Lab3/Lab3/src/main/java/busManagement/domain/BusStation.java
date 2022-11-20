package busManagement.domain;

import java.util.Objects;

public class BusStation extends BaseEntity<Long>{

    private Long cityId;
    private String name;

    public BusStation(){

    }

    public BusStation(Long cityId, String name){
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
        if (this ==  obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        BusStation busStation = (BusStation) obj;
        if(!Objects.equals(name, busStation.getName())) return false;
        return Objects.equals(cityId, busStation.cityId);

    }

    @Override
    public String toString(){
        return "BusStation{" +
                "cityId=" + cityId +
                ", name=" + name
                + "}" + super.toString();
    }

}
