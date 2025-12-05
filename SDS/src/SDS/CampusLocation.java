package SDS.src.SDS;

/**
 * Enumeration representing all the restaurant locations.
 */
public enum CampusLocation {

	TEMPE("Tempe"),

	POLYTECHNIC("Polytechnic"),

	PHOENIX("Phoenix"),

	WEST("West");

	private final String displayName;

	CampusLocation(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static CampusLocation fromString(String text) {
		if (text == null) {
			return null;
		}
		for (CampusLocation loc : CampusLocation.values()) {
			// match the display name (case-insensitive in code)
			if (loc.displayName.equalsIgnoreCase(text)) {
				return loc;
			}
		}
		throw new IllegalArgumentException("No CampusLocation for: " + text);
	}

	@Override
	public String toString() {
		return displayName;
	}

}
