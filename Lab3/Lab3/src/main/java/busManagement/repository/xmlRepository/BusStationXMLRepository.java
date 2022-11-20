package busManagement.repository.xmlRepository;

import busManagement.domain.Bus;
import busManagement.domain.BusStation;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;

public class BusStationXMLRepository extends XMLRepository<Long, BusStation> {
    public BusStationXMLRepository(Validator<BusStation> validator, String filePath) {
        super(validator, filePath, "busStations");
    }

    @Override
    protected Element convertEntity(BusStation entity) {
        Element busStationElement = rootDocument.createElement("busStation");
        busStationElement.setAttribute("id", entity.getId().toString());
        busStationElement.setAttribute("cityId", entity.getCityId().toString());
        this.addChildWithTextContent(busStationElement, "name", entity.getName());

        return busStationElement;
    }

    @Override
    protected BusStation extractEntity(Element dataTransferObject) {
        Long id, cityId;
        try{
            id = Long.parseLong(dataTransferObject.getAttribute("id"));
            cityId = Long.parseLong(dataTransferObject.getAttribute("cityId"));
        }
        catch (NumberFormatException nfe){
            throw new BusManagementException(nfe.getMessage());
        }

        String name = dataTransferObject.getElementsByTagName("name").item(0).getTextContent();

        BusStation busStation = new BusStation(cityId, name);
        busStation.setId(id);

        return busStation;
    }
}
