import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clauses {
	 public List<int[]> clauses;
	 //public int[][] propvars;
	 private static Clauses resClauses;
	 
	 public static Clauses generateClause()
	 {
		 //resClauses.propvars = new int[10][4];
		 resClauses = new Clauses();
		 Random random = new Random();
		 resClauses.clauses = new ArrayList<int[]>();
		 
         //Give each cell a number
         for (int i = 0; i < 5; i++) {
        	 int clause[] = new int[4];
             for (int j = 0; j < 4; j++) {                   
                     if(random.nextBoolean()==false)
                     {
                     	// assign random value and make it false
                    	 clause[j] = randInt(1,25)*-1;
                     }
                     else
                     {
                    	 clause[j] = randInt(1,25);
                     }                    
             }  
             //System.out.println("clause==="+Arrays.toString(clause));
             resClauses.clauses.add(clause);     
         }
         
         for (int i = 0; i < 5; i++) {
        	 int clause[] = new int[1];
                       
             if(random.nextBoolean()==false)
             {
             	// assign random value and make it false
            	 clause[0] = randInt(1,20)*-1;
             }
             else
             {
            	 clause[0] = randInt(1,20);
             }                    
             
             //System.out.println("clause==="+Arrays.toString(clause));
             resClauses.clauses.add(clause);     
         }
         
         for (int i = 0; i < 5; i++) {
        	 int clause[] = new int[2];
        	 for (int j = 0; j < 2; j++) {                   
                 if(random.nextBoolean()==false)
                 {
                 	// assign random value and make it false
                	 clause[j] = randInt(1,20)*-1;
                 }
                 else
                 {
                	 clause[j] = randInt(1,20);
                 }                    
             }  
             
             //System.out.println("clause==="+Arrays.toString(clause));
             resClauses.clauses.add(clause);     
         }
		 return resClauses;
	 }
	 
	 
	  public static int randInt(int min, int max) {
	        int randomNum = min + (int)(Math.random() * ((max - min) + 1));
	       // System.out.println("randomNum:"+randomNum);
	        return randomNum;       
	    }
}
