package busManagement.repository.xmlRepository;

import busManagement.domain.BusCompany;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;

public class BusCompanyXMLRepository extends XMLRepository<Long, BusCompany>{

    public BusCompanyXMLRepository(Validator<BusCompany> validator, String filePath) {
        super(validator, filePath, "busCompanies");
    }

    @Override
    protected Element convertEntity(BusCompany entity) {
        Element busCompanyElement = rootDocument.createElement("busCompany");
        busCompanyElement.setAttribute("id", entity.getId().toString());
        this.addChildWithTextContent(busCompanyElement, "name", entity.getName());

        return busCompanyElement;
    }

    @Override
    protected BusCompany extractEntity(Element dataTransferObject) {
        Long id;

        try {
            id = Long.parseLong(dataTransferObject.getAttribute("id"));
        }
        catch (NumberFormatException nfe) {
            throw new BusManagementException(nfe.getMessage());
        }

        String name = dataTransferObject.getElementsByTagName("name").item(0).toString();

        BusCompany busCompany = new BusCompany(name);

        busCompany.setId(id);

        return busCompany;
    }
}
