
public class Settler extends Unit{
	
	
	public Settler(Tile position, double hp, String faction) {
		super(position, hp, 2, faction); 
	}
	


	public void takeAction(Tile tile) {
		if ((this.getPosition().equals(tile)) && !(tile.isCity())){
			tile.foundCity();
			tile.removeUnit(this);
		}
	}

	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof Settler && super.equals(obj)) {
			equals = true;
		}
		else {
			equals = false;
		}
		return equals;
	}
	
	
	
}

