import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Concatenate {
    private String inputDir;
    private String outputDir;


    private ArrayList<String> categories;
    private ArrayList<String> concernCustomers;
    private ArrayList<String> concernDiscounts;
    private ArrayList<String> concernOrders;
    private ArrayList<String> concernReservations;
    private ArrayList<String> dishes;
    private ArrayList<String> employees;
    private ArrayList<String> individualCustomers;
    private ArrayList<String> individualDiscounts;
    private ArrayList<String> individualOrders;
    private ArrayList<String> individualReservations;
    private ArrayList<String> ingredients;
    private ArrayList<String> restaurants;
    private ArrayList<String> tables;
    private ArrayList<String> tablesRestrictions;
    private ArrayList<String> menu;
    private ArrayList<String> ingredientsToRestaurant;

    private final String[] files = new String[]{ "individualCustomers.sql", "concernCustomers.sql", "individualDiscounts.sql" , "concernDiscounts.sql", "restaurants.sql",
            "employees.sql", "tables.sql", "tablesRestrictions.sql" , "categories.sql", "ingredients.sql", "dishes.sql","menu.sql","ingredients_to_restaurant.sql" , "individualOrders.sql",
            "concernOrders.sql", "individualReservations.sql", "concernReservations.sql" };


    private List<ArrayList<String>> readedFiles = new ArrayList<>();

    public Concatenate(String inputDir, String outputDir) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        read();
    }

    private void read() {
        individualCustomers = readFile(files[0]);
        this.readedFiles.add(individualCustomers);

        concernCustomers = readFile(files[1]);
        this.readedFiles.add(concernCustomers);

        individualDiscounts = readFile(files[2]);
        this.readedFiles.add(individualDiscounts);

        concernDiscounts = readFile(files[3]);
        this.readedFiles.add(concernDiscounts);

        restaurants = readFile(files[4]);
        this.readedFiles.add(restaurants);

        employees = readFile(files[5]);
        this.readedFiles.add(employees);

        tables = readFile(files[6]);
        this.readedFiles.add(tables);

        tablesRestrictions = readFile(files[7]);
        this.readedFiles.add(tablesRestrictions);

        categories = readFile(files[8]);
        this.readedFiles.add(categories);

        ingredients = readFile(files[9]);
        this.readedFiles.add(ingredients);

        dishes = readFile(files[10]);
        this.readedFiles.add(dishes);

        ingredientsToRestaurant = readFile(files[11]);
        this.readedFiles.add(ingredientsToRestaurant);

        menu = readFile(files[12]);
        this.readedFiles.add(menu);

        individualOrders = readFile(files[13]);
        this.readedFiles.add(individualOrders);

        concernOrders = readFile(files[14]);
        this.readedFiles.add(concernOrders);

        individualReservations = readFile(files[15]);
        this.readedFiles.add(individualReservations);

        concernReservations = readFile(files[16]);
        this.readedFiles.add(concernReservations);

    }

    private ArrayList<String> readFile(String fileName) {
        String path = outputDir + File.separator + fileName;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

            String line;
            ArrayList<String> list = new ArrayList<>();
            while((line = in.readLine()) != null) {
                list.add(line);
            }
            return list;
        } catch (FileNotFoundException e) {
            System.err.println("Nie odnaleziono pliku: " + path);
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            System.err.println("Problem podczas czytania pliku: " + path);
            e.printStackTrace();
            return null;
        }
    }


    public void concatenate(){
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "concatenatedOutput.sql";
            PrintWriter out = new PrintWriter(path);

            int numberOfOutPuts = this.readedFiles.size();
            for (ArrayList<String> readedFile : this.readedFiles) {
                int numberOfRowsInOutPut = readedFile.size();

                for (int i = 0; i < numberOfRowsInOutPut; i++) {
                    out.println(readedFile.get(i));
                }

            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}