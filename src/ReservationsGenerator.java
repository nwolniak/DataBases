import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReservationsGenerator {
    private static final String ADD_NEW_INDIVIDUAL_RESERVATION = "exec p_add_individual_reservation_with_order ";
    /*
    private static final String ADD_NEW_INDIVIDUAL_RESERVATION = "exec p_add_individual_reservation ";
    private static final String ADD_NEW_ORDER = "exec p_add_new_order ";
    */
    private static final String ADD_NEW_CONCERN_RESERVATION = "exec p_add_concern_table_reservation ";
    private Random random = new Random();
    private DataGenerator dataGenerator;

    private String outputDir;

    public ReservationsGenerator(String inputDir, String outputDir) {
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

    public class StringVector2d {
        public final int i;
        public final String name;

        public StringVector2d(int i, String name) {
            this.i = i;
            this.name = name;
        }

        public String toString() {
            return ("(" + i + "," + name + ")");
        }
    }


    public void generateIndividualReservation(int numberOfIndividualReservations, int individualCustomers, int restaurants,
                                              int tables, int employess, int dishes, int orders) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "individualReservations.sql";
            PrintWriter out = new PrintWriter(path);
            int orderID = orders + 1;
            while (numberOfIndividualReservations-- > 0) {
                int customerID = random.nextInt(individualCustomers) + 1;
                int restaurantID = random.nextInt(restaurants) + 1;
                int tableID = random.nextInt(tables) + 1;
                String[] reservationDates = dataGenerator.generateTwoDates(random.nextInt(20) + 1);

                int numberOfDishes = random.nextInt(5) + 1;
                List<Vector2d> dishQuantityList = new ArrayList<>();
                List<Integer> dishIDList = new ArrayList<>();
                for (int i = 0; i < numberOfDishes; i++) {
                    Integer dishIDRandom = random.nextInt(dishes) + 1;
                    if (!dishIDList.contains(dishIDRandom)){
                        dishIDList.add(dishIDRandom);
                        dishQuantityList.add(new Vector2d(dishIDRandom, random.nextInt(3) + 1));
                    } else {
                        i -= 1;
                    }

                }

                int employeeID = random.nextInt(employess) + 1;

                /*
                String arguments2 = restaurantID + ", " + customerID + ", " + "\"" + reservationDates[0] + "\"" + ", NULL , " + employeeID + ", " + 0 + ", " + "@IndividualReservationDishID_Quantity_List_" + numberOfIndividualReservations;
                out.println("declare @IndividualReservationDishID_Quantity_List_" + numberOfIndividualReservations + " DishID_Quantity_List");
                out.println("insert into @IndividualReservationDishID_Quantity_List_" + numberOfIndividualReservations + " values " + dishQuantityList.toString().replace("[", "").replace("]", ""));
                out.println(ADD_NEW_ORDER + arguments2);
                */

                int paid = random.nextInt(2);
                int seats = random.nextInt(4) + 2;
                String arguments = customerID + ", " + restaurantID + ", " + tableID + ", " + "'" + reservationDates[0] + "'" + ", " + "'" + reservationDates[1] + "'" + ", " + seats + ", " + paid + ", " + employeeID + ", " + "@IndividualReservationDishID_Quantity_List_" + numberOfIndividualReservations;
                out.println("declare @IndividualReservationDishID_Quantity_List_" + numberOfIndividualReservations + " DishID_Quantity_List");
                out.println("insert into @IndividualReservationDishID_Quantity_List_" + numberOfIndividualReservations + " values " + dishQuantityList.toString().replace("[", "").replace("]", ""));
                out.println(ADD_NEW_INDIVIDUAL_RESERVATION + arguments);
                orderID += 1;
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateConcernReservations(int numberOfConcernReservations, int concernCustomers,int individualCustomers, int tables) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "concernReservations.sql";
            PrintWriter out = new PrintWriter(path);

            while (numberOfConcernReservations-- > 0) {
                int customerID = individualCustomers + random.nextInt(concernCustomers) + 1;
                int tableID = random.nextInt(tables) + 1;
                String[] reservationDates = dataGenerator.generateTwoDates(random.nextInt(20) + 1);


                int numberOfNames = random.nextInt(5) + 1;
                List<String> guestList = new ArrayList<>();
                for (int i = 1; i <= numberOfNames; i++) {
                    //guestList.add(new StringVector2d(i, "'" + dataGenerator.generateFirstName() + "'"));
                    guestList.add("('" + dataGenerator.generateFirstName() + "')");
                }


                out.println("declare @ConcernTablesReservationNamesList" + numberOfConcernReservations + " Guest_List");
                out.println("insert into @ConcernTablesReservationNamesList" + numberOfConcernReservations + " values " + guestList.toString().replace("[", "").replace("]", ""));
                String arguments = tableID + ", " + customerID + ", " + "\"" + reservationDates[0] + "\"" + ", " + "\"" + reservationDates[1] + "\"" + ", " + "@ConcernTablesReservationNamesList" + numberOfConcernReservations;

                out.println(ADD_NEW_CONCERN_RESERVATION + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}