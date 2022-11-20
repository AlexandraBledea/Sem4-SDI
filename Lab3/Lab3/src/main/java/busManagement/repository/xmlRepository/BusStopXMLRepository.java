package busManagement.repository.xmlRepository;

import busManagement.domain.Bus;
import busManagement.domain.BusStop;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import busManagement.utils.Pair;
import org.w3c.dom.Element;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class BusStopXMLRepository  extends XMLRepository<Pair<Long, Long>, BusStop> {
    public BusStopXMLRepository(Validator<BusStop> validator, String filePath) {
        super(validator, filePath, "busStops");
    }

    @Override
    protected Element convertEntity(BusStop entity) {

        Element busElement = rootDocument.createElement("busStop");
        busElement.setAttribute("id", entity.getId().toString());

        this.addChildWithTextContent(busElement, "stopTime",  entity.getStopTime().toString());

        return busElement;
    }

    @Override
    protected BusStop extractEntity(Element dataTransferObject) {

        AtomicReference<Pair<Long, Long>> id = new AtomicReference<>();
        LocalTime stopTime;

        try {
            Arrays.stream(dataTransferObject.getAttribute("id").split(",", 2)).reduce((a,b) -> {
                id.set(new Pair<Long, Long>(Long.parseLong(a.replaceAll("[(]", "")),
                        Long.parseLong(b.replaceAll("[)]", ""))));

                return b;
            });


            stopTime = LocalTime.parse(dataTransferObject.getElementsByTagName("stopTime").item(0).getTextContent());
        }
        catch (NumberFormatException nfe) {

            throw new BusManagementException(nfe.getMessage());
        }


        BusStop busStop = new BusStop(stopTime);

        busStop.setId(id.get());

        return busStop;
    }
}
