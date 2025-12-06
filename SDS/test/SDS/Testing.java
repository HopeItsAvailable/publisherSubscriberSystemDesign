package SDS;

import java.util.List;
import java.time.LocalDateTime;

/**
 * Controller class that processes text commands to simulate publisher-subscriber interaction.
 * Uses a Broker instance to manage communication between DiningPublisher and DiningSubscriber.
 */
public class Testing {

    /**
     * Constructor without any arguments.
     */
    public Testing() {

    }

    /**
     * Processes a single input command string. The publish and subscribe commands are:
     * <p>
     * publish, [restaurant name], [food item name], [quantity], [start time], [end time], [campus location]
     * <p>
     * subscribe, [diner name], [food item name], [quantity], [start time], [end time], [campus location]
     */
    public void processInput(String input) {
        try {
            if (input == null || input.trim().isEmpty()) return;

            //seperate by commat and trim
            String[] parts = input.split(",");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }

            String command = parts[0];

            if ("publish".equalsIgnoreCase(command)) {
                //publish, [restaurant name], [food item name], [quantity], [start time], [end time], [campus location]
                if (parts.length != 7) return;

                String restaurant = parts[1];
                String food = parts[2];
                int quantity = Integer.parseInt(parts[3]);
                LocalDateTime start = LocalDateTime.parse(parts[4]);
                LocalDateTime end = LocalDateTime.parse(parts[5]);
                CampusLocation location = CampusLocation.valueOf(parts[6].toUpperCase());

                Restaurant r = new Restaurant(restaurant);
                r.publish(food, quantity, start, end, location);

            } else if ("subscribe".equalsIgnoreCase(command)) {
                //subscribe, [diner name], [food item name], [quantity], [start time], [end time], [campus location]
                if (parts.length < 6 || parts.length > 7) return;

                String diner = parts[1];
                String food = parts[2];
                int quantity = Integer.parseInt(parts[3]);
                LocalDateTime start = LocalDateTime.parse(parts[4]);
                LocalDateTime end = LocalDateTime.parse(parts[5]);

                CampusLocation location = null;
                if (parts.length == 7) {
                    location = CampusLocation.valueOf(parts[6].toUpperCase());
                }

                DiningSubscriber subscriber = new DiningSubscriber(diner, food, quantity, start, end, location);
                subscriber.subscribe(food, quantity, start, end, location);
            }
        } catch (Exception e) {
            //do nothing if an error is found
            return;
        }
    }

    /**
     * Returns all notifications collected by the Broker
     */
    public List<String> getAggregatedOutput() {
        return Broker.getInstance().getAggregatedOutput();
    }

    /**
     * Resets Broker and clears all data
     */
    public void reset() {
        Broker.getInstance().reset();
    }

}
