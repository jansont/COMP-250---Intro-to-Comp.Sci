public abstract class MilitaryUnit extends Unit{
	
	private double attackDamage ;
	private int attackRange ;
	private int armour;
	
	
	
	public MilitaryUnit(Tile position, double hp, int availableRange, String faction, double attackDamage, int attackRange, int armour)  {
		super(position, hp, availableRange, faction);
		this.attackDamage = attackDamage;
		this.attackRange = attackRange;
		this.armour = armour;
		
		
	}
	
	
	public void takeAction(Tile tile) {
		Unit weakestUnit = tile.selectWeakEnemy(getFaction());
		if(weakestUnit == null) {
			return;
		}
		
		
		if (tile.isImproved()) {
			attackDamage = attackDamage * 1.05;
		}
		if (Tile.getDistance(getPosition(),tile) < (attackRange +1)) {
			weakestUnit.receiveDamage(attackDamage);
		}
	}
		
		
	public void receiveDamage(double damage) {
		double multiplier = (double) 100/(100+armour);
		damage = damage * multiplier;
		super.receiveDamage(damage);
		}
	
	
	
}
