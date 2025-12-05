/*
 * CSE 460 Fall 2025
 * 
 * Five Sample Test cases: 1, 2, 3, 4, 5 
 *      Three Sample ungraded test cases: 1, 2, 5
 *      Two Sample graded test cases: 3, 4
 */

package SDS;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Comprehensive JUnit test suite for the Dining Subscription System.
 * Tests use case-insensitive comparison to match expected output format.
 */
public class CSE460SDSTestsF2025 {

    private Testing testing;

    @Before
    public void setUp() {
        testing = new Testing();
    }

    @After
    public void tearDown() {
        testing.reset();
    }

    /** 1. Basic single-location matching */
    @Test
    public void testBasicPublishSubscribe() {
        List<String> expected = Arrays.asList(
            "John Doe is notified of 1 Grilled Chicken available at Pizza Hut in Tempe between 03/13/2025 11:00 and 03/13/2025 15:00."
        );

        testing.processInput("publish, Pizza Hut, Grilled Chicken, 25, 03/13/2025 11:00, 03/13/2025 15:00, Tempe");
        testing.processInput("subscribe, John Doe, Grilled Chicken, 1, 03/13/2025 12:00, 03/13/2025 14:00, Tempe");

        List<String> actual = testing.getAggregatedOutput().stream()
                .map(String::strip)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        expected = expected.stream().map(String::toLowerCase).collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    /** 2. Subscriber provides no preferred location — match across all available ones */
    @Test
    public void testNoPreferredLocationProvided() {
        List<String> expected = Arrays.asList(
            "John Doe is notified of 5 Sushi available at Barrett Dining in Polytechnic between 03/13/2025 11:00 and 03/13/2025 15:00.",
            "John Doe is notified of 4 Sushi available at West Cafe in West between 03/13/2025 11:00 and 03/13/2025 15:00.",
            "John Doe is notified of 6 Sushi available at Phoenix Deli in Phoenix between 03/13/2025 11:00 and 03/13/2025 15:00."
        );

        testing.processInput("publish, Barrett Dining, Sushi, 5, 03/13/2025 11:00, 03/13/2025 15:00, Polytechnic");
        testing.processInput("publish, West Cafe, Sushi, 4, 03/13/2025 11:00, 03/13/2025 15:00, West");
        testing.processInput("publish, Phoenix Deli, Sushi, 6, 03/13/2025 11:00, 03/13/2025 15:00, Phoenix");
        testing.processInput("subscribe, John Doe, Sushi, 15, 03/13/2025 12:00, 03/13/2025 14:00");

        List<String> actual = testing.getAggregatedOutput().stream()
                .map(String::strip)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        expected = expected.stream().map(String::toLowerCase).collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    /** 3. Time window overlap scenarios */
    @Test
    public void testTimeWindowOverlap() {
        List<String> expected = Arrays.asList(
            "Carol Johnson is notified of 1 Grilled Chicken available at Tooker House in Tempe between 03/16/2025 12:00 and 03/16/2025 14:00."
        );
        testing.processInput("publish, Tooker House, Grilled Chicken, 10, 03/13/2025 11:00, 03/13/2025 12:00, Tempe");
        testing.processInput("subscribe, John Doe, Grilled Chicken, 1, 03/13/2025 12:30, 03/13/2025 13:00, Tempe");
        testing.processInput("publish, Tooker House, Grilled Chicken, 10, 03/14/2025 14:00, 03/14/2025 15:00, Tempe");
        testing.processInput("subscribe, Jane Doe, Grilled Chicken, 1, 03/14/2025 12:00, 03/14/2025 13:00, Tempe");
        testing.processInput("publish, Tooker House, Grilled Chicken, 10, 03/15/2025 13:00, 03/15/2025 14:00, Tempe");
        testing.processInput("subscribe, Bob Smith, Grilled Chicken, 1, 03/15/2025 12:00, 03/15/2025 13:00, Tempe");
        testing.processInput("publish, Tooker House, Grilled Chicken, 10, 03/16/2025 12:00, 03/16/2025 14:00, Tempe");
        testing.processInput("subscribe, Carol Johnson, Grilled Chicken, 1, 03/16/2025 12:30, 03/16/2025 13:30, Tempe");

        List<String> actual = testing.getAggregatedOutput().stream()
                .map(String::strip)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        expected = expected.stream().map(String::toLowerCase).collect(Collectors.toList());
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    /** 4. Partial fulfillment when quantity exceeds single publisher availability */
    // No notification should be sent because subscriber requests 3 but only 2 are available
    @Test
    public void testInsufficientQuantity() {
        List<String> expected = Arrays.asList();

        testing.processInput("publish, Barrett Dining, Sandwich, 2, 03/13/2025 11:00, 03/13/2025 15:00, Polytechnic");
        testing.processInput("subscribe, Jane Doe, Sandwich, 3, 03/13/2025 12:00, 03/13/2025 14:00, Polytechnic");

        List<String> actual = testing.getAggregatedOutput().stream()
                .map(String::strip)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        expected = expected.stream().map(String::toLowerCase).collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    /** 5. Matches all locations when subscriber omits preferred location */
    @Test
    public void testAllLocations() {
        List<String> expected = Arrays.asList(
            "John Doe is notified of 20 Burger available at Tooker House in Tempe between 03/13/2025 11:00 and 03/13/2025 15:00."
        );

        testing.processInput("publish, Tooker House, Burger, 20, 03/13/2025 11:00, 03/13/2025 15:00, Tempe");
        testing.processInput("publish, Barrett Dining, Burger, 20, 03/13/2025 11:00, 03/13/2025 15:00, Polytechnic");
        testing.processInput("publish, West Cafe, Burger, 20, 03/13/2025 11:00, 03/13/2025 15:00, West");
        testing.processInput("publish, Phoenix Deli, Burger, 20, 03/13/2025 11:00, 03/13/2025 15:00, Phoenix");
        testing.processInput("subscribe, John Doe, Burger, 20, 03/13/2025 12:00, 03/13/2025 14:00");

        List<String> actual = testing.getAggregatedOutput().stream()
                .map(String::strip)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        expected = expected.stream().map(String::toLowerCase).collect(Collectors.toList());

        assertEquals(expected, actual);
    }

}