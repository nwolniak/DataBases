public class Main {
    private final static String inputDirectory = "dane";
    private final static String outputDirectory = "wyjscie";

    //default values
    private final static int individualCustomers = 10;
    private final static int concernCustomers = 10;
    private final static int restaurants = 5;
    private final static int employees = 30;
    private final static int orders = 200;
    private final static int dishes = 10;
    private final static int tables = 30;
    private final static int maxSeats = 5;
    private final static int numberOfIndividualDiscounts = 3;
    private final static int numberOfConcernDiscounts = 3;
    private final static int numberOfIndividualReservations = 10;
    private final static int numberOfConcernReservations = 10;
    private final static int numberOfIngredients = 15;


    public static void main(String[] args) {
        CustomersGenerator customersGen = new CustomersGenerator(inputDirectory, outputDirectory);
        customersGen.generateIndividual(individualCustomers);
        customersGen.generateConcern(concernCustomers);

        RestaurantsGenerator restaurantsGenerator = new RestaurantsGenerator(inputDirectory, outputDirectory);
        restaurantsGenerator.generateRestaurants(restaurants);
        restaurantsGenerator.generateEmployees(restaurants, employees);

        DishGenerator dishGenerator = new DishGenerator(inputDirectory, outputDirectory);
        dishGenerator.generateDishesAndCategories();

        RestaurantIngredientsGenerator restaurantIngredientsGenerator = new RestaurantIngredientsGenerator(inputDirectory,outputDirectory);
        restaurantIngredientsGenerator.generateIngredients(restaurants,numberOfIngredients);


        OrdersGenerator ordersGenerator = new OrdersGenerator(inputDirectory, outputDirectory);
        ordersGenerator.generateIndividualOrders(restaurants, individualCustomers, employees, orders, dishes);
        ordersGenerator.generateConcernOrder(restaurants, concernCustomers,individualCustomers,  employees, orders, dishes);

        TablesGenerator tablesGenerator = new TablesGenerator(inputDirectory, outputDirectory);
        tablesGenerator.generateTables(tables, restaurants, maxSeats);
        tablesGenerator.generateTablesRestrictions(tables);

        DiscountGenerator discountGenerator = new DiscountGenerator(inputDirectory, outputDirectory);
        discountGenerator.generateIndividualDiscounts(numberOfIndividualDiscounts);
        discountGenerator.generateConcernDiscounts(numberOfConcernDiscounts);

        ReservationsGenerator reservationsGenerator = new ReservationsGenerator(inputDirectory, outputDirectory);
        reservationsGenerator.generateIndividualReservation(numberOfIndividualReservations, individualCustomers, restaurants, tables, employees, dishes, orders);
        reservationsGenerator.generateConcernReservations(numberOfConcernReservations, concernCustomers,individualCustomers, tables);

        MenuGenerator menuGenerator = new MenuGenerator(inputDirectory, outputDirectory);
        menuGenerator.addDishesToMenu(dishGenerator.getDishes());

        Concatenate concatenate = new Concatenate(inputDirectory, outputDirectory);
        concatenate.concatenate();

    }
}