

public class Archer extends MilitaryUnit{
	
	private int arrowsAvailable;

	public Archer(Tile position, double hp, String faction){

		super(position, hp, 2, faction, 15.0, 2, 0);
		arrowsAvailable = 5;
			
	}
	
	
	
	
	public void takeAction(Tile tile) {
		
		if (arrowsAvailable == 0) {
			arrowsAvailable = 5;
			return;
		}
			this.arrowsAvailable --;

			super.takeAction(tile);
		}
	
	
	public boolean equals(Object obj) {
		boolean equals = false;
		if (obj instanceof Archer && ((Archer)obj).arrowsAvailable == this.arrowsAvailable  && super.equals(obj)) {
			equals = true;
		}
		else {
			equals = false;
		}
		return equals;
	}
	

	
}
	

	

