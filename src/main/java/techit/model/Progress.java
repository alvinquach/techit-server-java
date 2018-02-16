package techit.model;

public enum Progress {
			OPEN(0), INPROGRESS(1), ONHOLD(2), COMPLETED(3), CLOSED(4);

			private int progress;

			Progress(int progress) {
				this.progress = progress;
			};

			public String getProgressValue() {
				String progress = "";
				switch(this.progress)
				{
				case 0:
					progress = "OPEN";
					break;
				case 1:
					progress = "IN PROGRESS";
					break;
				case 2:
					progress = "ON HOLD";
					break;
				case 3:
					progress = "COMPLETED";
					break;
				case 4:
					progress = "CLOSED";
					break;
				}
				return progress;
			}

		};
		

