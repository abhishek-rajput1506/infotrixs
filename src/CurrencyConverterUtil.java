import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

class CurrencyConverterUtil {
    private final String API_KEY = "9ed90e7f5338afd14e198fd3";
    private final String filePath = "data.txt";
    private DecimalFormat formatter = new DecimalFormat("00.00");
    private String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private Scanner sc;
    private HashMap<String, String> currencyCodes;

    public CurrencyConverterUtil() {
        this.currencyCodes = new HashMap<>();
        sc = new Scanner(System.in).useDelimiter("\n");
        this.readDataFromFile();
    }

    private void writeDataToFile() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(this.filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(this.currencyCodes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readDataFromFile() {
        try (FileInputStream fileInputStream = new FileInputStream(this.filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            this.currencyCodes = (HashMap<String, String>) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist, create it and initialize currencyCodes as an empty HashMap
            if (e instanceof FileNotFoundException) {
                this.currencyCodes = new HashMap<>();
                this.writeDataToFile();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void loadSampleData() {
        this.addCurrencyCodeToFavoriteCodeListUtil("USD", "US Dollar");
        this.addCurrencyCodeToFavoriteCodeListUtil("CAD", "Canadian Dollar");
        this.addCurrencyCodeToFavoriteCodeListUtil("EUR", "EURO");
        this.addCurrencyCodeToFavoriteCodeListUtil("INR", "Indian Rupee");
        this.addCurrencyCodeToFavoriteCodeListUtil("HKD", "Hon Kong Dollar");
    }

    private String addCurrencyCodeToFavoriteCodeListUtil(String currencyCode, String currencyName) {

        if (this.currencyCodes.containsKey(currencyCode))
            return "Currency code " + currencyCode + " already present in List";

        this.currencyCodes.put(currencyCode, currencyName);
        this.writeDataToFile();
        return "Added " + currencyName + " to favorite currency list";
    }

    public void addCurrencyCodeToFavoriteCodeList(String[] args) {

        if (args.length != 3)
            System.out.println("Invalid number of arguments, -a flag requires 2 arguments (currency ISO code, currency Name)");

        String currencyCode = args[1];

        String currencyName = args[2];


        System.out.println(this.addCurrencyCodeToFavoriteCodeListUtil(currencyCode, currencyName));
    }

    public void removeCurrencyFromFavoriteCurrencyList(String[] args) {
        if (args.length != 2) {
            System.out.println("-r require only one argument (currency ISO code)");
            return;
        }

        String currencyCode = args[1];

        if (!this.currencyCodes.containsKey(currencyCode)) {
            System.out.println("This code is not present in favorite currency codes list");
            return;
        }
        this.currencyCodes.remove(currencyCode);
        this.writeDataToFile();
        System.out.println("Removed " + currencyCode + " from favorite currency list ");
    }

    public void displayFavoriteCurrencyList() {
        this.readDataFromFile();
        System.out.println("Available Currency Codes:");
        for (Map.Entry<String, String> entry : this.currencyCodes.entrySet()) {
            System.out.println("\t" + entry.getKey() + ": " + entry.getValue());
        }
    }

    public void currencyConverterController(String[] args) {
        if (args.length != 4) {
            System.out.println("Invalid number of arguments, -c flag requires 3 arguments (base currency code, to currency code, amount).");
            return;
        }

        String fromCode = args[1];
        String toCode = args[2];
        double amount = Double.parseDouble(args[3]);

        System.out.println("From: " + fromCode + " To: " + toCode + " amount: " + amount);
        try {
            String responseString = this.sendHttpGETRequest(fromCode, toCode, amount);
            System.out.println(responseString);
        } catch (FileNotFoundException e) {
            System.out.println("Please provide valid currency codes\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {

        //https://v6.exchangerate-api.com/v6/YOUR-API-KEY/pair/EUR/GBP/AMOUNT
        String GET_URL = this.BASE_URL + this.API_KEY + "/" + "pair" + "/" + fromCode + "/" + toCode + "/" + amount;
//        System.out.println(GET_URL);
        URL url = new URL(GET_URL);

        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        httpURLConnection.setRequestMethod("GET");

        int responseCode = httpURLConnection.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuffer response = new StringBuffer();

        String inputLine;

        while ((inputLine = in.readLine()) != null) {

            response.append(inputLine);

        }
        in.close();

        JSONObject obj = new JSONObject(response.toString());
        if (Objects.equals(obj.getString("result"), "success")) {
            String conversionResult = this.formatter.format(obj.getDouble("conversion_result"));
            return "Result: " + amount + " " + fromCode + " = " + conversionResult + " " + toCode;
        } else {
            String errorMessage = obj.getString("error-type");
            return "Conversion cannot be done reason: " + errorMessage;
        }
    }
}