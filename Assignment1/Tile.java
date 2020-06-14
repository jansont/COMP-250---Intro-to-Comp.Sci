
public class Tile {
	
	private int xLocation;
	private int yLocation;
	private boolean cityBuilt;
	private boolean tileImproved;
	private ListOfUnits unitsPresent;
	
	public Tile(int xLocation, int yLocation) {
		this.xLocation = xLocation;
		this.xLocation = xLocation;
		cityBuilt = false;
		tileImproved = false;
		this.unitsPresent = new ListOfUnits();
	}
	
	
	
	public int getX() {
		return this.xLocation;
	}
	
	public int getY() {
		return this.yLocation;
	}
	
	public boolean isCity() {
		return this.cityBuilt;
		
	}
	public boolean isImproved() {
		return this.tileImproved;
	}
	public void foundCity() {
		this.cityBuilt = true;
	}
	public void buildImprovement() {
		this.tileImproved = true;
	}
	
	
	public boolean addUnit(Unit unit) {
		if (unit instanceof MilitaryUnit) {
				if (otherArmyAbsent(unit)) { 
					unitsPresent.add(unit);
					return  true;
				}	
			}
		else {
			unitsPresent.add(unit);
			return true;
			}	
		return  false;
	}
	

	public boolean removeUnit(Unit unitRemove) {
		boolean removed =  false;
		for (int i=0; i<unitsPresent.size(); i++) {
	
			if (unitsPresent  == null) {
			removed = false;	
			}
		
			else if (!(unitRemove.equals(unitsPresent.getUnits()[i]))){
			removed =  false;
			}			
			else  {
				removed = this.unitsPresent.remove(unitRemove);
			}
		}
		return  removed;
	}
	
	
	
	public Unit selectWeakEnemy(String myFactionString) {
		Unit weakestUnit = null;
		for (int i = 0; i < unitsPresent.getUnits().length; i++) {
			
			if (!(unitsPresent.getUnits()[i].getFaction().equals(myFactionString))){
				if (unitsPresent.getUnits()[i].getHP() < weakestUnit.getHP()  || weakestUnit == null ) {
					weakestUnit =  unitsPresent.getUnits()[i];
					}
				}
			}
		return weakestUnit;

		}
	

	
	public static double getDistance(Tile tile1, Tile tile2) {
		return Math.sqrt(Math.pow((tile1.xLocation - tile2.xLocation),2) + Math.pow((tile1.yLocation-tile2.yLocation),2));
		
		
	}
	
	
	

	private boolean otherArmyAbsent(Unit unit) {
		boolean otherArmyAbsent = false;
		for (int i = 0; i < unitsPresent.getArmy().length; i++) {
			if (unit.getFaction().equals(unitsPresent.getArmy()[i].getFaction()) || unitsPresent.getArmy()[i] == null) {
				otherArmyAbsent = true;
			}
		}
		return otherArmyAbsent;
	}
	
	
	
	
}
