public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No args provided! Available options");
            System.out.println("\t-a - for adding currency code");
            System.out.println("\t-r - for removing currency code");
            System.out.println("\t--list - for displaying favorite currency code list");
            System.out.println("\t-c - for using currency converter");
        } else {
            CurrencyConverterUtil converter = new CurrencyConverterUtil();
            if (args[0].equals("-a")) {
                converter.addCurrencyCodeToFavoriteCodeList(args);
            } else if (args[0].equals("-r")) {
                converter.removeCurrencyFromFavoriteCurrencyList(args);
            } else if (args[0].equals("--list")) {
                converter.displayFavoriteCurrencyList();
            } else if (args[0].equals("-c")) {
                converter.currencyConverterController(args);
            } else {
                System.out.println("Please use valid commands!!");
            }
        }
    }
}

