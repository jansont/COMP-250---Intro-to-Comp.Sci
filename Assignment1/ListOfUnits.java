
public class ListOfUnits {
	
	private Unit[] unitList;
	
	private int unitListSize; 
	

	public ListOfUnits() {
		this.unitList = new Unit[10];
		this.unitListSize = 0; 
		}
	
	
	public int size(){
		return unitListSize;
	}
	
	public Unit[] getUnits() {
		
		Unit[] noNullUnitList = new Unit[size()];
		for(int i= 0; i < unitListSize; i++) {
			noNullUnitList[i] = unitList[i];	
		}
		return noNullUnitList;
	}
	
	

	public Unit get(int unitAt)  {
		if (unitAt < 0 || unitAt >= unitListSize) { 
			throw new  IndexOutOfBoundsException("Index out of Bounds!");
		}
		else {
			return unitList[unitAt];
		}
	}
		
	
	
	public void add(Unit newUnit) {
		if (unitListSize >= unitList.length) {
			int newCapacity = unitList.length + unitList.length/2 + 1;
			Unit[] extendedUnitList = new Unit[newCapacity];
			
			for (int i = 0; i < newCapacity; i++) { 
				extendedUnitList[i] = unitList[i];
			}
			unitList = extendedUnitList;
		}
			
		unitList[unitListSize] = newUnit;
		unitListSize++;
		}
		
	
	
	
	
	   public int indexOf(Unit unitAt) {
	        for(int i=0; i<unitListSize; i++) {
	            if(unitAt.equals(unitList[i])) {
	                return i;
	            }
	        }
	        return -1;
	    }
	
	
	
	public boolean remove(Unit unit) {
		int index = indexOf(unit);
		if(index == -1) {
			return false;
		}
		else {
			for(int i = index; i< unitListSize-1;i++) {
				if (unitList[i].equals(unit)){
					for (int j = i; j < unitListSize - 1 ; j++) {
						unitList[j]= unitList[j+1];
			}
			unitList[unitListSize-1] = null;
			unitListSize --;
			return true;
				}	
				
			}
		}
		return false;
	}
	
	
	
			
	
	
	public MilitaryUnit[] getArmy() {
		int numberMilitary = 0;
		int militaryCount = 0;
		
		for (int j = 0; j< size(); j++) {
			if (this.unitList[j] instanceof MilitaryUnit) {
				numberMilitary ++;
			}
		}
		
		MilitaryUnit[] militaryList= new MilitaryUnit[numberMilitary];	
		
		for(int i= 0; i < unitListSize; i++) {
			if (unitList[i] instanceof MilitaryUnit) {
				militaryList[militaryCount] = (MilitaryUnit) unitList[i];
				militaryCount++;
			}
		}
		
		return militaryList;
	}
		
	
	

}

