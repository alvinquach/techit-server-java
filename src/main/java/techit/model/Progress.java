package techit.model;

public enum Progress {
	
	OPEN		("OPEN"),
	INPROGRESS	("IN PROGRESS"),
	ONHOLD		("ON HOLD"),
	COMPLETED	("COMPLETED"),
	CLOSED		("CLOSED");

	private String progressValue;

	Progress(String progressValue) {
		this.progressValue = progressValue;
	};
	
	public String getProgressValue() {
		return progressValue;
	}
	
}