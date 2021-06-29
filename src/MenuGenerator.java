import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MenuGenerator {
    private static final String ADD_DISH_TO_MENU_PROCEDURE = "exec p_add_dish_to_menu ";

    private final String outputDir;

    public MenuGenerator(String inputDir, String outputDir) {
        this.outputDir = outputDir;
    }


    public void addDishesToMenu(Dish[] dishes) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "menu.sql";
            PrintWriter out = new PrintWriter(path);

            for(int i = 0; i<dishes.length; i++) {
                String arguments = (i+1) + ", " + ("\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")) + "\"") + ", " + ("\"" + LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss")) + "\"");
                out.println(ADD_DISH_TO_MENU_PROCEDURE + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}