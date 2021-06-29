import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TablesGenerator {
    private static final String ADD_NEW_TABLE = "exec p_add_new_table ";
    private static final String ADD_NEW_TABLE_RESTRICTION = "exec p_add_tablerestriction ";
    private Random random = new Random();
    private DataGenerator dataGenerator;
    private List<Integer> tableSeatsList = new ArrayList<>();

    private String outputDir;

    public TablesGenerator(String inputDir, String outputDir) {
        this.dataGenerator = new DataGenerator(inputDir);
        this.outputDir = outputDir;
    }


    public void generateTables(int tables, int restaurants, int maxSeats) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "tables.sql";
            PrintWriter out = new PrintWriter(path);


            while (tables-- > 0) {
                int restaurantID = random.nextInt(restaurants) + 1;
                int seats = random.nextInt(maxSeats) + 1;
                this.tableSeatsList.add(seats);
                String arguments = restaurantID + ", " + seats;
                out.println(ADD_NEW_TABLE + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateTablesRestrictions(int tables) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "tablesRestrictions.sql";
            PrintWriter out = new PrintWriter(path);

            int[] restrictionsTime = {7, 14, 21, 28};
            int n = restrictionsTime.length;

            while (tables-- > 0) {
                int seats = random.nextInt(this.tableSeatsList.get(tables));
                String[] restrictionsDates = dataGenerator.generateRestrictions(restrictionsTime[random.nextInt(n)]);
                String arguments = (tables + 1) + ", " + seats + ", " + "\"" + restrictionsDates[0] + "\"" + ", " + "\"" + restrictionsDates[1] + "\"";
                out.println(ADD_NEW_TABLE_RESTRICTION + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}