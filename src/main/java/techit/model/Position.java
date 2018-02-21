package techit.model;

public enum Position {
	
	SYS_ADMIN,
	SUPERVISING_TECHNICIAN,
	TECHNICIAN,
	USER;

	// Leaving this in for now to not cause issues if/when
	// we transfer the logic from the old techit over.
	public int getValue() {
		return this.ordinal();
	}
	
}