import java.util.Scanner;
import java.util.ArrayList;

public class CarMainApp implements ProductConstants
{
    // declare two class variables
    private static ProductDAO productDAO;
    private static Scanner sc;

    public static void main(String args[])
    {
        // display a welcome message
        System.out.println("Welcome to the Car list application\n");

        // set the class variables
        productDAO = DAOFactory.getProductDAO();
        sc = new Scanner(System.in);

        // display the command menu
        displayMenu();

        // perform 1 or more actions
        String action = "";
        while (!action.equalsIgnoreCase("exit"))
        {
            // get the input from the user
            action = Validator.getString(sc,
                    "Enter a command: ");
            System.out.println();

            if (action.equalsIgnoreCase("list"))
                displayAllCars();
            else if (action.equalsIgnoreCase("add"))
                addCar();
            else if (action.equalsIgnoreCase("del") || action.equalsIgnoreCase("delete"))
                deleteCar();
            else if (action.equalsIgnoreCase("help") || action.equalsIgnoreCase("menu"))
                displayMenu();
            else if (action.equalsIgnoreCase("exit"))
                System.out.println("Bye.\n");
            else
                System.out.println("Error! Not a valid command.\n");
        }
    }

    public static void displayMenu()
    {
        System.out.println("COMMAND MENU");
        System.out.println("list    - List all products");
        System.out.println("add     - Add a product");
        System.out.println("del     - Delete a product");
        System.out.println("help    - Show this menu");
        System.out.println("exit    - Exit this application\n");
    }

    public static void displayAllCars()
    {
        System.out.println("CAR LIST");

        ArrayList<Car> cars = productDAO.getCars();
        if (cars == null)
        {
            System.out.println("Error! Unable to get cars.\n");
        }
        else
        {
            Car p = null;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < cars.size(); i++)
            {
                p = cars.get(i);
                sb.append
                        (
                                StringUtils.padWithSpaces(
                                        p.getMake(), CODE_SIZE + 4) +
                                        StringUtils.padWithSpaces(
                                                p.getModel(), DESCRIPTION_SIZE + 4) +
                                        p.getFormattedPrice() + "\n"
                        );
            }
            System.out.println(sb.toString());
        }
    }

    public static void addCar()
    {
        String make = Validator.getString(
                sc, "Enter car make: ");
        String model = Validator.getLine(
                sc, "Enter car model: ");
        double price = Validator.getDouble(
                sc, "Enter car price: ");

        Car car = new Car();
        car.setMake(make);
        car.setModel(model);
        car.setPrice(price);
        boolean success = productDAO.addCar(car);

        System.out.println();
        if (success)
            System.out.println(model
                    + " was added to the database.\n");
        else
            System.out.println("Error! Unable to add car\n");
    }

    public static void deleteCar()
    {
        String make = Validator.getString(sc,
                "Enter car maker name to delete: ");

        Car p = productDAO.getCar(make);

        System.out.println();
        if (p != null)
        {
            boolean success = productDAO.deleteCar(p);
            if (success)
                System.out.println(p.getModel()
                        + " was deleted from the database.\n");
            else
                System.out.println("Error! Unable to add car\n");
        }
        else
        {
            System.out.println("No car matches that make.\n");
        }
    }
}