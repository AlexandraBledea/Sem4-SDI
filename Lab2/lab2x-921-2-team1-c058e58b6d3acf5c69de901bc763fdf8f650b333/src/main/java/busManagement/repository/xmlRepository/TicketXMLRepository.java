package busManagement.repository.xmlRepository;

import busManagement.domain.Bus;
import busManagement.domain.Ticket;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;

import java.time.LocalTime;

public class TicketXMLRepository extends XMLRepository<Long, Ticket>{

    public TicketXMLRepository(Validator<Ticket> validator, String filePath) {
        super(validator, filePath, "tickets");
    }

    @Override
    protected Element convertEntity(Ticket entity) {
        Element ticketElement = rootDocument.createElement("ticket");
        ticketElement.setAttribute("id", entity.getId().toString());
        ticketElement.setAttribute("passengerId", entity.getPassengerId().toString());
        ticketElement.setAttribute("busId", entity.getBusId().toString());
        this.addChildWithTextContent(ticketElement, "boardingTime", entity.getBoardingTime().toString());
        this.addChildWithTextContent(ticketElement, "price", entity.getPrice().toString());

        return ticketElement;
    }

    @Override
    protected Ticket extractEntity(Element dataTransferObject) {
        Long id, passengerId, busId, price;
        LocalTime boardingTime;

        try {
            id = Long.parseLong(dataTransferObject.getAttribute("id"));
            passengerId = Long.parseLong(dataTransferObject.getAttribute("passengerId"));
            busId = Long.parseLong(dataTransferObject.getAttribute("busId"));

            boardingTime = LocalTime.parse(dataTransferObject.getElementsByTagName("boardingTime").item(0).getTextContent());
            price = Long.parseLong(dataTransferObject.getElementsByTagName("price").item(0).getTextContent());
        }
        catch (NumberFormatException nfe) {
            throw new BusManagementException(nfe.getMessage());
        }

        Ticket ticket = new Ticket(passengerId, busId, boardingTime, price);

        ticket.setId(id);

        return ticket;
    }
}
