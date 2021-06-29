import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrdersGenerator {
    private static final String ADD_NEW_ORDER = "exec p_add_new_order ";
    private Random random = new Random();
    private DataGenerator dataGenerator;

    private String outputDir;

    public OrdersGenerator(String inputDir, String outputDir) {
        this.dataGenerator = new DataGenerator(inputDir);
        this.outputDir = outputDir;
    }

    public class Vector2d {
        public final int x;
        public final int y;

        public Vector2d(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return ("(" + x + "," + y + ")");
        }
    }

    public void generateIndividualOrders(int restaurants, int individualCustomers, int employess, int orders, int dishes) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "individualOrders.sql";
            PrintWriter out = new PrintWriter(path);

            int ordersHalf = (int)orders/2;

            while (orders-- > ordersHalf) {
                int restaurantID = random.nextInt(restaurants) + 1;
                int customerID = random.nextInt(individualCustomers) + 1;
                int employeeID = random.nextInt(employess) + 1;
                //String orderDate = dataGenerator.generateDate();

                String[] orderDates = dataGenerator.generateTwoDates(1);
                int numberOfDishes = random.nextInt(5) + 1;
                List<Vector2d> dishQuantityList = new ArrayList<>();
                List<Integer> randomDishIDList = new ArrayList<>();
                for (int i = 0; i < numberOfDishes; i++) {
                    Integer randomDishID = random.nextInt(dishes) + 1;
                    if (!randomDishIDList.contains(randomDishID)){
                        dishQuantityList.add(new Vector2d(randomDishID, random.nextInt(3) + 1));
                        randomDishIDList.add(randomDishID);
                    } else {
                        i -= 1;
                    }
                }

                String arguments = restaurantID + ", " + customerID + ", " + "'" + orderDates[0] + "'" +", " + "'" + orderDates[1] + "'"+ ", " + employeeID + ", " + 0 + ", " + "@IndividualDishID_Quantity_List_" + orders;
                out.println("declare @IndividualDishID_Quantity_List_" + orders + " DishID_Quantity_List");
                out.println("insert into @IndividualDishID_Quantity_List_" + orders + " values " + dishQuantityList.toString().replace("[", "").replace("]", ""));
                out.println(ADD_NEW_ORDER + arguments);
            }
            while (ordersHalf-- > 0) {
                int restaurantID = random.nextInt(restaurants) + 1;
                int customerID = random.nextInt(individualCustomers) + 1;
                int employeeID = random.nextInt(employess) + 1;
                //String orderDate = dataGenerator.generateDate();

                String[] orderDates = dataGenerator.generateTwoDates(1);
                int numberOfDishes = random.nextInt(5) + 1;
                List<Vector2d> dishQuantityList = new ArrayList<>();
                List<Integer> randomDishIDList = new ArrayList<>();
                for (int i = 0; i < numberOfDishes; i++) {
                    Integer randomDishID = random.nextInt(dishes) + 1;
                    if (!randomDishIDList.contains(randomDishID)){
                        dishQuantityList.add(new Vector2d(randomDishID, random.nextInt(3) + 1));
                        randomDishIDList.add(randomDishID);
                    } else {
                        i -= 1;
                    }
                }

                String arguments = restaurantID + ", " + customerID + ", " + "'" + orderDates[0] +"'" + ", NULL , " + employeeID + ", " + 0 + ", " + "@IndividualDishID_Quantity_List_" + ordersHalf;
                out.println("declare @IndividualDishID_Quantity_List_" + ordersHalf + " DishID_Quantity_List");
                out.println("insert into @IndividualDishID_Quantity_List_" + ordersHalf + " values " + dishQuantityList.toString().replace("[", "").replace("]", ""));
                out.println(ADD_NEW_ORDER + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateConcernOrder(int restaurants, int concernCustomers,int individualCustomers, int employess, int orders, int dishes) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "concernOrders.sql";
            PrintWriter out = new PrintWriter(path);

            int ordersHalf = (int) orders/2;
            while (orders-- > ordersHalf) {
                int restaurantID = random.nextInt(restaurants) + 1;
                int customerID = individualCustomers + random.nextInt(concernCustomers) + 1;
                int employeeID = random.nextInt(employess) + 1;
                String orderDate = dataGenerator.generateDate();
                String[] orderDates = dataGenerator.generateTwoDates(1);
                int numberOfDishes = random.nextInt(5) + 1;
                List<Vector2d> dishQuantityList = new ArrayList<>();
                List<Integer> randomDishIDList = new ArrayList<>();
                for (int i = 0; i < numberOfDishes; i++) {
                    Integer randomDishID = random.nextInt(dishes) + 1;
                    if (!randomDishIDList.contains(randomDishID)){
                        dishQuantityList.add(new Vector2d(randomDishID, random.nextInt(3) + 1));
                        randomDishIDList.add(randomDishID);
                    } else {
                        i -= 1;
                    }
                }

                String arguments = restaurantID + ", " + customerID + ", " + "'" + orderDates[0] +"'" + ", " + "'" + orderDates[1] + "'" + ", " + employeeID + ", " + 1 + ", " + "@ConcernDishID_Quantity_List_" + orders;
                out.println("declare @ConcernDishID_Quantity_List_" + orders + " DishID_Quantity_List");
                out.println("insert into @ConcernDishID_Quantity_List_" + orders + " values " + dishQuantityList.toString().replace("[", "").replace("]", ""));
                out.println(ADD_NEW_ORDER + arguments);
            }
            while (ordersHalf-- > 0) {
                int restaurantID = random.nextInt(restaurants) + 1;
                int customerID = individualCustomers + random.nextInt(concernCustomers) + 1;
                int employeeID = random.nextInt(employess) + 1;
                String orderDate = dataGenerator.generateDate();
                String[] orderDates = dataGenerator.generateTwoDates(1);
                int numberOfDishes = random.nextInt(5) + 1;
                List<Vector2d> dishQuantityList = new ArrayList<>();
                List<Integer> randomDishIDList = new ArrayList<>();
                for (int i = 0; i < numberOfDishes; i++) {
                    Integer randomDishID = random.nextInt(dishes) + 1;
                    if (!randomDishIDList.contains(randomDishID)){
                        dishQuantityList.add(new Vector2d(randomDishID, random.nextInt(3) + 1));
                        randomDishIDList.add(randomDishID);
                    } else {
                        i -= 1;
                    }
                }

                String arguments = restaurantID + ", " + customerID + ", " + "'" + orderDates[0] + "'" +  ", " +  "NULL" + ", " + employeeID + ", " + 1 + ", " + "@ConcernDishID_Quantity_List_" + ordersHalf;
                out.println("declare @ConcernDishID_Quantity_List_" + ordersHalf + " DishID_Quantity_List");
                out.println("insert into @ConcernDishID_Quantity_List_" + ordersHalf + " values " + dishQuantityList.toString().replace("[", "").replace("]", ""));
                out.println(ADD_NEW_ORDER + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}