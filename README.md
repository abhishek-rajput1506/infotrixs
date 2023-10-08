# Currency Converter Command-Line Application Documentation

## Overview

This Java command-line application allows you to perform various currency-related operations, including managing a list of favorite currencies, adding and removing currencies from the list, and converting an amount from one currency to another using the latest exchange rates obtained from the web.

## Usage

To use the application, run the JAR file with one of the following command-line options:

### List Favorite Currencies

To display the list of favorite currencies, use the `--list` option:

```
java -jar task1.jar --list
```

### Add Currency to Favorite List

To add a currency to the list of favorite currencies, use the `-a` option followed by the currency code and currency name:

```
java -jar task1.jar -a Currency_code Currency_name
```

Example:

```
java -jar task1.jar -a USD US_Dollar
```

This command adds the US_Dollar (USD) to the list of favorite currencies with the name "US_Dollar."(Please don't use as "US Dollar")

### Remove Currency from Favorite List

To remove a currency from the list of favorite currencies, use the `-r` option followed by the currency code:

```
java -jar task1.jar -r Currency_code
```

Example:

```
java -jar task1.jar -r EUR
```

This command removes the Euro (EUR) from the list of favorite currencies.

### Currency Conversion

To convert an amount from one currency to another using the latest exchange rates obtained from the web, use the `-c` option followed by the base currency code, target currency code, and the amount to convert:

```
java -jar task1.jar -c Base_currency_code Target_currency_code Amount_to_convert
```

Example:

```
java -jar task1.jar -c USD EUR 100
```

This command converts 100 US Dollars (USD) to Euros (EUR) using the latest exchange rates.

## Note

- The application fetches the latest exchange rates from the web for currency conversion.
- Make sure you have an active internet connection for currency conversion to work correctly.

---

This documentation provides a clear explanation of how to use your Java command-line currency converter application with the available commands and options. Users can refer to this documentation to perform various currency-related tasks using your application.
