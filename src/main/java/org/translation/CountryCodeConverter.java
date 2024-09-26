package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// TODO CheckStyle: Wrong lexicographical order for 'java.util.HashMap' import (remove this comment once resolved) (Complete)
// Swapped the order of the imports to make alphabetically ordered

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {
    private static final int COUNTRY_NAME = 0;  // Name of the country
    private static final int ALPHA3 = 2;  // Alpha 3 code

    private HashMap<String, String> codeToCountry = new HashMap<>();  // Country Name <-> Code
    private int numCountries = 0;  // Number of countries

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            numCountries = lines.size() - 1;

            for (String line : lines.subList(1, lines.size())) {
                String[] parts = line.split("\t");

                codeToCountry.put(parts[ALPHA3], parts[COUNTRY_NAME]);
                codeToCountry.put(parts[COUNTRY_NAME], parts[ALPHA3]);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        String codeUpper = code.toUpperCase();
        if (codeToCountry.containsKey(codeUpper)) {
            return codeToCountry.get(codeUpper);
        }
        return code;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        if (codeToCountry.containsKey(country)) {
            return codeToCountry.get(country);
        }
        return country;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return numCountries;
    }
}
