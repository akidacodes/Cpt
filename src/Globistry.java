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
        if (travelerType.equalsIgnoreCase("Business") || travelerType.equalsIgnoreCase("B")) {
            System.out.println(name + " you are a Business Traveler.");
            
            // Business traveler specific questions
            System.out.println("What is your job?");
            String job = input.nextLine();

            System.out.println("Which company are you traveling for?");
            String company = input.nextLine();

            System.out.println("What is the purpose of your business trip?");
            String purpose = input.nextLine();

            // You can store this info in the BusinessTraveler object or just display it for now.
            System.out.println("Job: " + job);
            System.out.println("Company: " + company);
            System.out.println("Purpose: " + purpose);

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
        if (travelerType.equalsIgnoreCase("Business") || travelerType.equalsIgnoreCase("B")) {
            traveler = new BusinessTraveler(name, flightBudget);
        } else if (travelerType.equalsIgnoreCase("Leisure") || travelerType.equalsIgnoreCase("L")) {
            traveler = new LeisureTraveler(name, flightBudget);
        }

        // Available countries with flight budgets
        List<CountryBudget> countryBudgets = new ArrayList<>();
        countryBudgets.add(new CountryBudget("Morocco", 850, "MAD"));
        countryBudgets.add(new CountryBudget("Japan", 880, "JPY"));
        countryBudgets.add(new CountryBudget("Egypt", 750, "EGP"));
        countryBudgets.add(new CountryBudget("UK", 700, "GBP"));
        countryBudgets.add(new CountryBudget("Germany", 750, "EUR"));

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
        String preferredCountry = input.nextLine().toLowerCase();  // Make input lowercase
        System.out.println("You are traveling to " + preferredCountry);

        // Getting accommodation details
        Map<String, Map<String, List<String>>> accommodationsMap = new HashMap<>();

        Map<String, List<String>> moroccoCities = new HashMap<>();
        moroccoCities.put("Marrakech", List.of("La Mamounia Hotel", "Luxury Riad Airbnb"));
        moroccoCities.put("Casablanca", List.of("Luxury Riad Airbnb"));
        moroccoCities.put("Rabat", List.of("Traditional House"));
        accommodationsMap.put("morocco", moroccoCities);

        Map<String, List<String>> japanCities = new HashMap<>();
        japanCities.put("Tokyo", List.of("The Ritz-Carlton Hotel"));
        japanCities.put("Kyoto", List.of("Kyoto Machiya Airbnb"));
        japanCities.put("Osaka", List.of("Modern House"));
        japanCities.put("Hokkaido", List.of("Forest Spa Resort Hotel"));
        accommodationsMap.put("japan", japanCities);

        Map<String, List<String>> egyptCities = new HashMap<>();
        egyptCities.put("Cairo", List.of("Luxury Nile Cruise Hotel", "Cairo Downtown Airbnb"));
        egyptCities.put("Alexandria", List.of("Beachfront Resort Hotel", "Charming House"));
        egyptCities.put("Luxor", List.of("Ancient Palace Airbnb"));
        accommodationsMap.put("egypt", egyptCities);

        Map<String, List<String>> ukCities = new HashMap<>();
        ukCities.put("London", List.of("The Savoy Hotel"));
        ukCities.put("Edinburgh", List.of("Luxury Airbnb"));
        ukCities.put("York", List.of("Traditional House"));
        accommodationsMap.put("uk", ukCities);

        Map<String, List<String>> germanyCities = new HashMap<>();
        germanyCities.put("Munich", List.of("Bayerischer Hof Hotel"));
        germanyCities.put("Berlin", List.of("Stylish Airbnb"));
        germanyCities.put("Hamburg", List.of("Cozy House"));
        accommodationsMap.put("germany", germanyCities);

        if (accommodationsMap.containsKey(preferredCountry)) {
            System.out.println("Which city will you be staying in?");
            Map<String, List<String>> cities = accommodationsMap.get(preferredCountry);
            System.out.print("Available cities: ");
            System.out.println("[" + String.join(", ", cities.keySet()) + "]");  // Display cities in brackets
            String city = input.nextLine();

            if (cities.containsKey(city)) {
                System.out.println("Available accommodations in " + city + ": "
                        + String.join(", ", cities.get(city)));
                System.out.println("Do you have a specific location to stay in? (Hotel, Airbnb, House):");
                String location = input.nextLine();

                if (cities.get(city).stream()
                        .anyMatch(acc -> acc.contains(location))) {
                    System.out.println("You have chosen to stay in a " + location + " in " + city);
                } else {
                    System.out.println("Sorry, the location you entered does not exist in the specified city.");
                }
            } else {
                System.out.println("Sorry, we don't have information for the city you entered.");
            }

            // Ask for activity preference
            System.out.println("Which activity would you prefer doing?");
            AdventureMap adventureMap = new AdventureMap();
            adventureMap.addActivity("morocco", "Desert Safari, Sahara");
            adventureMap.addActivity("japan", "Cherry Blossom Festival, Tokyo");
            adventureMap.addActivity("egypt", "Pyramids of Giza Tour, Cairo");

            adventureMap.addRestaurant("morocco", "Al Fassia, Marrakech");
            adventureMap.addRestaurant("japan", "Sushi Saito, Tokyo");
            adventureMap.addRestaurant("egypt", "Abou El Sid, Cairo");

            adventureMap.addActivity("uk", "Visit the British Museum, London");
            adventureMap.addActivity("germany", "Oktoberfest, Munich");

            adventureMap.addRestaurant("uk", "The Ledbury, London");
            adventureMap.addRestaurant("germany", "Viktualienmarkt, Munich");

            System.out.print("Available activities: ");
            System.out.println("[" + String.join(", ", adventureMap.getActivities(preferredCountry)) + "]");  // Display activities in brackets
            String activity = input.nextLine();

            if (adventureMap.getActivities(preferredCountry).contains(activity)) {
                System.out.println("You have chosen to do: " + activity);
            } else {
                System.out.println("Sorry, the activity you entered does not exist.");
            }
        } else {
            System.out.println("Sorry, we don't have information for the country you entered.");
        }

        // Currency conversion
        System.out.println("Please enter your preferred currency (e.g., USD, EUR, JPY, GBP, CAD): ");
        String preferredCurrency = input.nextLine().toUpperCase();
        System.out.println("What is the budget in your preferred currency?");
        double budgetPreferredCurrency = input.nextDouble();
        input.nextLine();

        // Currency exchange rates for each country
        Map<String, Double> exchangeRates = new HashMap<>();
        exchangeRates.put("USD", 1.0); // Base currency for USD
        exchangeRates.put("EUR", 0.85); // Euro
        exchangeRates.put("JPY", 110.15); // Japanese Yen
        exchangeRates.put("GBP", 0.75); // British Pound
        exchangeRates.put("CAD", 1.27); // Canadian Dollar
        exchangeRates.put("MAD", 10.0); // Moroccan Dirham
        exchangeRates.put("EGP", 30.0); // Egyptian Pound

        // Convert to the local currency of the destination country
        double budgetInDestinationCurrency = 0.0;
        CountryBudget selectedCountry = countryBudgets.stream()
                .filter(cb -> cb.getCountryName().equalsIgnoreCase(preferredCountry))
                .findFirst()
                .orElse(null);

        if (selectedCountry != null && exchangeRates.containsKey(preferredCurrency)) {
            Double destinationRate = exchangeRates.get(selectedCountry.getCurrencyCode()); // Destination country rate
            budgetInDestinationCurrency = budgetPreferredCurrency * destinationRate / exchangeRates.get(preferredCurrency);
            System.out.println("Your new budget in " + preferredCountry + " (" + selectedCountry.getCurrencyCode() + "): " + budgetInDestinationCurrency);
        } else {
            System.out.println("Currency not supported or country not found.");
            input.close();
            return;
        }

        input.close();
    }
}

// CountryBudget class
class CountryBudget {
    private String countryName;
    private double flightBudget;
    private String currencyCode;

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
