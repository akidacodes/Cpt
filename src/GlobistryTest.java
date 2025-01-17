

/**
 * author: Omar Akida
 * date: 17/01/2025
 * Globirtsy testcases
 */
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GlobistryTest {

    @Test
    void testTraveler() {
        // Act
        Traveler traveler = new LeisureTraveler("Omar", 1200.0);

        // Assert
        assertNotNull(traveler, "Traveler object should not be null");
        assertEquals("Omar", traveler.getName(), "Traveler name should match");
        assertEquals(1200.0, traveler.getBudget(), "Traveler budget should match");

        // Arrange
        String name = "Omar";
        double budget = 1200.0;
    }

    @Test
    void testGetExchangeRate() {
        // Act
        double exchangeRate = Globistry.getExchangeRate("USD", "EUR");

        // Assert
        assertTrue(exchangeRate > 0, "Exchange rate should be greater than 0");
        assertEquals(0.85, exchangeRate, "Exchange rate for USD to EUR should match");

        // Arrange
        String fromCurrency = "USD";
        String toCurrency = "EUR";
    }

    @Test
    void testHandleActivitySelection() {
        // Act
        AdventureMap adventureMap = Globistry.initializeAdventureMap();
        List<String> activities = adventureMap.getActivities("Japan");

        // Assert
        assertNotNull(activities, "Activities list should not be null");
        assertFalse(activities.isEmpty(), "Activities list should not be empty");
        assertEquals("Cherry Blossom Festival, Tokyo", activities.get(0), "First activity should match");

        // Arrange
        String country = "Japan";
    }

    @Test
    void testHandleCitySelection() {
        // Act
        Map<String, List<String>> cities = Globistry.initializeAccommodations().get("morocco");

        // Assert
        assertNotNull(cities, "Cities should not be null for Morocco");
        assertTrue(cities.containsKey("Marrakech"), "Marrakech should be a key in Morocco");
        assertEquals(2, cities.get("Marrakech").size(), "Marrakech should have 2 accommodations");

        // Arrange
        String country = "morocco";
    }

    @Test
    void testHandleCurrencyConversion() {
        // Act
        List<CountryBudget> countryBudgets = Globistry.initializeCountryBudgets();
        CountryBudget japanBudget = countryBudgets.stream()
                .filter(cb -> cb.getCountryName().equalsIgnoreCase("Japan"))
                .findFirst()
                .orElse(null);

        double exchangeRate = Globistry.getExchangeRate("USD", japanBudget.getCurrencyCode());
        double convertedBudget = 1000 * exchangeRate;

        // Assert
        assertNotNull(japanBudget, "Japan budget should not be null");
        assertTrue(exchangeRate > 0, "Exchange rate should be valid");
        assertEquals(1000 * 110.15, convertedBudget, 0.01, "Converted budget should match expected value");

        // Arrange
        double budget = 1000;
    }

    @Test
    void testHandleTravelerType() {
        // Act
        String travelerType = "Leisure";
        boolean isLeisure = travelerType.equalsIgnoreCase("Leisure");

        // Assert
        assertTrue(isLeisure, "Traveler type should be identified as Leisure");

        // Arrange
        String expectedTravelerType = "Leisure";
    }

    @Test
    void testInitializeAccommodations() {
        // Act
        Map<String, Map<String, List<String>>> accommodations = Globistry.initializeAccommodations();

        // Assert
        assertNotNull(accommodations, "Accommodations should not be null");
        assertTrue(accommodations.containsKey("morocco"), "Morocco should be a key in accommodations");

        // Arrange
    }

    @Test
    void testInitializeAdventureMap() {
        // Act
        AdventureMap adventureMap = Globistry.initializeAdventureMap();

        // Assert
        assertNotNull(adventureMap, "Adventure map should not be null");
        assertTrue(adventureMap.hasActivities("Japan"), "Adventure map should contain activities for Japan");

        // Arrange
    }

    @Test
    void testInitializeCountryBudgets() {
        // Act
        List<CountryBudget> countryBudgets = Globistry.initializeCountryBudgets();

        // Assert
        assertNotNull(countryBudgets, "Country budgets should not be null");
        assertEquals(5, countryBudgets.size(), "There should be 5 country budgets");

        // Arrange
    }

    @Test
    void testInitializeRestaurants() {
        // Act
        List<Restaurant> restaurants = Globistry.initializeRestaurants("Marrakech");

        // Assert
        assertNotNull(restaurants, "Restaurants list should not be null");
        assertEquals(3, restaurants.size(), "There should be 3 restaurants for Marrakech");
        assertEquals("Dar Yacout: Traditional cuisine", restaurants.get(0).getName() + ": " + restaurants.get(0).getPros(),
                "First restaurant should match expected value");

        // Arrange
        String city = "Marrakech";
    }

    @Test
    void testMain() {
        // Act
        Exception exception = assertDoesNotThrow(() -> Globistry.main(new String[]{}), "Main method should not throw exceptions");

        // Assert
        assertNull(exception, "No exception should be thrown during main method execution");

        // Arrange
        String[] args = {};
    }

    @Test
    void testShowAffordableCountries() {
        // Act
        List<String> affordableCountries = Globistry.initializeCountryBudgets().stream()
                .filter(cb -> cb.getFlightBudget() <= 800)
                .map(CountryBudget::getCountryName)
                .toList();

        // Assert
        assertNotNull(affordableCountries, "Affordable countries should not be null");
        assertTrue(affordableCountries.contains("Morocco"), "Morocco should be affordable");
        assertFalse(affordableCountries.contains("Japan"), "Japan should not be affordable");

        // Arrange
        double budget = 800;
    }
}