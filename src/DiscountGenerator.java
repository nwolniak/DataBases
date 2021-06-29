import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class DiscountGenerator {
    private static final String ADD_NEW_INDIVIDUAL_DISCOUNT = "exec p_add_new_individual_discount ";
    private static final String ADD_NEW_CONCERN_DISCOUNT = "exec p_add_new_concern_discount ";
    private final Random random = new Random();
    private DataGenerator dataGenerator;

    private String outputDir;

    public DiscountGenerator(String inputDir, String outputDir) {
        this.dataGenerator = new DataGenerator(inputDir);
        this.outputDir = outputDir;
    }

    public void generateConcernDiscounts(int numberOfConcernDiscounts) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "concernDiscounts.sql";
            PrintWriter out = new PrintWriter(path);

            String[] periodsOfTime = {"M", "Q"};
            int numberOfConcernDiscounts2 = numberOfConcernDiscounts;
            while (numberOfConcernDiscounts-- > 0) {
                String periodOfTime = periodsOfTime[0];
                int FZ = random.nextInt(5) + 1;
                int FK = random.nextInt(500) + 1;

                double FM = Math.round((0.05 + (0.3 - 0.05) * random.nextDouble())*1000)/1000.0;
                double FR = Math.round((0.001 + (0.05 - 0.001) * random.nextDouble())*1000)/1000.0;

                String startDate = dataGenerator.generateDate();

                String arguments = "\"" + periodOfTime + "\"" + ", " + FZ + ", " + FK + ", " + FR + ", " + FM + ", " + "\"" + startDate + "\"" + ", NULL";
                out.println(ADD_NEW_CONCERN_DISCOUNT + arguments);
            }
            while (numberOfConcernDiscounts2-- > 0) {
                String periodOfTime = periodsOfTime[1];
                int FK = random.nextInt(10000) + 1;

                double FM = Math.round((0.05 + (0.3 - 0.05) * random.nextDouble())*1000)/1000.0;
                double FR = Math.round((0.001 + (0.05 - 0.001) * random.nextDouble())*1000)/1000.0;

                String startDate = dataGenerator.generateDate();

                String arguments = "\"" + periodOfTime + "\"" + ", " + "NULL" + ", " + FK + ", " + FR + ", " + FM + ", " + "\"" + startDate + "\"" + ", NULL";
                out.println(ADD_NEW_CONCERN_DISCOUNT + arguments);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void generateIndividualDiscounts(int numberOfIndividualDiscounts) {
        try {
            File dir = new File(outputDir);
            if (!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "individualDiscounts.sql";
            PrintWriter out = new PrintWriter(path);


            while (numberOfIndividualDiscounts-- > 0) {
                int Z = random.nextInt(10) + 1;
                int K1 = random.nextInt(30) + 1;
                double K2 = random.nextInt(1000) + 1;
                double R = Math.round((0.001 + (0.05 - 0.001) * random.nextDouble())*1000)/1000.0;
                int duration = random.nextInt(28) + 1;
                int singleUse = random.nextInt(2);
                String startDate = dataGenerator.generateDate();

                if (singleUse == 1){
                    String arguments = "NULL" + ", " + "NULL" + ", " + K2 + ", " + R + ", " + "\"" + startDate + "\"" + ", NULL ," + duration + ", " + singleUse;
                    out.println(ADD_NEW_INDIVIDUAL_DISCOUNT + arguments);
                } else {
                    String arguments = Z + ", " + K1 + ", " + "NULL" + ", " + R + ", " + "\"" + startDate + "\"" + ", NULL ," + "NULL" + ", " + singleUse;
                    out.println(ADD_NEW_INDIVIDUAL_DISCOUNT + arguments);
                }

            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}