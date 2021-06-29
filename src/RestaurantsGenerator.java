import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

class RestaurantsGenerator{
    private static final String ADD_RESTAURANT_PROCEDURE = "exec p_add_new_restaurant ";
    private static final String ADD_NEW_EMPLOYEE = "exec p_add_new_employee ";
    private Random random = new Random();
    private DataGenerator dataGenerator;

    private String outputDir;

    public RestaurantsGenerator(String inputDir, String outputDir) {
        this.dataGenerator = new DataGenerator(inputDir);
        this.outputDir = outputDir;
    }

    public void generateRestaurants(int restaurants) {
        try {
            File dir = new File(outputDir);
            if(!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "restaurants.sql";
            PrintWriter out = new PrintWriter(path);


            while(restaurants-- > 0) {
                String address = dataGenerator.generateAddress();
                String city = dataGenerator.generateCity();
                String country = dataGenerator.generateCountry();
                String arguments = "\"" + address + "\"" + ", " + "\"" + city + "\"" + ", " + "\"" + country + "\"";
                out.println(ADD_RESTAURANT_PROCEDURE + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateEmployees(int restaurants, int employees) {
        try {
            File dir = new File(outputDir);
            if(!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "employees.sql";
            PrintWriter out = new PrintWriter(path);



            while(employees-- > 0) {
                int restaurantID = random.nextInt(restaurants) + 1;
                String arguments = ("\"" + dataGenerator.generateFirstName() + "\"") + ", " + ("\"" + dataGenerator.generateLastName() + "\"") + ", " + (restaurantID ) + ", " + ("\"" + dataGenerator.generateDate() + "\"");
                out.println(ADD_NEW_EMPLOYEE + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}