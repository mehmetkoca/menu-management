package sample.model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

//Contact --> Food
//ContactData --> FoodData

public class FoodData {

    private static final String FOODS_FILE = "foods.xml";

    private static final String FOOD = "food";
    private static final String FIRST_ITEM = "first_item";
    private static final String SECOND_ITEM = "second_item";
    private static final String THIRD_ITEM = "third_item";
    private static final String FOURTH_ITEM = "fourth_item";

    private ObservableList<Food> foods;

    public FoodData() {
        // *** initialize the foods list here ***
        foods = FXCollections.observableArrayList();
    }

    // *** methods to add/delete/access foods here ***
    public ObservableList<Food> getFoods(){
        return foods;
    }

    public void addFood(Food food) {
        foods.add(food);
    }

    public void deleteFood(Food food) {
        foods.remove(food);
    }

    public void loadFoods() {
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(FOODS_FILE);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            Food food = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have a food item, we create a new food
                    if (startElement.getName().getLocalPart().equals(FOOD)) {
                        food = new Food();
                        continue;
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(FIRST_ITEM)) {
                            event = eventReader.nextEvent();
                            food.setFirstItem(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(SECOND_ITEM)) {
                        event = eventReader.nextEvent();
                        food.setSecondItem(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(THIRD_ITEM)) {
                        event = eventReader.nextEvent();
                        food.setThirdItem(event.asCharacters().getData());
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(FOURTH_ITEM)) {
                        event = eventReader.nextEvent();
                        food.setFourthItem(event.asCharacters().getData());
                        continue;
                    }
                }

                // If we reach the end of a food element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(FOOD)) {
                        foods.add(food);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            //e.printStackTrace();
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void saveFoods() {

        try {
            // create an XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            // create XMLEventWriter
            XMLEventWriter eventWriter = outputFactory
                    .createXMLEventWriter(new FileOutputStream(FOODS_FILE));
            // create an EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            StartElement foodsStartElement = eventFactory.createStartElement("",
                    "", "foods");
            eventWriter.add(foodsStartElement);
            eventWriter.add(end);

            for (Food food: foods) {
                saveFood(eventWriter, eventFactory, food);
            }

            eventWriter.add(eventFactory.createEndElement("", "", "foods"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Problem with Foodss file: " + e.getMessage());
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            System.out.println("Problem writing food: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveFood(XMLEventWriter eventWriter, XMLEventFactory eventFactory, Food food)
            throws FileNotFoundException, XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");

        // create food open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", FOOD);
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        // Write the different nodes
        createNode(eventWriter, FIRST_ITEM, food.getFirstItem());
        createNode(eventWriter, SECOND_ITEM, food.getSecondItem());
        createNode(eventWriter, THIRD_ITEM, food.getThirdItem());
        createNode(eventWriter, FOURTH_ITEM, food.getFourthItem());

        eventWriter.add(eventFactory.createEndElement("", "", FOOD));
        eventWriter.add(end);
    }

    private void createNode(XMLEventWriter eventWriter, String name,
                            String value) throws XMLStreamException {

        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t");
        // create Start node
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        // create Content
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        // create End node
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }

}