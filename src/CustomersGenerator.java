import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class CustomersGenerator {
    private static final String INDIVIDUAL_CUSTOMER_PROCEDURE = "exec p_add_individual_customer ";
    private static final String CONCERN_CUSTOMER_PROCEDURE = "exec p_add_concern_customer ";
    private Random random = new Random();
    private final DataGenerator dataGenerator;

    private String outputDir;

    public CustomersGenerator(String inputDir, String outputDir) {
        this.outputDir = outputDir;
        this.dataGenerator = new DataGenerator(inputDir);
    }

    public void generateIndividual(int individualCustomers) {
        try {
            File dir = new File(outputDir);
            if(!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "individualCustomers.sql";
            PrintWriter out = new PrintWriter(path);


            while(individualCustomers-- > 0) {
                String firstName = dataGenerator.generateFirstName();
                String lastName = dataGenerator.generateLastName();
                String arguments = ("\"" + firstName + "\"") + ", " + ("\"" + lastName  +"\"") + ", " +
                        ("\"" + dataGenerator.generateEmail(firstName, lastName) + "\"") + ", " + ("\"" + dataGenerator.generatePhoneNumber() + "\"");
                out.println(INDIVIDUAL_CUSTOMER_PROCEDURE + arguments);
            }

            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void generateConcern(int concernCustomers) {
        try {
            File dir = new File(outputDir);
            if(!dir.exists())
                dir.mkdirs();
            String path = outputDir + File.separator + "concernCustomers.sql";
            PrintWriter out = new PrintWriter(path);


            while(concernCustomers-- > 0) {
                String firstName = dataGenerator.generateFirstName();
                String lastName = dataGenerator.generateLastName();
                String arguments = ("\"" + firstName + "\"") + ", " + ("\"" + lastName + "\"") + ", " +
                        ("\"" + dataGenerator.generateCompanyName() + "\"") + ", " +
                        ("\"" + dataGenerator.generateEmail(firstName, lastName) + "\"") + ", " +
                        ("\"" + dataGenerator.generatePhoneNumber() + "\"") + ", " +
                        ("\"" + dataGenerator.generateNIP() + "\"");
                out.println(CONCERN_CUSTOMER_PROCEDURE + arguments);
            }

            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}