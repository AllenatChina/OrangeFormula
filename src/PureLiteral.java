import java.util.*;

public class PureLiteral {
	public  static List<int[]> clauses;
	static List<Integer> PureLiterals = new ArrayList<Integer>();
	public static void main(String[] args) {
		
		Set<Integer> set = new HashSet<Integer>();		
		Clauses resClauses = Clauses.generateClause();
		UnitPro unitpro = new UnitPro(resClauses.clauses);
    	unitpro.collectUnitLit();
    	unitpro.removeClause();  	
    	
		clauses =unitpro.clauses;    	
		for(int i = 0 ;i < unitpro.clauses.size() ; i++){  System.out.println(Arrays.toString(unitpro.clauses.get(i))); 
			for (int j=0;j<unitpro.clauses.get(i).length;j++){
				set.add((unitpro.clauses.get(i)[j]));
				}
			        
		}
		
		Iterator<Integer> it = set.iterator();
		while(it.hasNext()) { 
		Integer Variable = it.next();
				 if (!(set.contains(Variable*-1))){
					 PureLiterals.add(Variable);				 
				 }
		}
		 RemoveClausesWithPureLiterals();
	}
	
	public static void RemoveClausesWithPureLiterals(){	
		for (int i=0;i<PureLiterals.size();i++){
			Iterator<int[]> it = clauses.iterator();
			while(it.hasNext()){
				int[] clause = it.next();	
				if(contains(clause,PureLiterals.get(i))){
						it.remove();
				}   
			}
		}
		//PureLiterals:-
		System.out.println("\nPureLiterals:-");
		for(int i = 0 ;i < PureLiterals.size() ; i++){  
			System.out.println(PureLiterals.get(i)); 
		}
				
		//removed clauses:-
		System.out.println("\nClauses after romving pure literlas:-");
		for(int i = 0 ;i < clauses.size() ; i++){  
			System.out.println(Arrays.toString(clauses.get(i))); 
		}
		        	
	}
	public static <T> boolean contains(final int[] array, final int v) {
		for (final int e : array)
	        if (e == v)
	            return true;

	    return false;
	}

}
