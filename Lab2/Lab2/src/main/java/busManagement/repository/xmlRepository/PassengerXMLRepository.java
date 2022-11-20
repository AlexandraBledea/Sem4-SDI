package busManagement.repository.xmlRepository;

import busManagement.domain.Passenger;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PassengerXMLRepository extends XMLRepository<Long, Passenger> {

    public PassengerXMLRepository(Validator<Passenger> validator, String filePath) {
        super(validator, filePath, "passengers");
    }

    @Override
    protected Element convertEntity(Passenger entity) {
        Element passengerElement = rootDocument.createElement("passenger");
        passengerElement.setAttribute("id", entity.getId().toString());
        this.addChildWithTextContent(passengerElement, "firstName", entity.getFirstName());
        this.addChildWithTextContent(passengerElement, "lastName", entity.getLastName());
        this.addChildWithTextContent(passengerElement, "dateOfBirth", entity.getDateOfBirth().toString());
        this.addChildWithTextContent(passengerElement, "timesTravelled", entity.getTimesTravelled().toString());
        return passengerElement;
    }

    @Override
    protected Passenger extractEntity(Element dataTransferObject) {
        Long id;
        Integer timesTravelled;

        try{
            id = Long.parseLong(dataTransferObject.getAttribute("id"));
            timesTravelled = Integer.parseInt(dataTransferObject.getElementsByTagName("timesTravelled")
                    .item(0).getTextContent());
        }
        catch (NumberFormatException nfe) {
            throw new BusManagementException(nfe.getMessage());
        }

        String firstName = dataTransferObject.getElementsByTagName("firstName").item(0).getTextContent();
        String lastName = dataTransferObject.getElementsByTagName("lastName").item(0).getTextContent();
        LocalDate dateOfBirth;

        try {
            dateOfBirth = LocalDate.parse(dataTransferObject.getElementsByTagName("dateOfBirth").item(0).getTextContent());
        }
        catch(DateTimeParseException dtpe){
            throw new BusManagementException(dtpe.getMessage());
        }

        Passenger passenger = new Passenger(firstName, lastName, dateOfBirth, timesTravelled);
        passenger.setId(id);

        return passenger;
    }
}
