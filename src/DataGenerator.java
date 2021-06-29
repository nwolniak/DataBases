import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class DataGenerator {
    private final Random rand = new Random();
    private final String inputDir;
    protected String separator = File.separator;
    private final String[] files = new String[]{ "imiona_m.txt", "imiona_k.txt", "nazwiska.txt", "firmy.txt",
            "ulice.txt", "miasta.txt", "panstwa.txt" };
    private static final int MAX_PRICE = 100;

    private ArrayList<String> maleNameList;
    private ArrayList<String> femaleNameList;
    private ArrayList<String> surnameList;
    private ArrayList<String> companyList;
    private ArrayList<String> streetList;
    private ArrayList<String> cityList;
    private ArrayList<String> countryList;



    public DataGenerator(String inputDir) {
        this.inputDir = inputDir;
        read();
    }



    private void read() {
        maleNameList = readFile(files[0]);
        femaleNameList = readFile(files[1]);
        surnameList = readFile(files[2]);
        companyList = readFile(files[3]);
        streetList = readFile(files[4]);
        cityList = readFile(files[5]);
        countryList = readFile(files[6]);
    }

    private ArrayList<String> readFile(String fileName) {
        String path = inputDir + separator + fileName;
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


    public String generateFirstName() {
        String name;
        if (rand.nextBoolean()) {
            name = maleNameList.get(rand.nextInt(maleNameList.size()));
        }
        else {
            name = femaleNameList.get(rand.nextInt(femaleNameList.size()));
        }
        return name;
    }

    public String generateLastName(){
        return surnameList.get(rand.nextInt(surnameList.size()));
    }

    public String generateCompanyName(){
        return companyList.get(rand.nextInt(companyList.size()));
    }

    public String generateEmail(String name, String surname) {
        if(name.length() > 20) name = name.substring(0, 20);
        if(surname.length() > 20) surname = surname.substring(0,20);
        return name + surname + "@mail.com";
    }

    public String generatePhoneNumber() {
        int size = 9;
        StringBuilder sb = new StringBuilder();
        while(size-- > 0) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public String generateNIP(){
        int size = 10;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public String generateAddress(){
        return streetList.get(rand.nextInt(streetList.size()));
    }
    public String generateCity(){
        return cityList.get(rand.nextInt(cityList.size()));
    }
    public String generateCountry(){
        return countryList.get(rand.nextInt(countryList.size()));
    }
    public String generateDate() {
        Calendar cal = Calendar.getInstance();
        int day = rand.nextInt(cal.get(Calendar.DAY_OF_MONTH)) + 1;
        //int day = rand.nextInt(28) + 1;
        //int month = rand.nextInt(Calendar.MONTH) + 1;
        int month = rand.nextInt(cal.get(Calendar.MONTH) + 1) + 1;
        int year = cal.get(Calendar.YEAR);
        return year + "-" + month + "-" + day;
    }


    public String[] generateRestrictions(int days){
        Calendar cal = Calendar.getInstance();


        int day = rand.nextInt(cal.get(Calendar.DAY_OF_MONTH)) + 1;
        int month = rand.nextInt(cal.get(Calendar.MONTH) + 1) + 1;
        int year = cal.get(Calendar.YEAR);

        int day2 = day;
        int month2 = month;
        int year2 = year;

        if (day2 + days > 28) {
            while (day2 + days > 28) {
                day2 = (day2 + days) % 28;

                month2 += 1;
                if (month2 > 13) {
                    month2 = month2 % 12;
                    year2 += 1;
                }
                if (month2 == 0) {
                    month2 += 1;
                }
                days -= (28 - day);
            }
        } else {
            day2 = day + days;
        }


        return new String[]{year + "-" + month + "-" + day, year2 + "-" + month2 + "-" + day2};
    }


    public String[] generateTwoDates(int days) {
        Calendar cal = Calendar.getInstance();

        int day = rand.nextInt(28) + 1;
        int month = rand.nextInt(12) + 1;
        int year = 2020 + rand.nextInt(2);

        if (year == 2021){
            while (month >= 2){
                month -= 1;
            }
        }
        if (year == 2021 && month == 1){
            while (day >= 24){
                day -= 1;
            }
        }

        /*
        int day = rand.nextInt(cal.get(Calendar.DAY_OF_MONTH)) + 1;
        int month = rand.nextInt(cal.get(Calendar.MONTH) + 1) + 1;
        int year = cal.get(Calendar.YEAR);
        */
        int day2 = day;
        int month2 = month;
        int year2 = year;

        if (day2 + days > 28) {
            while (day2 + days > 28) {
                day2 = (day2 + days) % 28;

                month2 += 1;
                if (month2 > 13) {
                    month2 = month2 % 12;
                    year2 += 1;
                }
                if (month2 == 0) {
                    month2 += 1;
                }
                days -= (28 - day);
            }
        } else {
            day2 = day + days;
        }


        return new String[]{year + "-" + month + "-" + day, year2 + "-" + month2 + "-" + day2};
    }

    public double generatePrice() {
        return Math.round((double)(rand.nextInt(100*(MAX_PRICE+1))))/100d + 1;
    }


}