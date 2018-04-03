package techit.model;

public enum Status {
	
	OPEN		("OPEN"),
	INPROGRESS	("IN PROGRESS"),
	ONHOLD		("ON HOLD"),
	COMPLETED	("COMPLETED"),
	CLOSED		("CLOSED");

	private String value;

	private Status(String value) {
		this.value = value;
	};
	
	public String getValue() {
		return value;
	}
	
}