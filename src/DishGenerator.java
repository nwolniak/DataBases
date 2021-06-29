import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

public class DishGenerator {
    private static final String ADD_NEW_CATEGORY_PROCEDURE = "exec p_add_new_category ";
    private static final String ADD_NEW_DISH_PROCEDURE = "exec p_add_new_dish_with_ingredients ";
    private static final String ADD_NEW_INGREDIENT_PROCEDURE = "exec p_add_new_ingredient ";

    private final Random random = new Random();
    private final DataGenerator dataGenerator;
    private final HashMap<String, Integer> categories = new HashMap<>();
    private final HashMap<String, Integer> ingredients = new HashMap<>();
    private final Gson gson = new Gson();
    private Dish[] dishes;

    private final String outputDir;
    private final String inputDir;


    public DishGenerator(String inputDir, String outputDir) {
        this.dataGenerator = new DataGenerator(inputDir);
        this.outputDir = outputDir;
        this.inputDir = inputDir;
    }

    public void generateDishesAndCategories() {
        try {
            File dir = new File(outputDir);
            if(!dir.exists()) {
                dir.mkdirs();
            }
            String path1 = outputDir + File.separator + "categories.sql";
            String path2 = outputDir + File.separator + "dishes.sql";
            String path3 = outputDir + File.separator + "ingredients.sql";
            PrintWriter categoriesSQL = new PrintWriter(path1);
            PrintWriter dishesSQL = new PrintWriter(path2);
            PrintWriter ingredientsSQL = new PrintWriter(path3);


            this.dishes = gson.fromJson(new FileReader(inputDir + File.separator + "dania.json"), Dish[].class);
            int uniqueCategories = 0;
            int uniqueIngredients = 0;
            int uniqueDishes = 0;
            for(Dish dish : this.dishes) {

                if(!categories.containsKey(dish.category)) {
                    categories.put(dish.category, ++uniqueCategories);
                    String arguments = "\"" + dish.category + "\"";
                    categoriesSQL.println(ADD_NEW_CATEGORY_PROCEDURE + arguments);
                }
                int categoryID = categories.get(dish.category);

                String varName = "@temp" + ++uniqueDishes;
                StringBuilder temp = new StringBuilder("declare " + varName + " IngredientID_QuantityRequired_List");
                temp.append("\ninsert into ").append(varName).append(" values ");
                for(Ingredient ingredient : dish.ingredients) {
                    if(!ingredients.containsKey(ingredient.name)) {
                        ingredients.put(ingredient.name, ++uniqueIngredients);
                        String arguments = "\"" + ingredient.name + "\"";
                        ingredientsSQL.println(ADD_NEW_INGREDIENT_PROCEDURE + arguments);
                    }
                    int ingredientID = ingredients.get(ingredient.name);
                    temp.append("(").append(ingredientID).append(", ").append(ingredient.quantity).append("), ");
                }

                String result = temp.toString();
                dishesSQL.println(result.substring(0, result.length() - 2));
                String arguments = ("\"" + categoryID + "\"") + ", " + ("\"" + dish.name + "\"")  + ", " + ("\"" + dataGenerator.generatePrice() + "\"") + ", " + varName;
                dishesSQL.println(ADD_NEW_DISH_PROCEDURE + arguments + "\n");
            }
            categoriesSQL.flush();
            categoriesSQL.close();
            dishesSQL.flush();
            dishesSQL.close();
            ingredientsSQL.flush();
            ingredientsSQL.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Dish[] getDishes() {
        return this.dishes;
    }

}

class Ingredient {
    String name;
    Double quantity;
}

class Dish {
    String name;
    String category;
    Ingredient[] ingredients;
}