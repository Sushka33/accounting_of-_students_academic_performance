package com.example.popitka;

import javax.xml.stream.XMLStreamException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;


public class UserData {

    private static final String CUSTOMERS_FILE = "customer.xml";
    private static final String CUSTOMER = "customer";
    private static final String SPISOK = "spisok";
    private static final String MATHH = "mathh";
    private static final String INFORM = "inform";
    private static final String BEL = "bel";

    private ObservableList<User> customers;


    public  UserData() {
        // *** initialize the contacts list here ***
        customers= FXCollections.observableArrayList();
        loadCustomers();

    }

    public ObservableList<User> getCustomers() {
        return customers;
    }
    public void addCustomer(User contact){
        customers.add(contact);

    }
    public void deleteCustomer(User CUSTOMER){
        customers.remove(CUSTOMER);

    }

    public void loadCustomers() {
        try {
            // First, create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            // Setup a new eventReader
            InputStream in = new FileInputStream(CUSTOMERS_FILE);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
            // read the XML document
            User contact = null;

            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();

                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    // If we have a contact item, we create a new contact
                    if (startElement.getName().getLocalPart().equals(CUSTOMER)) {
                        contact = new User();
                        continue;
                    }

                    if (event.isStartElement()) {
                        if (event.asStartElement().getName().getLocalPart()
                                .equals(SPISOK)) {
                            event = eventReader.nextEvent();
                            contact.setSpisok(event.asCharacters().getData());
                            continue;
                        }
                    }
                    if (event.asStartElement().getName().getLocalPart()
                            .equals(MATHH)) {
                        event = eventReader.nextEvent();
                        contact.setMathh(Integer.parseInt(event.asCharacters().getData()));
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(INFORM)) {
                        event = eventReader.nextEvent();
                        contact.setInform(Integer.parseInt(event.asCharacters().getData()));
                        continue;
                    }

                    if (event.asStartElement().getName().getLocalPart()
                            .equals(BEL)) {
                        event = eventReader.nextEvent();
                        contact.setBel(Integer.parseInt(event.asCharacters().getData()));
                        continue;
                    }
                }

                // If we reach the end of a contact element, we add it to the list
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(CUSTOMER)) {
                        customers.add(contact);
                    }
                }
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            e.printStackTrace();
        }

    }

    public void saveCustomers() {

        try {
            // create an XMLOutputFactory
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            // create XMLEventWriter
            XMLEventWriter eventWriter = outputFactory
                    .createXMLEventWriter(new FileOutputStream(CUSTOMERS_FILE));
            // create an EventFactory
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            // create and write Start Tag
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);

            StartElement contactsStartElement = eventFactory.createStartElement("",
                    "", "customers");
            eventWriter.add(contactsStartElement);
            eventWriter.add(end);

            for (User contact: customers) {
                saveCustomer(eventWriter, eventFactory, contact);
            }

            eventWriter.add(eventFactory.createEndElement("", "", "customers"));
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Problem with Contacts file: " + e.getMessage());
            e.printStackTrace();
        }
        catch (XMLStreamException e) {
            System.out.println("Problem writing contact: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveCustomer(XMLEventWriter eventWriter, XMLEventFactory eventFactory, User contact)
            throws FileNotFoundException, XMLStreamException {

        XMLEvent end = eventFactory.createDTD("\n");

        // create contact open tag
        StartElement configStartElement = eventFactory.createStartElement("",
                "", CUSTOMER);
        eventWriter.add(configStartElement);
        eventWriter.add(end);
        // Write the different nodes
        createNode(eventWriter, SPISOK, contact.getSpisok());
        createNode(eventWriter, MATHH, String.valueOf(contact.getMathh()));
        createNode(eventWriter, INFORM, String.valueOf(contact.getInform()));
        createNode(eventWriter, BEL, String.valueOf(contact.getBel()));

        eventWriter.add(eventFactory.createEndElement("", "", CUSTOMER));
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


