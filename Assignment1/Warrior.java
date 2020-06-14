
public class Warrior extends MilitaryUnit {
	

	public Warrior(Tile position, double hp, String faction)  {
		super(position, hp, 1, faction, 20.0, 1, 25);
		
		
	}


	
	public boolean equals(Object obj) {
	
		if (obj instanceof Warrior  && super.equals(obj)){
			return true;
		}
		else {
		return false;
		}
	}

	
}

	


