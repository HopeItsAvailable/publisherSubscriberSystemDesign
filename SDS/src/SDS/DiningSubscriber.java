package SDS;
import java.time.LocalDateTime;

/**
 * DiningSubscriber represents a subscriber in the dining subscription system. Represents a diner who participates in the subscription process for a food item. This class implements the ISubscriber interface, providing a concrete definition of the subscription behavior.
 */
public class DiningSubscriber implements ISubscriber {

	private String desiredFoodName;

	/**
	 * We need the diner name for the output
	 */
	private String dinerName;

	private int desiredQuantity;

	private LocalDateTime requestStart;

	private LocalDateTime requestEnd;

	private CampusLocation preferredLocation;

	/**
	 * This is a constructor for a DiningSubscriber instance. 
	 */
	public DiningSubscriber(String dinerName, String foodName, int quantity, LocalDateTime start, LocalDateTime end, CampusLocation location) {
		this.dinerName = dinerName;
		this.desiredFoodName = foodName;
		this.desiredQuantity = quantity;
		this.requestStart = start;
		this.requestEnd = end;
		this.preferredLocation = location;
	}

	public String getDesiredFoodName() {
		return desiredFoodName;
	}

	public int getDesiredQuantity() {
		return desiredQuantity;
	}

	public LocalDateTime getRequestStart() {
		return requestStart;
	}

	public LocalDateTime getRequestEnd() {
		return requestEnd;
	}

	public CampusLocation getPreferredLocation() {
		return preferredLocation;
	}

	public String getDinerName() {
		return dinerName;
	}

	public void setDesiredQuantity(int quantity) {
        this.desiredQuantity = quantity;
    }


	/**
	 * @see Class_and_State_Machine_Diagrams.ISubscriber#subscribe(java.lang.String, int, LocalDateTime, LocalDateTime, Class_and_State_Machine_Diagrams.CampusLocation)
	 */
	@Override
	public void subscribe(String foodItemName, int quantity, LocalDateTime requestStart, LocalDateTime requestEnd, CampusLocation preferredCampusLocation) {
		this.desiredFoodName = foodItemName;
		this.desiredQuantity = quantity;
		this.requestStart = requestStart;
		this.requestEnd = requestEnd;
		this.preferredLocation = preferredCampusLocation;

		Broker.getInstance().subscribe(this); //only works because Broker is a singleton
	}

}
