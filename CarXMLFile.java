import java.util.*;
import java.io.*;
import javax.xml.stream.*;  // StAX API

public class CarXMLFile implements ProductDAO
{
    private String productsFilename = "cars.xml";
    private File productsFile = null;

    public CarXMLFile()
    {
        productsFile = new File(productsFilename);
    }

    private void checkFile() throws IOException
    {
        // if the file doesn't exist, create it
        if (!productsFile.exists())
            productsFile.createNewFile();
    }

    private boolean saveCar(ArrayList<Car> cars)
    {
        // create the XMLOutputFactory object
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();

        try
        {
            // check the file to make sure it exists
            this.checkFile();

            // create XMLStreamWriter object
            FileWriter fileWriter =
                    new FileWriter(productsFilename);
            XMLStreamWriter writer =
                    outputFactory.createXMLStreamWriter(fileWriter);

            //write the cars to the file
            writer.writeStartDocument("1.0");
            writer.writeStartElement("Cars");
            for (Car p : cars)
            {
                writer.writeStartElement("Car");
                writer.writeAttribute("Make", p.getMake());

                writer.writeStartElement("Model");
                writer.writeCharacters(p.getModel());
                writer.writeEndElement();

                writer.writeStartElement("Price");
                double price = p.getPrice();
                writer.writeCharacters(Double.toString(price));
                writer.writeEndElement();



                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.flush();
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (XMLStreamException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Car> getCars()
    {
        ArrayList<Car> cars = new ArrayList<Car>();
        Car p = null;

        // create the XMLInputFactory object
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try
        {
            // check the file to make sure it exists
            this.checkFile();

            // create a XMLStreamReader object
            FileReader fileReader =
                    new FileReader(productsFilename);
            XMLStreamReader reader =
                    inputFactory.createXMLStreamReader(fileReader);

            // read the cars from the file
            while (reader.hasNext())
            {
                int eventType = reader.getEventType();
                switch (eventType)
                {
                    case XMLStreamConstants.START_ELEMENT:
                        String elementName = reader.getLocalName();
                        if (elementName.equals("Car"))
                        {
                            p = new Car();

                            String make = reader.getAttributeValue(0);
                            p.setMake(make);
                        }
                        if (elementName.equals("Model"))
                        {
                            String model = reader.getElementText();

                            if (p != null) {
                                p.setModel(model);
                            }
                        }
                        if (elementName.equals("Price"))
                        {
                            String priceString = reader.getElementText();
                            double price = Double.parseDouble(priceString);

                            if (p != null) {
                                p.setPrice(price);
                            }
                        }

                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        elementName = reader.getLocalName();
                        if (elementName.equals("Car"))
                        {
                            cars.add(p);
                        }
                        break;
                    default:
                        break;
                }
                reader.next();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (XMLStreamException e)
        {
            e.printStackTrace();
            return null;
        }
        return cars;
    }

    public Car getCar(String make)
    {
        ArrayList<Car> cars = this.getCars();
        for (Car p : cars)
        {
            if (p.getMake().equals(make))
                return p;
        }
        return null;
    }

    public boolean addCar(Car p)
    {
        ArrayList<Car> cars = this.getCars();
        cars.add(p);
        return this.saveCar(cars);
    }

    public boolean deleteCar(Car p)
    {
        ArrayList<Car> cars = this.getCars();
        cars.remove(p);
        return this.saveCar(cars);
    }

    public boolean updateCar(Car newCar)
    {
        ArrayList<Car> cars = this.getCars();

        // get the old product and remove it
        Car oldCar = this.getCar(newCar.getMake());
        int i = cars.indexOf(oldCar);
        cars.remove(i);

        // add the updated product
        cars.add(i, newCar);

        return this.saveCar(cars);
    }
}

