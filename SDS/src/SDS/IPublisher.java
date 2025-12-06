package SDS;
import java.time.LocalDateTime;


/**
 * Defines a publishing entity that can announce food item availability. The IPublisher interface defines a contract for entities that publish information on food item availability. Any class implementing this interface must provide its own way of handling the publication process.
 */
public abstract interface IPublisher {

	/**
	 * A method signature for restaurants to publish food items at one or more campus locations.
	 */
	public abstract void publish(String itemName, int quantity, LocalDateTime availabilityStart, LocalDateTime availabilityEnd, CampusLocation location);

}
