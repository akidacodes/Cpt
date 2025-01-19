import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * author: Omar Akida
 * date: 19/01/2025
 * Globistry
 */
public class Globistry {

    /**
     * The main entry point of the Globistry application.
     * <p>
     * Prompts the user to enter their name and traveler type, displays a list of 
     * affordable countries based on budget, and allows selection of country, city, 
     * and activities. Finally, handles currency conversions.
     * </p>
     *
     * @param args command-line arguments (not used)
     */
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

        // Initializing the country budgets
        List<CountryBudget> countryBudgets = initializeCountryBudgets();
        Traveler traveler = createTraveler(name, travelerType, flightBudget);

        showAffordableCountries(countryBudgets, flightBudget);

        System.out.println("Enter which country you'd prefer traveling to: ");
        String preferredCountry = input.nextLine().trim().toLowerCase();
        System.out.println("You have chosen to travel to " + preferredCountry + ".");

        // Create a restaurants map by country and city
        Map<String, Map<String, List<String>>> restaurantsMap = createRestaurantsMap();
        if (restaurantsMap.containsKey(preferredCountry)) {
            handleCitySelection(preferredCountry, restaurantsMap, input);
        } else {
            System.out.println("No cities found for " + preferredCountry + ".");
        }

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

    /**
     * Determines the type of traveler (Business or Leisure) and prompts
     * for additional details if the traveler is a Business traveler.
     *
     * @param name         the traveler's name
     * @param travelerType the user's traveler type input (Business/Leisure)
     * @param input        a Scanner for reading user input
     */
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

    /**
     * Creates a nested map of countries, their cities, and the restaurants in each city.
     * <p>
     * The returned structure is:
     * <code>{ country -> { city -> [restaurant names] } }</code>
     * </p>
     *
     * @return a map of restaurant data keyed by country and city
     */
    public static Map<String, Map<String, List<String>>> createRestaurantsMap() {
        Map<String, Map<String, List<String>>> restaurantsMap = new HashMap<>();

        Map<String, List<String>> moroccoCities = new HashMap<>();
        moroccoCities.put("Marrakech", List.of("La Mamounia Hotel", "Dar Yacout", "Riad Kniza"));
        moroccoCities.put("Casablanca", List.of("La Sqala", "Rick's Café", "Le Cabestan", "Restaurant du Port de Pêche"));
        moroccoCities.put("Rabat", List.of("Dinarjat", "Le Dhow", "Restaurant Cosmopolitan", "Al Marsa"));
        restaurantsMap.put("morocco", moroccoCities);

        Map<String, List<String>> japanCities = new HashMap<>();
        japanCities.put("Tokyo", List.of("Sukiyabashi Jiro", "Ichiran Ramen", "Robot Restaurant"));
        japanCities.put("Kyoto", List.of("Nishiki Market", "Kikunoi Honten", "Arashiyama Bamboo Grove Cafe"));
        restaurantsMap.put("japan", japanCities);

        Map<String, List<String>> egyptCities = new HashMap<>();
        egyptCities.put("Cairo", List.of("Abou Tarek", "Koshary El Tahrir", "Sequoia"));
        egyptCities.put("Alexandria", List.of("Fish Market", "Balbaa Village", "Hosny"));
        restaurantsMap.put("egypt", egyptCities);

        Map<String, List<String>> ukCities = new HashMap<>();
        ukCities.put("London", List.of("The Ivy", "Dishoom", "Sketch"));
        ukCities.put("Manchester", List.of("Hawksmoor", "Rudy's Pizza", "The Refuge"));
        restaurantsMap.put("uk", ukCities);

        Map<String, List<String>> germanyCities = new HashMap<>();
        germanyCities.put("Berlin", List.of("Curry 36", "Tim Raue", "Berliner Republik"));
        germanyCities.put("Munich", List.of("Hofbräuhaus", "Augustiner-Keller", "Tantris"));
        restaurantsMap.put("germany", germanyCities);

        return restaurantsMap;
    }

    /**
     * Initializes and returns a list of country budgets, each containing:
     * country name, flight budget, and currency code.
     *
     * @return a list of {@link CountryBudget} objects
     */
    public static List<CountryBudget> initializeCountryBudgets() {
        return List.of(
            new CountryBudget("Morocco", 850, "MAD"),
            new CountryBudget("Japan", 880, "JPY"),
            new CountryBudget("Egypt", 750, "EGP"),
            new CountryBudget("UK", 700, "GBP"),
            new CountryBudget("Germany", 750, "EUR")
        );
    }

    /**
     * Creates a {@link Traveler} instance (either {@link BusinessTraveler} or {@link LeisureTraveler})
     * based on the traveler's type input.
     *
     * @param name         the traveler's name
     * @param travelerType the traveler's type (Business/Leisure)
     * @param budget       the traveler's flight budget
     * @return a {@link Traveler} object (either Business or Leisure)
     */
    public static Traveler createTraveler(String name, String travelerType, double budget) {
        if (travelerType.equalsIgnoreCase("Business") || travelerType.equalsIgnoreCase("B")) {
            return new BusinessTraveler(name, budget);
        } else {
            return new LeisureTraveler(name, budget);
        }
    }

    /**
     * Displays the list of countries whose flight cost is within the user's budget.
     * If no countries are found, terminates the program.
     *
     * @param countryBudgets a list of {@link CountryBudget} objects
     * @param budget         the user's flight budget
     */
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

    // Ai used -> ChatGPT, Prompt -> "Add city selection handling"
    /**
     * Handles city selection within the chosen country, displays the restaurants in that city,
     * and asks the user for their lodging choice.
     *
     * @param preferredCountry the name of the country chosen by the user
     * @param restaurantsMap   a map of countries -> cities -> restaurant names
     * @param input            a Scanner for user input
     */
    public static void handleCitySelection(String preferredCountry,
                                           Map<String, Map<String, List<String>>> restaurantsMap,
                                           Scanner input) {
        Map<String, List<String>> cities = restaurantsMap.get(preferredCountry);
        System.out.println("Available cities: " + cities.keySet());
        System.out.println("Which city will you be staying in?");
        String city = input.nextLine();

        String matchedCity = null;
        for (String existingCity : cities.keySet()) {
            if (existingCity.equalsIgnoreCase(city)) {
                matchedCity = existingCity;
                break;
            }
        }

        if (matchedCity != null) {
            System.out.println("Restaurants in " + matchedCity + ": " + cities.get(matchedCity));
            System.out.println("Which location will you be staying in? (Hotel, Airbnb, House)");
            String location = input.nextLine();
            System.out.println("Great! You chose to stay in a " + location + " in " + matchedCity + ".");
        } else {
            System.out.println("City was not found.");
        }
    }

    /**
     * Initializes the adventure map with activities specific to each country.
     *
     * @return an {@link AdventureMap} containing a list of activities keyed by country
     */
    public static AdventureMap initializeAdventureMap() {
        AdventureMap map = new AdventureMap();
        map.addActivity("morocco", "Desert Safari, Sahara");
        map.addActivity("japan", "Cherry Blossom Festival, Tokyo");
        map.addActivity("egypt", "Pyramids of Giza Tour, Cairo");
        map.addActivity("uk", "Visit the British Museum, London");
        map.addActivity("germany", "Oktoberfest, Munich");
        return map;
    }

    // Ai used -> ChatGPT, Prompt -> "Add activity selection handling to deal with the correct corresponding number to the activity listed"
    /**
     * Presents the user with a list of activities for the chosen country
     * and allows the user to select one by its corresponding number.
     *
     * @param preferredCountry the country chosen by the user
     * @param adventureMap     an {@link AdventureMap} storing country-activity mappings
     * @param input            a Scanner for user input
     */
    public static void handleActivitySelection(String preferredCountry,
                                               AdventureMap adventureMap,
                                               Scanner input) {
        List<String> availableActivities = adventureMap.getActivities(preferredCountry);
        System.out.println("Available activities in " + preferredCountry + ": ");

        for (int i = 0; i < availableActivities.size(); i++) {
            System.out.println((i + 1) + ") " + availableActivities.get(i));
        }

        System.out.println("Select an activity by entering the corresponding number:");
        int choice = input.nextInt();
        input.nextLine();

        if (choice >= 1 && choice <= availableActivities.size()) {
            System.out.println("You selected: " + availableActivities.get(choice - 1));
        } else {
            System.out.println("Invalid selection.");
        }
    }

    /**
     * Handles currency conversion from the user's preferred currency to the
     * destination country's currency, then prints the converted budget.
     *
     * @param preferredCountry the country the user has chosen
     * @param countryBudgets   a list of {@link CountryBudget} objects
     * @param input            a Scanner for user input
     */
    public static void handleCurrencyConversion(String preferredCountry,
                                                List<CountryBudget> countryBudgets,
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
            double converted = budget * exchangeRate;
            System.out.printf("Your budget in %s: %.2f%n", selectedCountry.getCountryName(), converted);
        } else {
            System.out.println("Country not found or currency not supported.");
        }
    }

    // Ai used: ChatGPT, Prompt -> "Provide example mock currency rates"
    /**
     * Provides a mock exchange rate between two currencies for demonstration purposes.
     *
     * @param fromCurrency the 3-letter currency code converting from
     * @param toCurrency   the 3-letter currency code converting to
     * @return the exchange rate (toCurrency / fromCurrency)
     */
    public static double getExchangeRate(String fromCurrency, String toCurrency) {
        Map<String, Double> exchangeRates = Map.of(
            "USD", 1.0,
            "EUR", 0.85,
            "JPY", 110.15,
            "GBP", 0.75,
            "AUD", 1.5
        );
        double toRate = exchangeRates.getOrDefault(toCurrency.toUpperCase(), 1.0);
        double fromRate = exchangeRates.getOrDefault(fromCurrency.toUpperCase(), 1.0);
        return toRate / fromRate;
    }

    /**
     * Initializes a list of {@link Restaurant} objects for the specified city.
     * <p>
     * Currently supports only Moroccan cities in this example.
     * </p>
     *
     * @param city the city name
     * @return a list of restaurants available in that city
     */
    public static List<Restaurant> initializeRestaurants(String city) {
        List<Restaurant> restaurants = new ArrayList<>();
        switch (city.toLowerCase()) {
            case "marrakech":
                restaurants.add(new Restaurant("Pepe Nero", "Fusion of moroccan and italian cuisine"));
                restaurants.add(new Restaurant("Dar Yacout", "Traditional cuisine"));
                restaurants.add(new Restaurant("Café Clock", "Live music and cultural events"));
                break;
            case "casablanca":
                restaurants.add(new Restaurant("La Sqala", "Historical architecture"));
                restaurants.add(new Restaurant("Rick's Café", "Iconic restaurant"));
                restaurants.add(new Restaurant("Le Cabestan", "Seafront dining"));
                restaurants.add(new Restaurant("Restaurant du Port de Pêche", "Seafood"));
                break;
            case "rabat":
                restaurants.add(new Restaurant("Dinarjat", "Traditional Moroccan"));
                restaurants.add(new Restaurant("Le Dhow", "Floating restaurant"));
                restaurants.add(new Restaurant("Restaurant Cosmopolitan", "French cuisine"));
                restaurants.add(new Restaurant("Al Marsa", "Seafood"));
                break;
            default:
                break;
        }
        return restaurants;
    }
}

/**
 * Represents the budget information for traveling to a specific country,
 * including flight budget and currency code.
 */
class CountryBudget {
    private final String countryName;
    private final double flightBudget;
    private final String currencyCode;

    /**
     * Constructs a {@code CountryBudget} instance.
     *
     * @param countryName  the name of the country
     * @param flightBudget the required flight budget for this country
     * @param currencyCode the ISO currency code for the country's currency
     */
    public CountryBudget(String countryName, double flightBudget, String currencyCode) {
        this.countryName = countryName;
        this.flightBudget = flightBudget;
        this.currencyCode = currencyCode;
    }

    /**
     * @return the name of the country
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return the flight budget for this country
     */
    public double getFlightBudget() {
        return flightBudget;
    }

    /**
     * @return the ISO currency code (e.g., 'MAD', 'JPY', 'EGP') for this country
     */
    public String getCurrencyCode() {
        return currencyCode;
    }
}

/**
 * A generic traveler with a specified name and budget.
 */
class Traveler {
    private final String name;
    private final double budget;

    /**
     * Constructs a {@code Traveler} object.
     *
     * @param name   the traveler's name
     * @param budget the traveler's budget
     */
    public Traveler(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    /**
     * @return the traveler's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the traveler's budget
     */
    public double getBudget() {
        return budget;
    }
}

/**
 * A traveler who travels for business purposes.
 */
class BusinessTraveler extends Traveler {

    /**
     * Constructs a {@code BusinessTraveler} object.
     *
     * @param name   the traveler's name
     * @param budget the traveler's budget
     */
    public BusinessTraveler(String name, double budget) {
        super(name, budget);
    }
}

/**
 * A traveler who travels for leisure purposes.
 */
class LeisureTraveler extends Traveler {

    /**
     * Constructs a {@code LeisureTraveler} object.
     *
     * @param name   the traveler's name
     * @param budget the traveler's budget
     */
    public LeisureTraveler(String name, double budget) {
        super(name, budget);
    }
}

/**
 * A mapping of countries to a list of activities available in each country.
 */
class AdventureMap {
    private final Map<String, List<String>> activitiesMap = new HashMap<>();

    /**
     * Adds an activity for the specified country.
     *
     * @param country  the country to add the activity to
     * @param activity a description of the activity
     */
    public void addActivity(String country, String activity) {
        activitiesMap.computeIfAbsent(country, k -> new ArrayList<>()).add(activity);
    }

    /**
     * Checks if a given country has any associated activities.
     *
     * @param country the country to check
     * @return {@code true} if the country has one or more activities, otherwise {@code false}
     */
    public boolean hasActivities(String country) {
        return activitiesMap.containsKey(country) && !activitiesMap.get(country).isEmpty();
    }

    /**
     * Retrieves the list of activities for the specified country.
     *
     * @param country the country for which to retrieve activities
     * @return a list of activity descriptions, or an empty list if none exist
     */
    public List<String> getActivities(String country) {
        return activitiesMap.getOrDefault(country, new ArrayList<>());
    }
}

/**
 * Represents a restaurant with a name and notable pros/attributes.
 */
class Restaurant {
    private final String name;
    private final String pros;

    /**
     * Constructs a {@code Restaurant} object.
     *
     * @param name the name of the restaurant
     * @param pros a short description of the restaurant's highlights
     */
    public Restaurant(String name, String pros) {
        this.name = name;
        this.pros = pros;
    }

    /**
     * @return the restaurant's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return a short description of the restaurant's positive features
     */
    public String getPros() {
        return pros;
    }
}
