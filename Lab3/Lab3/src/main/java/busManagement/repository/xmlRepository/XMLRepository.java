package busManagement.repository.xmlRepository;

import busManagement.domain.BaseEntity;
import busManagement.domain.Luggage;
import busManagement.domain.exceptions.BusManagementException;
import busManagement.domain.validators.Validator;
import busManagement.repository.PersistentRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public abstract class XMLRepository<ID, E extends BaseEntity<ID>> extends PersistentRepository<ID, E, Element> {
    protected final String documentRootName;
    protected final String filePath;
    protected Document rootDocument;

    public XMLRepository(Validator<E> validator, String filePath, String documentRootName) {
        super(validator);
        this.filePath = filePath;
        this.documentRootName = documentRootName;
        loadData();
    }

    /**
     *  Loads the data from the XML file
     */
    @Override
    protected void loadData() {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            rootDocument = documentBuilder.parse(filePath);
            NodeList nodeList = rootDocument.getDocumentElement().getChildNodes();
            List<E> entities = new ArrayList<>();
            IntStream
                    .range(0, nodeList.getLength())
                    .filter((i) -> nodeList.item(i) instanceof Element)
                    .forEach((i) -> entities.add(extractEntity((Element) nodeList.item(i))));
            loadEntities(entities);
        } catch (ParserConfigurationException e) {
            throw new BusManagementException("Parser Configuration Exception: " + e.getMessage());
        } catch (SAXException e) {
            throw new BusManagementException("SAXException: " + e.getMessage());
        } catch (IOException ioException) {
            throw new BusManagementException("IOException: " + ioException.getMessage());
        }
    }

    /**
     *  Saves data to the XML File
     */
    @Override
    protected void saveData() {
        try {
            this.rootDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            this.rootDocument.appendChild(this.rootDocument.createElement(this.documentRootName));
        } catch (ParserConfigurationException e) {
            throw new BusManagementException("Parser Configuration Exception: " + e.getMessage());
        }

        findAll().forEach(entity -> {
            Element element = convertEntity(entity);
            rootDocument.getDocumentElement().appendChild(element);
        });

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(
                    new DOMSource(rootDocument),
                    new StreamResult(new File(filePath))
            );
        } catch (TransformerConfigurationException e) {
            throw new BusManagementException("Transformer Configuration Exception: " + e.getMessage());
        } catch (TransformerException e) {
            throw new BusManagementException("Transformer Exception: " + e.getMessage());
        }
    }

    @Override
    protected void saveNode(E entity) {

        try {
            this.rootDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);

            String entityName = entity.getClass().getName().split("\\.")[2];

            NodeList list = rootDocument.getElementsByTagName(
                    Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1)
            );

            boolean duplicate = false;

            for (int i = 0; i < list.getLength(); i++) {

                String idTag = list.item(i).getAttributes().getNamedItem("id").getNodeValue();
                if (idTag.equals(String.valueOf(entity.getId()))) {
                    duplicate = true;
                }
            }

            if (!duplicate) {

                Element element = convertEntity(entity);
                rootDocument.getDocumentElement().appendChild(element);
            }


        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new BusManagementException("ParserConfigurationException: " + e.getMessage());
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(rootDocument), new StreamResult(new File(filePath)));
        } catch (TransformerConfigurationException tce) {
            throw new BusManagementException("TransformerConfigurationException: " + tce.getMessage());
        } catch (TransformerException te) {
            throw new BusManagementException("TransformerException: " + te.getMessage());
        }
    }

    protected void deleteNode(Optional<E> optionalE) {
        try {
            this.rootDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(filePath);

            String entityName = optionalE.get().getClass().getName().split("\\.")[2];

            NodeList list = rootDocument.getElementsByTagName(
                    Character.toLowerCase(entityName.charAt(0)) + entityName.substring(1)
            );

            for (int i = 0; i < list.getLength(); i++) {

                Element element = (Element) list.item(i);
                String idTag = list.item(i).getAttributes().getNamedItem("id").getNodeValue();
                if (idTag.equals(String.valueOf(optionalE.get().getId()))) {
                    element.getParentNode().removeChild(element);
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new BusManagementException("ParserConfigurationException: " + e.getMessage());
        }

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.transform(new DOMSource(rootDocument), new StreamResult(new File(filePath)));
        } catch (TransformerConfigurationException tce) {
            throw new BusManagementException("TransformerConfigurationException: " + tce.getMessage());
        } catch (TransformerException te) {
            throw new BusManagementException("TransformerException: " + te.getMessage());
        }
    }

    /**
     *  Adds text content to element
     * @param parent - parent element
     * @param tagName - name of tag
     * @param textContent - content to be added
     */
    protected void addChildWithTextContent(Element parent, String tagName, String textContent) {
        Element childElement = rootDocument.createElement(tagName);
        childElement.setTextContent(textContent);
        parent.appendChild(childElement);
    }
}