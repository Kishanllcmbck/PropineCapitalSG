package currying;

import java.util.function.Function;

public class PerformCurrying {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			      
	      int curry = add().apply(5).apply(4);
	      System.out.println(curry);
	}
	
	private static Function<Integer, Function<Integer, Integer>> add() {
	    return x -> y -> x + y;
	}
}
