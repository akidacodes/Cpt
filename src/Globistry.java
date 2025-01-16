import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Globistry {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter your name: ");
        String name = input.nextLine();
        System.out.println(name + ", welcome to Globistry!");

        System.out.println("What type of traveler are you? (Business/Leisure): ");
        String travelerType = input.nextLine().trim();
        handleTravelerType(name, travelerType, input);

        System.out.println("What's your flight budget?");
        double flightBudget = input.nextDouble();
        input.nextLine();

        List<CountryBudget> countryBudgets = initializeCountryBudgets();
        Traveler traveler = createTraveler(name, travelerType, flightBudget);
        showAffordableCountries(countryBudgets, flightBudget);

        System.out.println("Enter which country you'd prefer traveling to: ");
        String preferredCountry = input.nextLine().trim().toLowerCase();
        System.out.println("You have chosen to travel to " + preferredCountry + ".");

        System.out.println("Which activity would you prefer doing?");
        AdventureMap adventureMap = initializeAdventureMap();
        if (!adventureMap.hasActivities(preferredCountry)) {
            System.out.println("You have chosen to do this activity.");
        } else {
            handleActivitySelection(preferredCountry, adventureMap, input);
        }

        handleCurrencyConversion(preferredCountry, countryBudgets, input);
        input.close();
    }

    public static void handleTravelerType(String name, String travelerType, Scanner input) {
        if (travelerType.equalsIgnoreCase("Business") || travelerType.equalsIgnoreCase("B")) {
            System.out.println(name + " you are a Business Traveler.");
            System.out.println("What is your job?");
            String job = input.nextLine();
            System.out.println("Which company are you traveling for?");
            String company = input.nextLine();
            System.out.println("What is the purpose of your business trip?");
            String purpose = input.nextLine();
            System.out.printf("Job: %s, Company: %s, Purpose: %s%n", job, company, purpose);
        } else if (travelerType.equalsIgnoreCase("Leisure") || travelerType.equalsIgnoreCase("L")) {
            System.out.println(name + " you are a Leisure Traveler.");
        } else {
            System.out.println("Invalid traveler type. Exiting.");
            System.exit(0);
        }
    }

    public static List<CountryBudget> initializeCountryBudgets() {
        return List.of(
                new CountryBudget("Morocco", 850, "MAD"),
                new CountryBudget("Japan", 880, "JPY"),
                new CountryBudget("Egypt", 750, "EGP"),
                new CountryBudget("UK", 700, "GBP"),
                new CountryBudget("Germany", 750, "EUR"));
    }

    public static Traveler createTraveler(String name, String travelerType, double budget) {
        if (travelerType.equalsIgnoreCase("Business") || travelerType.equalsIgnoreCase("B")) {
            return new BusinessTraveler(name, budget);
        } else {
            return new LeisureTraveler(name, budget);
        }
    }

    public static void showAffordableCountries(List<CountryBudget> countryBudgets, double budget) {
        System.out.println("Based on your flight budget, you can travel to the following countries:");
        boolean found = false;
        for (CountryBudget cb : countryBudgets) {
            if (cb.getFlightBudget() <= budget) {
                System.out.println(cb.getCountryName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Unfortunately, there are no countries available within your budget.");
            System.exit(0);
        }
    }

    public static Map<String, Map<String, List<String>>> initializeAccommodations() {
        Map<String, Map<String, List<String>>> accommodations = new HashMap<>();
        accommodations.put("morocco", Map.of(
                "Marrakech", List.of("La Mamounia Hotel", "Luxury Riad Airbnb"),
                "Casablanca", List.of("Luxury Riad Airbnb"),
                "Rabat", List.of("Traditional House")));
        accommodations.put("japan", Map.of(
                "Tokyo", List.of("The Ritz-Carlton Hotel"),
                "Kyoto", List.of("Kyoto Machiya Airbnb"),
                "Osaka", List.of("Modern House"),
                "Hokkaido", List.of("Forest Spa Resort Hotel")));
        return accommodations;
    }

    public static void handleCitySelection(String preferredCountry,
            Map<String, Map<String, List<String>>> accommodationsMap, Scanner input) {
        Map<String, List<String>> cities = accommodationsMap.get(preferredCountry);
        System.out.println("Available cities: " + cities.keySet());
        System.out.println("Which city will you be staying in?");
        String city = input.nextLine();
        if (cities.containsKey(city)) {
            System.out.println("Accommodations in " + city + ": " + cities.get(city));
        } else {
            System.out.println("City not found.");
        }
    }

    public static AdventureMap initializeAdventureMap() {
        AdventureMap map = new AdventureMap();
        map.addActivity("morocco", "Desert Safari, Sahara");
        map.addActivity("japan", "Cherry Blossom Festival, Tokyo");
        map.addActivity("egypt", "Pyramids of Giza Tour, Cairo");
        map.addActivity("uk", "Visit the British Museum, London");
        map.addActivity("germany", "Oktoberfest, Munich");
        return map;
    }

    public static void handleActivitySelection(String preferredCountry, AdventureMap adventureMap, Scanner input) {
        System.out.println(
                "Available activities in " + preferredCountry + ": " + adventureMap.getActivities(preferredCountry));
        System.out.println("Select an activity by entering the corresponding number:");
        int activity = input.nextInt();
        input.nextLine();
        switch (activity) {
            case 1:
                System.out.println("You selected: " + adventureMap.getActivities(preferredCountry).get(0));
                break;

            case 2:
                System.out.println("You selected: " + adventureMap.getActivities(preferredCountry).get(1));

                break;
            case 3:
                System.out.println("You selected: " + adventureMap.getActivities(preferredCountry).get(2));
                break;
            case 4:
                System.out.println("You selected: " + adventureMap.getActivities(preferredCountry).get(3));
                break;
            case 5:
                System.out.println("You selected: " + adventureMap.getActivities(preferredCountry).get(4));
                break;
            default:
        }
        System.out.println("Invalid selection.");
    }

    public static void handleCurrencyConversion(String preferredCountry, List<CountryBudget> countryBudgets,
            Scanner input) {
        System.out.println("Enter your preferred currency (USD, EUR, GBP, AUD, JPY): ");
        String preferredCurrency = input.nextLine().toUpperCase();
        System.out.println("What is the budget in your preferred currency?");
        double budget = input.nextDouble();

        CountryBudget selectedCountry = countryBudgets.stream()
                .filter(cb -> cb.getCountryName().equalsIgnoreCase(preferredCountry))
                .findFirst()
                .orElse(null);

        if (selectedCountry != null) {
            double exchangeRate = getExchangeRate(preferredCurrency, selectedCountry.getCurrencyCode());
            System.out.printf("Your budget in %s: %.2f%n", selectedCountry.getCountryName(), budget * exchangeRate);
        } else {
            System.out.println("Country not found or currency not supported.");
        }
    }

    public static double getExchangeRate(String fromCurrency, String toCurrency) {
        Map<String, Double> exchangeRates = Map.of(
                "USD", 1.0,
                "EUR", 0.85,
                "JPY", 110.15);
        return exchangeRates.getOrDefault(toCurrency, 1.0) / exchangeRates.getOrDefault(fromCurrency, 1.0);
    }
}

class CountryBudget {
    private final String countryName;
    private final double flightBudget;
    private final String currencyCode;

    public CountryBudget(String countryName, double flightBudget, String currencyCode) {
        this.countryName = countryName;
        this.flightBudget = flightBudget;
        this.currencyCode = currencyCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public double getFlightBudget() {
        return flightBudget;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }
}

class Traveler {
    private final String name;
    private final double budget;

    public Traveler(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public double getBudget() {
        return budget;
    }
}

class BusinessTraveler extends Traveler {
    public BusinessTraveler(String name, double budget) {
        super(name, budget);
    }
}

class LeisureTraveler extends Traveler {
    public LeisureTraveler(String name, double budget) {
        super(name, budget);
    }
}

class AdventureMap {
    private final Map<String, List<String>> activitiesMap = new HashMap<>();

    public void addActivity(String country, String activity) {
        activitiesMap.computeIfAbsent(country, k -> new ArrayList<>()).add(activity);
    }

    public boolean hasActivities(String country) {
        return activitiesMap.containsKey(country) && !activitiesMap.get(country).isEmpty();
    }

    public List<String> getActivities(String country) {
        return activitiesMap.getOrDefault(country, new ArrayList<>());
    }
}