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

        // Initializing the countrybudgets
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

    // A method to handle the traveler type and ask certain questions to determine the traveler type
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

   // A method used to set up the data structure for each country including the cities and restaurants available
    
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

    
    // A method used to initialize the country budgets
    public static List<CountryBudget> initializeCountryBudgets() {
        return List.of(
            new CountryBudget("Morocco", 850, "MAD"),
            new CountryBudget("Japan", 880, "JPY"),
            new CountryBudget("Egypt", 750, "EGP"),
            new CountryBudget("UK", 700, "GBP"),
            new CountryBudget("Germany", 750, "EUR")
        );
    }

    // A method to decide which type of traveler is the person whether using b for business or l for leisure
    public static Traveler createTraveler(String name, String travelerType, double budget) {
        if (travelerType.equalsIgnoreCase("Business") || travelerType.equalsIgnoreCase("B")) {
            return new BusinessTraveler(name, budget);
        } else {
            return new LeisureTraveler(name, budget);
        }
    }
    
    // A method created to show the affordable countries based on the person's flight budget
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

    // A method to initialize the adventure map with the activities in each country and city
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

    // A method used to handle accurate currency coversions based on the person's preferred currency and their budget to spend in that country
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

    // A method used to include a list of restaurants in cities in morocco, other countries are included in the restaurant map
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

class Restaurant {
    private final String name;
    private final String pros;

    public Restaurant(String name, String pros) {
        this.name = name;
        this.pros = pros;
    }

    public String getName() {
        return name;
    }

    public String getPros() {
        return pros;
    }
}
