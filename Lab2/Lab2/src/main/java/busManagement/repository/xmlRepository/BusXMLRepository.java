package busManagement.repository.xmlRepository;

import busManagement.domain.Bus;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;

public class BusXMLRepository extends XMLRepository<Long, Bus> {

    public BusXMLRepository(Validator<Bus> validator, String filePath) {
        super(validator, filePath, "buses");
    }

    @Override
    protected Element convertEntity(Bus entity) {

        Element busElement = rootDocument.createElement("bus");
        busElement.setAttribute("id", entity.getId().toString());
        busElement.setAttribute("companyId", entity.getCompanyId().toString());
        busElement.setAttribute("driverId", entity.getDriverId().toString());
        this.addChildWithTextContent(busElement, "modelName", entity.getModelName());

        return busElement;
    }

    @Override
    protected Bus extractEntity(Element dataTransferObject) {

        Long id, companyId, driverId;

        try {
            id = Long.parseLong(dataTransferObject.getAttribute("id"));
            companyId = Long.parseLong(dataTransferObject.getAttribute("companyId"));
            driverId = Long.parseLong(dataTransferObject.getAttribute("driverId"));
        }
        catch (NumberFormatException nfe) {

            throw new BusManagementException(nfe.getMessage());
        }

        String modelName = dataTransferObject.getElementsByTagName("modelName").item(0).getTextContent();

        Bus bus = new Bus(companyId, driverId, modelName);

        bus.setId(id);

        return bus;
    }


}
