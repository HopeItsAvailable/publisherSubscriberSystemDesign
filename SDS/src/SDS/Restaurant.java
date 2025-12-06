package SDS;
import java.time.LocalDateTime;

/**
 * Restaurant is responsible for publishing menu item availability from a specific dining location. This class represents a restaurant that publishes a food item. This class implements the IPublisher interface, meaning it provides a concrete definition of the publishing process for food item availability.
 */
public class Restaurant implements IPublisher {

	private String name;

	public Restaurant(String name) {
		this.name = name;
	}


	/**
	 * @see Class_and_State_Machine_Diagrams.IPublisher#publish(java.lang.String, int, LocalDateTime, LocalDateTime, Class_and_State_Machine_Diagrams.CampusLocation)
	 */
	@Override
	public void publish(String itemName, int quantity, LocalDateTime availabilityStart, LocalDateTime availabilityEnd, CampusLocation location) {
		FoodItem foodItem = new FoodItem(itemName, quantity, availabilityStart, availabilityEnd, this.name, location);
		Broker.getInstance().publish(foodItem); //only works because Broker is a singleton
	}

}
