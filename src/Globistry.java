import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Globistry {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Getting Travel Information
        System.out.println("Please enter your name: ");
        String name = input.nextLine();
        System.out.println(name + ", welcome to Globistry!");

        System.out.println("What type of traveler are you? (Business/Leisure): ");
        String travelerType = input.nextLine();
        if (travelerType.equalsIgnoreCase("Business") || travelerType.equalsIgnoreCase("B") ) {
            System.out.println(name + " you are a Business Traveler.");
        } else if (travelerType.equalsIgnoreCase("Leisure") || travelerType.equalsIgnoreCase("L")) {
            System.out.println(name + " you are a Leisure Traveler.");
        } else {
            System.out.println("Invalid traveler type.");
            input.close();
            return;
        }
        System.out.println("What's your flight budget?");
        double flightBudget = input.nextDouble();
        input.nextLine();
        
        Traveler traveler = null;
        if (travelerType.equalsIgnoreCase("Business") || travelerType.equalsIgnoreCase("B") ) {
            traveler = new BusinessTraveler(name, flightBudget);
        } else if (travelerType.equalsIgnoreCase("Leisure") || travelerType.equalsIgnoreCase("L")) {
            traveler = new LeisureTraveler(name, flightBudget);
        }
        // Available countries with flight budgets
        List<CountryBudget> countryBudgets = new ArrayList<>();
        countryBudgets.add(new CountryBudget("Morocco", 850));
        countryBudgets.add(new CountryBudget("Japan", 880));
        countryBudgets.add(new CountryBudget("Canada", 750));
        countryBudgets.add(new CountryBudget("UK", 700));
        countryBudgets.add(new CountryBudget("Germany", 750));

        System.out.println(name + ", based on your flight budget, you can travel to the following countries:");
        boolean countriesExist = false;
        for (CountryBudget cb : countryBudgets) {
            if (cb.getFlightBudget() <= flightBudget) {
                System.out.println(cb.getCountryName());
                countriesExist = true;
            }
        }

        if (!countriesExist) {
            System.out.println("Unfortunately, there are no countries available within your budget.");
            input.close();
            return;
        }

        System.out.println("Enter which country you'd prefer traveling to: ");
        String preferredCountry = input.nextLine();
        System.out.println("You are traveling to " + preferredCountry);

        // Getting accommodation details
        Map<String, Map<String, List<String>>> accommodationsMap = new HashMap<>();

        Map<String, List<String>> moroccoCities = new HashMap<>();
        moroccoCities.put("Marrakech", List.of("La Mamounia Hotel", "Luxury Riad Airbnb"));
        moroccoCities.put("Casablanca", List.of("Luxury Riad Airbnb"));
        moroccoCities.put("Rabat", List.of("Traditional House"));
        accommodationsMap.put("Morocco", moroccoCities);
        
        Map<String, List<String>> japanCities = new HashMap<>();
        japanCities.put("Tokyo", List.of("The Ritz-Carlton Hotel"));
        japanCities.put("Kyoto", List.of("Kyoto Machiya Airbnb"));
        japanCities.put("Osaka", List.of("Modern House"));
        accommodationsMap.put("Japan", japanCities);

        Map<String, List<String>> canadaCities = new HashMap<>();
        canadaCities.put("Toronto", List.of("Fairmont Royal York Hotel"));
        canadaCities.put("Vancouver", List.of("Downtown Airbnb"));
        canadaCities.put("Montreal", List.of("Charming House"));
        accommodationsMap.put("Canada", canadaCities);

        Map<String, List<String>> ukCities = new HashMap<>();
        ukCities.put("London", List.of("The Savoy Hotel"));
        ukCities.put("Edinburgh", List.of("Luxury Airbnb"));
        ukCities.put("York", List.of("Traditional House"));
        accommodationsMap.put("UK", ukCities);

        Map<String, List<String>> germanyCities = new HashMap<>();
        germanyCities.put("Munich", List.of("Bayerischer Hof Hotel"));
        germanyCities.put("Berlin", List.of("Stylish Airbnb"));
        germanyCities.put("Hamburg", List.of("Cozy House"));
        accommodationsMap.put("Germany", germanyCities);

        if (accommodationsMap.containsKey(preferredCountry)) {
            System.out.println("Which city will you be staying in?");
            String city = input.nextLine();

            if (accommodationsMap.get(preferredCountry).containsKey(city)) {
                System.out.println("Available accommodations in " + city + ": " + String.join(", ", accommodationsMap.get(preferredCountry).get(city)));
                System.out.println("Do you have a specific location to stay in? (Hotel, Airbnb, House):");
                String location = input.nextLine();

                if (accommodationsMap.get(preferredCountry).get(city).stream().anyMatch(acc -> acc.contains(location))) {
                    System.out.println("You have chosen to stay in a " + location + " in " + city);
                } else {
                    System.out.println("Sorry, the location you entered does not exist in the specified city.");
                }
            } else {
                System.out.println("Sorry, we don't have information for the city you entered.");
            }
        } else {
            System.out.println("Sorry, we don't have information for the country you entered.");
        }

        // Getting Currency Information
        System.out.println("Please enter your preferred currency (e.g., USD, EUR, JPY, GBP, CAD): ");
        String preferredCurrency = input.nextLine().toUpperCase();
        System.out.println("What is the budget in your preferred currency?");
        double budgetPreferredCurrency = input.nextDouble();
        input.nextLine();

        // Currency exchange rates
        Map<String, Double> exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0);  // Base currency
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("JPY", 110.15);
        exchangeRates.put("GBP", 0.75);
        exchangeRates.put("CAD", 1.27);

        double budgetInDestinationCurrency = 0.0;
        if (exchangeRates.containsKey(preferredCurrency)) {
            Double destinationRate = exchangeRates.get("USD");
            if (preferredCountry.equalsIgnoreCase("Japan")) {
                destinationRate = exchangeRates.get("JPY");
            } else if (preferredCountry.equalsIgnoreCase("Canada")) {
                destinationRate = exchangeRates.get("CAD");
            } else if (preferredCountry.equalsIgnoreCase("UK")) {
                destinationRate = exchangeRates.get("GBP");
            } else if (preferredCountry.equalsIgnoreCase("Germany")) {
                destinationRate = exchangeRates.get("EUR");
            }

            budgetInDestinationCurrency = budgetPreferredCurrency * destinationRate / exchangeRates.get(preferredCurrency);
            System.out.println("Your budget in " + preferredCountry + " currency is: " + budgetInDestinationCurrency);
        } else {
            System.out.println("Currency not supported.");
            input.close();
            return;
        }

        // AdventureMap for activities and restaurants
        AdventureMap adventureMap = new AdventureMap();
        adventureMap.addActivity("Morocco", "Desert Safari, Sahara");
        adventureMap.addActivity("Japan", "Cherry Blossom Festival, Tokyo");
        adventureMap.addActivity("Canada", "Hiking, Banff National Park");

        adventureMap.addRestaurant("Morocco", "Al Fassia, Marrakech");
        adventureMap.addRestaurant("Japan", "Sushi Saito, Tokyo");
        adventureMap.addRestaurant("Canada", "Joe Beef, Montreal");

        adventureMap.addActivity("UK", "Visit the British Museum, London");
        adventureMap.addActivity("Germany", "Oktoberfest, Munich");

        adventureMap.addRestaurant("UK", "The Ledbury, London");
        adventureMap.addRestaurant("Germany", "Viktualienmarkt, Munich");

        System.out.println("Adventure activities and restaurants in " + preferredCountry + ":");
        System.out.println("Activities available in " + preferredCountry + ": "
                + String.join(", ", adventureMap.getActivities(preferredCountry)));
        System.out.println("Restaurants available in " + preferredCountry + ": "
                + String.join(", ", adventureMap.getRestaurants(preferredCountry)));

        input.close();
    }
}

// CountryBudget class
class CountryBudget {
    private String countryName;
    private double flightBudget;

    public CountryBudget(String countryName, double flightBudget) {
        this.countryName = countryName;
        this.flightBudget = flightBudget;
    }

    public String getCountryName() {
        return countryName;
    }

    public double getFlightBudget() {
        return flightBudget;
    }
}


// Traveler class
 class Traveler {
    private String name;
    private double budget;

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

// AdventureMap class
class AdventureMap {
    private Map<String, List<String>> activitiesMap = new HashMap<>();
    private Map<String, List<String>> restaurantsMap = new HashMap<>();

    public void addActivity(String country, String activity) {
        activitiesMap.computeIfAbsent(country, k -> new ArrayList<>()).add(activity);
    }

    public void addRestaurant(String country, String restaurant) {
        restaurantsMap.computeIfAbsent(country, k -> new ArrayList<>()).add(restaurant);
    }

    public List<String> getActivities(String country) {
        return activitiesMap.getOrDefault(country, new ArrayList<>());
    }

   public List<String> getRestaurants(String country) {
        return restaurantsMap.getOrDefault(country, new ArrayList<>());
    }
}
