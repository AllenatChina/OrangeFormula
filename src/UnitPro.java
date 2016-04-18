import java.util.*;

public class UnitPro {
	public boolean OK;
	//store the literals appearing in unit clauses
	Stack<Integer> Units;
	public List<int[]> clauses; 
	
	public static List<int[]> unitClauses; 
	
	public UnitPro(List<int[]> x){
		clauses = x;
	}
 
	public void collectUnitClause()
	{
		unitClauses = new ArrayList<int[]>();
		System.out.println("==========Original Unit Clause=========");
		Set<int[]> DuplicatesRemover = new  HashSet<int[]>();
		for(int i = 0; i<clauses.size();i++ ){			
			if (clauses.get(i).length ==1)	
			{
				unitClauses.add(clauses.get(i));
				System.out.println(Arrays.toString(clauses.get(i)));
			}
		}
		DuplicatesRemover.addAll(unitClauses);
		/*for(int i = 0 ;i < unitClauses.size() ; i++){  
            System.out.println(Arrays.toString(((List<int[]>) unitClauses).get(i)));  
        }*/
		//Iterator iter = unitClauses.iterator();
		
		System.out.println("==========Original Unit Clause size=========");
		System.out.println(DuplicatesRemover.size());  
		
	}
	public void RemoveDup()
	{
		/*for(int i = 0 ; i < unitClauses.size();i++ )
		{
			for(int j = i+1; j< unitClauses.size();j++)
			{
				if(Arrays.equals(unitClauses.get(i), unitClauses.get(j)))
				{
					unitClauses.remove(j);
				}
			}
		}*/
		/*Set<int[]> DuplicatesRemover = new HashSet<int[]>();	
		//int[] arr={2,3,4};
    	DuplicatesRemover.addAll(unitClauses);
    	//DuplicatesRemover.add(arr);
    	//System.out.println(DuplicatesRemover.size());*/
		
    	/*Set<int[]> DuplicatesRemover1 = new HashSet<int[]>();	
		int[] arr={2,3,4};
		List<int[]> l= new ArrayList<int[]>();
		l.add(arr);l.add(arr);l.add(arr);l.add(arr);
		System.out.println(Arrays.toString(arr));

		DuplicatesRemover1.addAll(l);
    	System.out.println(l.size());
    	System.out.println(DuplicatesRemover1.size());
		/*for(int i = 0 ;i < unitClauses.size() ; i++){  
            System.out.println(Arrays.toString(((List<int[]>) DuplicatesRemover).get(i)));  
        } */
	}
	
	public void collectUnitLit(){
		Units = new Stack<Integer>();
		for(int i = 0; i<unitClauses.size();i++ ){			
		   Units.push(clauses.get(i)[0]);	
		}
		System.out.println("1=====Units"+Units);
	}
	
	public void removeClause()
	{
		List<int[]> delList = new ArrayList<int[]>();
		List<int[]> adlList = new ArrayList<int[]>();
		for(int i= 0; i< Units.size();i++){
			int var = Units.get(i);
			Iterator<int[]> iterator = clauses.iterator();
			while(iterator.hasNext()){
				int[] clause = iterator.next();
			    // remove the clause
			    if (containsLit(clause,var)){
			    	//System.out.println("Remove the current element from the iterator and the list");
			        // Remove the current element from the iterator and the list.
			    	if(clause.length>1)
			    	{
			    		iterator.remove();
			    		delList.add(clause);
			    	}
			    }
			    // replace the clause by replace the clause without opposite lit
			    if (containsOpLit(clause,var)){
//			    	delList.add(clause);
			    	iterator.remove();	
			    	
			    	int[] ia = removeElements(clause,var*(-1));
			    	if(ia.length==1)
			    	{
			    		Units.push(ia[0]);
			    		adlList.add(ia);
			    	}// have not consider the case for [1],[-1]
			    	else if( ia.length>1)
					{
			    		adlList.add(ia);
					}			    	
			    	//System.out.println("clause.toString():"+Arrays.toString(clause)+"-var"+var);
			    	
			    }
			   // clauses.removeAll(delList);
			}
			
		}
		
		clauses.addAll(adlList);
		
		for(int i = 0 ;i < clauses.size() ; i++){  
            System.out.println(Arrays.toString(clauses.get(i)));  
        }   
		System.out.println("2=====Units"+Units);
		

		
	}
	
	// check if the list of clause is empty or not
	
	
	
	// check if the clause contains the 
	public static <T> boolean containsLit(final int[] temp, final int i) {
	     for (final int e : temp)
	         if (e == i )
	         return true;          
	   return false;
	}
	
	
	public static <T> boolean containsOpLit(final int[] temp, final int i) {
	     for (final int e : temp)
	         if (e == -i )
	         return true;          
	   return false;
	}
	
	public static int[] removeElements(int[] input, int deleteMe) {
	    List<Integer>  result = new ArrayList<Integer>();
	    if(input.length >1)
	    {	
		    for(int item : input)
		        if(deleteMe!=item)
		            result.add(item);
		    System.out.println("clause.toString():"+Arrays.toString(input)+"var:"+deleteMe);
		    System.out.println("clause.toString():"+result.toString()+"var:"+deleteMe);
	    }
	    int[] result2 = new int[result.size()];
	    for(int i =0; i<result.size();i++){
	    	result2[i] = result.get(i).intValue();
	    }
	    
	    return result2;
	}
}
