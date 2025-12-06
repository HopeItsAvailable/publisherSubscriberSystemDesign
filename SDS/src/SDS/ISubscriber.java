package SDS;
import java.time.LocalDateTime;


/**
 * ISubscriber interface defines a contract for objects that can subscribe to a specific food item. Any class implementing this interface must provide its own way of handling subscriptions.
 */
public abstract interface ISubscriber {

	/**
	 * A method signature for a patron to subscribe to food items potentially at a specific campus location.
	 */
	public abstract void subscribe(String foodItemName, int quantity, LocalDateTime requestStart, LocalDateTime requestEnd, CampusLocation preferredCampusLocation);

}
