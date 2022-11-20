package busManagement.repository.xmlRepository;

import busManagement.domain.Luggage;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;

public class LuggageXMLRepository extends XMLRepository<Long, Luggage>{

    public LuggageXMLRepository(Validator<Luggage> validator, String filePath) {
        super(validator, filePath, "luggages");
    }

    @Override
    protected Element convertEntity(Luggage entity) {

        Element luggageElement = rootDocument.createElement("luggage");
        luggageElement.setAttribute("id", entity.getId().toString());
        luggageElement.setAttribute("passengerId", entity.getPassengerId().toString());

        this.addChildWithTextContent(luggageElement,"weight", entity.getWeight().toString());

        return luggageElement;
    }

    @Override
    protected Luggage extractEntity(Element dataTransferObject) {

        Long id, passengerId;

        try {
            id = Long.parseLong(dataTransferObject.getAttribute("id"));
            passengerId = Long.parseLong(dataTransferObject.getAttribute("passengerId"));
        }
        catch (NumberFormatException nfe) {

            throw new BusManagementException(nfe.getMessage());
        }

        Integer weight = Integer.parseInt(dataTransferObject.getElementsByTagName("weight").item(0).getTextContent());

        Luggage luggage = new Luggage(passengerId, weight);

        luggage.setId(id);

        return luggage;
    }
}
