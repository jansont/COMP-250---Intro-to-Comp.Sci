

public class Worker extends Unit{
	
	private int jobsDone;

	

	public Worker(Tile position, double hp, String faction)  {
		super(position, hp, 2, faction );
		this.jobsDone  = 0;
		
	}
	
	public void takeAction(Tile tile) {
		if (tile.equals(getPosition()) && !tile.isImproved()) {
			tile.buildImprovement();
			jobsDone ++;
		}
		if (jobsDone == 10) {
			tile.removeUnit(this);
		}
	}


	
	

	
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof Worker && super.equals(obj) && this.jobsDone == ((Worker)obj).jobsDone) {
			equals = true;
		}
		else {
			equals = false;
		}
		return equals;
	}
	
	
}

	
	

