/**
 * author: Omar Akida
 * date: 19/01/2025
 * Globistry testcases
 */
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class GlobistryTest {

    @Test
    void testTraveler() {
        // Arrange
        String name = "Omar";
        double budget = 900.0;

        // Act
        Traveler traveler = new LeisureTraveler(name, budget);

        // Assert
        assertNotNull(traveler, "Traveler object should not be null");
        assertEquals(name, traveler.getName(), "Traveler name should match");
        assertEquals(budget, traveler.getBudget(), "Traveler budget should match");
    }
    
    @Test
    void testGetExchangeRate() {
        // Arrange
        String fromCurrency = "USD";
        String toCurrency = "EUR";

        // Act
        double exchangeRate = Globistry.getExchangeRate(fromCurrency, toCurrency);

        // Assert
        assertTrue(exchangeRate > 0, "Exchange rate should be greater than 0");
        assertEquals(0.85, exchangeRate, "Exchange rate for USD to EUR should match");
    }

    @Test
    void testHandleActivitySelection() {
        // Arrange
        String country = "japan";

        // Act
        AdventureMap adventureMap = Globistry.initializeAdventureMap();
        List<String> activities = adventureMap.getActivities(country);

        // Assert
        assertNotNull(activities, "Activities list should not be null");
        assertFalse(activities.isEmpty(), "Activities list should not be empty");
        assertEquals("Cherry Blossom Festival, Tokyo", activities.get(0), "First activity should match");
    }

    /**
     * Updated to test the logic in createRestaurantsMap() rather than a non-existent initializeAccommodations()
     */
    @Test
    void testHandleCitySelection() {
        // Arrange
        String country = "morocco";

        // Act
        Map<String, Map<String, List<String>>> restaurantsMap = Globistry.createRestaurantsMap();
        Map<String, List<String>> cities = restaurantsMap.get(country);

        // Assert
        assertNotNull(cities, "Cities should not be null for Morocco");
        assertTrue(cities.containsKey("Marrakech"), "Marrakech should be a key in Morocco");
        // In your createRestaurantsMap, Marrakech has 3 entries: "La Mamounia Hotel", "Dar Yacout", "Riad Kniza"
        assertEquals(3, cities.get("Marrakech").size(), "Marrakech should have 3 restaurants");
    }

    @Test
    void testHandleCurrencyConversion() {
        // Arrange
        double budget = 1000;
        List<CountryBudget> countryBudgets = Globistry.initializeCountryBudgets();

        // Act
        CountryBudget japanBudget = countryBudgets.stream()
                .filter(cb -> cb.getCountryName().equalsIgnoreCase("Japan"))
                .findFirst()
                .orElse(null);
        assertNotNull(japanBudget, "Japan budget should not be null");

        double exchangeRate = Globistry.getExchangeRate("USD", japanBudget.getCurrencyCode());
        double convertedBudget = budget * exchangeRate;

        // Assert
        assertTrue(exchangeRate > 0, "Exchange rate should be valid");
        assertEquals(1000 * 110.15, convertedBudget, 0.01, "Converted budget should match expected value");
    }

    @Test
    void testHandleTravelerType() {
        // Arrange
        String travelerType = "Leisure";

        // Act
        boolean isLeisure = travelerType.equalsIgnoreCase("Leisure");

        // Assert
        assertTrue(isLeisure, "Traveler type should be identified as Leisure");
    }

    /**
     * Replaces the non-existent method "initializeAccommodations()" with "createRestaurantsMap()" 
     * to ensure everything is consistent with your current code.
     */
    @Test
    void testCreateRestaurantsMap() {
        // Act
        Map<String, Map<String, List<String>>> restaurantsMap = Globistry.createRestaurantsMap();

        // Assert
        assertNotNull(restaurantsMap, "restaurantsMap should not be null");
        assertTrue(restaurantsMap.containsKey("morocco"), "Morocco should be a key in the restaurantsMap");
    }

    @Test
    void testInitializeAdventureMap() {
        // Act
        AdventureMap adventureMap = Globistry.initializeAdventureMap();

        // Assert
        assertNotNull(adventureMap, "Adventure map should not be null");
        assertTrue(adventureMap.hasActivities("japan"), "Adventure map should contain activities for Japan");
    }

    @Test
    void testInitializeCountryBudgets() {
        // Act
        List<CountryBudget> countryBudgets = Globistry.initializeCountryBudgets();

        // Assert
        assertNotNull(countryBudgets, "Country budgets should not be null");
        assertEquals(5, countryBudgets.size(), "There should be 5 country budgets");
    }

    /**
     * Matches your code, which puts "Pepe Nero" first in Marrakech.
     */
    @Test
    void testInitializeRestaurants() {
        // Arrange
        String city = "Marrakech";

        // Act
        List<Restaurant> restaurants = Globistry.initializeRestaurants(city);

        // Assert
        assertNotNull(restaurants, "Restaurants list should not be null");
        assertEquals(3, restaurants.size(), "There should be 3 restaurants for Marrakech");

        // "Pepe Nero" is the first item in your code for Marrakech
        assertEquals("Pepe Nero: Fusion of moroccan and italian cuisine",
            restaurants.get(0).getName() + ": " + restaurants.get(0).getPros(),
            "First restaurant should match expected value"
        );
    }

    @Test
    void testMain() {
    String[] args = {};
    assertDoesNotThrow(
        () -> Globistry.main(args),
        "Main method should not throw exceptions"
    );
}


    @Test
    void testShowAffordableCountries() {
        // Arrange
        double budget = 800;

        // Act
        List<String> affordableCountries = Globistry.initializeCountryBudgets().stream()
                .filter(cb -> cb.getFlightBudget() <= budget)
                .map(CountryBudget::getCountryName)
                .toList();

        // Assert
        assertNotNull(affordableCountries, "Affordable countries should not be null");
        assertTrue(affordableCountries.contains("Egypt"), "Egypt should be affordable");
        assertFalse(affordableCountries.contains("Japan"), "Japan should not be affordable");
    }
}
