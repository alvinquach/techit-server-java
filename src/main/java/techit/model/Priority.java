package techit.model;

public enum Priority {
	
	NA		("NOT ASSIGNED"),
	LOW		("LOW"),
	MEDIUM	("MEDIUM"),
	HIGH	("HIGH");
	
	private String priorityValue;

	Priority(String priorityValue) {
		this.priorityValue = priorityValue;
	};
	
	public String getPriorityValue() {
		return priorityValue;
	}

}