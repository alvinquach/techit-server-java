package techit.model;

public enum Position {
	SYS_ADMIN(0), SUPERVISING_TECHNICIAN(1), TECHNICIAN(2), USER(3);
	private int position;
	
	Position(int position){
		this.position = position;
	}
	
	public String getPositionValue(){
		String position = "";
		switch(this.position){
		case 0:
			position = "SYS_ADMIN";
			break;
		case 1:
			position = "SUPERVISING_TECHNICIAN";
			break;
		case 2:
			position = "TECHNICIAN";
			break;
		case 3:
			position = "USER";
			break;
		}
		return position;
	}
	
	public int getPositionNumericValue(){
		return this.position;
	}
}