package busManagement.repository.xmlRepository;

import busManagement.domain.City;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;

public class CityXMLRepository extends XMLRepository<Long, City>{

    public CityXMLRepository(Validator<City> validator, String filePath) {
        super(validator, filePath, "cities");
    }

    @Override
    protected Element convertEntity(City entity) {
        Element cityElement = rootDocument.createElement("city");
        cityElement.setAttribute("id", entity.getId().toString());
        this.addChildWithTextContent(cityElement, "name", entity.getName());
        this.addChildWithTextContent(cityElement, "population", String.valueOf(entity.getPopulation()));

        return cityElement;
    }

    @Override
    protected City extractEntity(Element dataTransferObject) {

        Long id;
        try{
            id = Long.parseLong(dataTransferObject.getAttribute("id"));
        }
        catch (NumberFormatException nfe)
        {
            throw new BusManagementException(nfe.getMessage());
        }

        String name = dataTransferObject.getElementsByTagName("name").item(0).getTextContent();
        int population = Integer.parseInt(dataTransferObject.getElementsByTagName("population").item(0).getTextContent());
        System.out.println(population);
        City city = new City(name, population);
        city.setId(id);

        return city;
    }
}
