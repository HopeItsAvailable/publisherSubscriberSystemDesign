package SDS.src.SDS;
import java.time.LocalDateTime;

/**
 * This class represents a medication with its essential details, including its name, type, available quantity, and availability time window.Represents the availability of food at a restaurant location.
 */
public class FoodItem {

	/**
	 * Unique name given to a restaurant
	 */
	public final String restaurantName;

	/**
	 * Name of food item
	 */
	private final String itemName;

	/**
	 * Number of food items
	 */
	protected int quantity = 0;

	/**
	 * Unique five digit ID
	 */
	final String id = "00000";

	private LocalDateTime availabilityStart;

	private LocalDateTime availabilityEnd;
	
	private CampusLocation location;

	/**
	 * This is a constructor for a foodItem instance. 
	 */
	public FoodItem(String name, int quantity, LocalDateTime start, LocalDateTime end, String restaurant, CampusLocation location) {
		this.itemName = name;
        this.quantity = quantity;
        this.availabilityStart = start;
        this.availabilityEnd = end;
        this.restaurantName = restaurant;
        this.location = location;
	}

	public String getItemName() {
		return itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public LocalDateTime getAvailabilityStart() {
		return availabilityStart;
	}

	public LocalDateTime getAvailabilityEnd() {
		return availabilityEnd;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public CampusLocation getLocation() {
		return location;
	}

}
