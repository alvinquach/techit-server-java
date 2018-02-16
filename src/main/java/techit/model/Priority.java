package techit.model;

public enum Priority {
	NA(0), LOW(1), MEDIUM(2), HIGH(3);
	private int priority;
	
	Priority(int priority){
		this.priority = priority;
	}
	
	public String getPriorityValue(){
		String priority = "";
		switch(this.priority){
		case 0:
			priority = "NOT ASSIGNED";
			break;
		case 1:
			priority = "LOW";
			break;
		case 2:
			priority = "MEDIUM";
			break;
		case 3:
			priority = "HIGH";
			break;
		}
		return priority;
	}
	
	public int getPriorityNumericValue(){
		return this.priority;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
}