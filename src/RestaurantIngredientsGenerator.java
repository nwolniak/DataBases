import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class RestaurantIngredientsGenerator {
    private static final String ADD_INGREDIENT_TO_RESTAURANT = "exec p_add_ingredients_in_restaurant ";

    private Random random = new Random();
    private DataGenerator dataGenerator;


    private String outputDir;

    public RestaurantIngredientsGenerator(String inputDir, String outputDir) {
        this.dataGenerator = new DataGenerator(inputDir);
        this.outputDir = outputDir;
    }


    public void generateIngredients(int restaurants, int numberOfIngredients) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "ingredients_to_restaurant.sql";
            PrintWriter out = new PrintWriter(path);


            for(int j = 1 ; j <= restaurants; j++){
                for (int i = 1 ; i <= numberOfIngredients ; i++){
                    String arguments = j + ", " + i + ", " + 1000;
                    out.println(ADD_INGREDIENT_TO_RESTAURANT + arguments);
                }
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
