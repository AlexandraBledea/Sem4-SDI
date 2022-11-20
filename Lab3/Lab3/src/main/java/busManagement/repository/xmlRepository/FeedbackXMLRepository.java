package busManagement.repository.xmlRepository;

import busManagement.domain.Bus;
import busManagement.domain.Feedback;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import org.w3c.dom.Element;
import busManagement.utils.Pair;

public class FeedbackXMLRepository extends  XMLRepository<Pair<Long,Long>, Feedback>{

    public FeedbackXMLRepository(Validator<Feedback> validator, String filePath){
        super(validator,filePath,"feedbacks");
    }

    @Override
    protected Element convertEntity(Feedback entity) {

        Element feedbackElement = rootDocument.createElement("feedback");
        feedbackElement.setAttribute("id",entity.getId().toString());
        this.addChildWithTextContent(feedbackElement,"review", entity.getReview());

        return feedbackElement;
    }

    @Override
    protected Feedback extractEntity(Element dataTransferObject) {

        Pair<Long,Long> id;

        String idAux = dataTransferObject.getAttribute("id");
        idAux = idAux.replaceAll("[()]","");
        String [] splitted = idAux.split(",");

        try {
            id = new Pair<>(Long.parseLong(splitted[0]),Long.parseLong(splitted[1]));
        }
        catch (NumberFormatException nfe) {

            throw new BusManagementException(nfe.getMessage());
        }

        String review = dataTransferObject.getElementsByTagName("review").item(0).getTextContent();

        Feedback feedback = new Feedback(review);
        feedback.setId(id);

        return feedback;
    }
}
