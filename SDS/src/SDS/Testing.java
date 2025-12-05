package SDS.src.SDS;

import java.util.List;

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
	 * 
	 * publish, [restaurant name], [food item name], [quantity], [start time], [end time], [campus location]
	 * 
	 * subscribe, [diner name], [food item name], [quantity], [start time], [end time], [campus location]
	 */
	public void processInput(String input) {

	}

	/**
	 * Returns all notifications collected by the Broker
	 */
	public List<String> getAggregatedOutput() {
		return null;
	}

	/**
	 * Resets Broker and clears all data
	 */
	public void reset() {

	}

}
