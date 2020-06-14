

public abstract class Unit {
	
	private Tile position;
	private int availableRange;
	private double hp;
	private String faction; 



	

	public Unit(Tile position, double hp, int availableRange, String faction) {
		this.position = position;
		this.hp = hp;
		this.availableRange = availableRange;
		this.faction = faction;
		
		if (!this.position.addUnit(this)) { throw new IllegalArgumentException(); }
	
		
	}





	public final Tile getPosition () {
		return this.position;
	}
	
	public final double getHP() {
		return this.hp;
	}
	
	public final String getFaction() {
		return this.faction;
	}
	

	
	public boolean moveTo(Tile tile) {
		boolean moved = false;
		if ((Tile.getDistance(this.position,tile) < this.availableRange + 1) && tile.addUnit(this)) { 
			position.removeUnit(this);
			moved = true;
			this.position = tile;
		}
		return moved;	
	}
	
	
	public void receiveDamage(double damage) {
		if (position.isCity()) {
			damage = damage * 0.9;
		}
		hp = hp - damage;
		 
		if (hp <= 0) {
			position.removeUnit(this);
		}
	}
	
	public abstract void takeAction(Tile tile);
	
	
	//CHECK EQUALS
	public boolean equals(Object obj) {
		if ((Unit)obj instanceof Unit && ((Unit)obj).position.equals(this.position) &&
				((Unit)obj).faction.equals(this.faction) && ((Unit)obj).hp == this.hp){
				return true;
		}
			else	{
				return false;
				}
			}
}
	



	

