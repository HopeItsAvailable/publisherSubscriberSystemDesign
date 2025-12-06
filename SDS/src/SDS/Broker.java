package SDS;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The manager that acts as an intermediary between Publishers and Subscribers.
 * It maintains the state of available food items and pending subscriptions
 */
public class Broker {

    /**
     * This is required since the broker is a static class. Created according to
     * Design Patterns : Ch. 1 : Concepts and Techniques + Singleton Pattern, slides
     * 10-11
     */
    private static Broker uniqueInstance = new Broker();

    /**
     * Stores valid food items published by restaurants
     */
    private List<FoodItem> inventory;

    /**
     * Stores active subscriptions from diners waiting for a match
     */
    private List<DiningSubscriber> subscribers;

    /**
     * Stores the formatted notification strings to be sent to the Testing class
     */
    private List<String> notifications;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

    /**
     * Equivalent to getReference of slide 11 of singleton pattern slides. Returns
     * this broker.
     */
    public static Broker getInstance() {
        return uniqueInstance;
    }

    /**
     * Private constructor for the broker class. Put here to ensure it is private
     * and cannot be called elsewhere.
     */
    private Broker() {
        inventory = new ArrayList<>();
        subscribers = new ArrayList<>();
        notifications = new ArrayList<>();
    }

    /**
     * This will implement the logic of finding matches between the publisher and
     * subscriber.
     */
    // for reference
    // Subscribers = List<DiningSubscriber>
    // Inventory = List<FoodItem>
    private void processMatches() {
        // iterate all subscribers through all inventory and check for matches
        Iterator<DiningSubscriber> subIterator = subscribers.iterator();

        while (subIterator.hasNext()) {
            DiningSubscriber subscriber = subIterator.next(); // actual subscriber object
            Iterator<FoodItem> invIterator = inventory.iterator(); //reset iterator each time

            //add an initial pass for the cumulative fulfillment test cases, if it has enough food to fulfill the request from multiple
            //restaurants, then we go into inner loop, otherwise we skip to the next subscriber
            int totalAvailableFoodQty = 0;

            for (FoodItem food : inventory) {
                boolean nameMatch = subscriber.getDesiredFoodName().equalsIgnoreCase(food.getItemName());
                boolean locationMatch;
                boolean timeMatch;

                if (subscriber.getPreferredLocation() == null) {
                    locationMatch = true;
                } else {
                    locationMatch = subscriber.getPreferredLocation().equals(food.getLocation());
                }

                timeMatch = subscriber.getRequestStart().isBefore(food.getAvailabilityEnd())
                        && subscriber.getRequestEnd().isAfter(food.getAvailabilityStart());

                if (nameMatch && locationMatch && timeMatch) {
                    totalAvailableFoodQty += food.getQuantity();
                }
            }

            if (totalAvailableFoodQty < subscriber.getDesiredQuantity()) {
                continue;
            }

            while (invIterator.hasNext()) {
                FoodItem foodItem = invIterator.next(); // actual food item object

                boolean nameMatch = subscriber.getDesiredFoodName().equalsIgnoreCase(foodItem.getItemName());
                boolean locationMatch;
                boolean timeMatch;

                // check location match, location can be optional
                if (subscriber.getPreferredLocation() == null) {
                    locationMatch = true;
                } else {
                    locationMatch = subscriber.getPreferredLocation().equals(foodItem.getLocation());
                }

                // check time match. Logic: If sub.start < food.end and sub.end > food.start
                timeMatch = subscriber.getRequestStart().isBefore(foodItem.getAvailabilityEnd())
                        && subscriber.getRequestEnd().isAfter(foodItem.getAvailabilityStart());

                if (nameMatch && locationMatch && timeMatch) {
                    // match found
                    int amtToNotify = Math.min(subscriber.getDesiredQuantity(), foodItem.getQuantity());

                    // sample output format:
                    // John Doe is notified of 1 Grilled Chicken available at Tooker House in Tempe
                    // between 03/13/2025 11:00 and 03/13/2025 15:00.
                    String notification = String.format(
                            "%s is notified of %d %s available at %s in %s between %s and %s.",
                            subscriber.getDinerName(),
                            amtToNotify,
                            foodItem.getItemName(),
                            foodItem.getRestaurantName(),
                            foodItem.getLocation().toString(),
                            foodItem.getAvailabilityStart().format(formatter), //have to format
                            foodItem.getAvailabilityEnd().format(formatter)
                    );

                    notifications.add(notification); // add to notifications list for output

                    // update quantities
                    foodItem.quantity -= amtToNotify;
                    subscriber.setDesiredQuantity(subscriber.getDesiredQuantity() - amtToNotify); // eg if they wanted 3
                    // and got 1, now
                    // want 2
                    // remove food item if quantity is 0
                    if (foodItem.getQuantity() <= 0) {
                        invIterator.remove();
                    }
                    // remove subscriber if their desired quantity is 0
                    if (subscriber.getDesiredQuantity() <= 0) {
                        subIterator.remove();
                        break; // no need to check other food items for this subscriber, so go to next
                        // subscriber
                    }
                }
            }
        }
    }

    /**
     * Receives a food item, adds it to inventory, and checks for matches against
     * the subscriber list
     */
    public void publish(FoodItem item) {
        inventory.add(item);
        processMatches();
    }

    /**
     * Receives a subscriber, adds them to the list, and checks for matches against
     * the inventory
     */
    public void subscribe(DiningSubscriber subscriber) {
        subscribers.add(subscriber);
        processMatches();
    }

    /**
     * Returns the list of notification messages collected so far
     */
    public List<String> getAggregatedOutput() {
        return notifications;
    }

    /**
     * Clears the inventory, subscribers, and notifications to prepare for the next
     * test case
     */
    public void reset() {
        inventory.clear();
        subscribers.clear();
        notifications.clear();
    }

}
