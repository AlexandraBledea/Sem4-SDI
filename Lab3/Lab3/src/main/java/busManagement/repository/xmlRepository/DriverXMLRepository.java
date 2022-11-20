package busManagement.repository.xmlRepository;

import busManagement.domain.Driver;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DriverXMLRepository extends XMLRepository<Long, Driver>{

    public DriverXMLRepository(Validator<Driver> validator, String filepath){
        super(validator, filepath, "drivers");
    }

    @Override
    protected Element convertEntity(Driver entity) {
        Element driverElement = rootDocument.createElement("driver");
        driverElement.setAttribute("id", entity.getId().toString());
        this.addChildWithTextContent(driverElement, "firstName", entity.getFirstName());
        this.addChildWithTextContent(driverElement, "lastName", entity.getLastName());
        this.addChildWithTextContent(driverElement, "dateOfBirth", entity.getDateOfBirth().toString());
        this.addChildWithTextContent(driverElement, "monthsActive", entity.getMonthsActive().toString());
        return driverElement;
    }

    @Override
    protected Driver extractEntity(Element dataTransferObject) {
        Long id;
        Integer monthsActive;

        try{
            id = Long.parseLong(dataTransferObject.getAttribute("id"));
            monthsActive = Integer.parseInt(dataTransferObject.getElementsByTagName("monthsActive").item(0).getTextContent());
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

        Driver driver = new Driver(firstName, lastName, dateOfBirth, monthsActive);
        driver.setId(id);

        return driver;
    }
}
