import java.text.NumberFormat;

public class Car
{
    private String make;
    private String model;
    private double price;
    private String notes;
    private int stock;
    private int sold;

    public Car()
    {
        this("", "", 0, "", 0, 0);
    }

    public Car(String make, String model, double price, String notes, int stock, int sold)
    {
        this.make = make;
        this.model = model;
        this.price = price;
        this.notes = notes;
        this.stock = stock;
        this.sold = sold;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    public int getStock()
    {
        return stock;
    }

    public void setSold(int sold)
    {
        this.sold = sold;
    }

    public int getSold()
    {
        return sold;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public String getMake(){
        return make;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getModel()
    {
        return model;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return price;
    }

    public String getFormattedPrice()
    {
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(price);
    }

    public boolean equals(Object object)
    {
        if (object instanceof Car)
        {
            Car car2 = (Car) object;
            if
                    (
                    make.equals(car2.getMake()) &&
                            model.equals(car2.getModel()) &&
                            price == car2.getPrice()
                    )
                return true;
        }
        return false;
    }

    public String toString()
    {
        return "Make:        " + make + "\n" +
                "Model: " + model + "\n" +
                "Price:       " + this.getFormattedPrice() + "\n";
    }
}