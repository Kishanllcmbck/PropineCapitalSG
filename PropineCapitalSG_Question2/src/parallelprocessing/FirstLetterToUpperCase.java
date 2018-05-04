package parallelprocessing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstLetterToUpperCase implements Runnable{

	private List<String> list = new ArrayList<>();
	private Lister intermediateListOut = new Lister();
	private String lastItem = new String();
	
	public FirstLetterToUpperCase(List<String> elist, Lister intermediateListOut, String lastItem) {
		// TODO Auto-generated constructor stub
		this.list = elist;
		this.intermediateListOut = intermediateListOut;
		this.lastItem = lastItem;
	}
	
	
	 private void ChangeNames(List<String> l, Lister intermediateListOut, String lastItem) {
		  
		  Iterator<String> itr = l.iterator();
		  
		  List<String> list = new ArrayList<>();
		  
		  while (itr.hasNext()) {
		    	String line = itr.next();
		    	
		    	String date = line.split(",", -1)[0];
		    	
		    	line = line.split(",", -1)[1];
		    	
		    	 Pattern pattern = Pattern.compile("[a-zA-Z]*");
		    	 
		    	 Matcher matcher = pattern.matcher(line.substring(0, 1));
		    	 		    	
		    	 line = (matcher.matches()) ? line.replaceFirst(line.substring(0, 1), line.substring(0, 1).toUpperCase()) : line;
		    	
		    	list.add(date+","+line);
		    }
		  if(lastItem == null & intermediateListOut.getList().size() == 0) {
			  synchronized (this) {
				  
				  intermediateListOut.getList().addAll(list);
			    }
			  lastItem = "done";
			
		  }else {
			  while(intermediateListOut.getList().size() == 0) {
				 
				  try {
					Thread.sleep(0,20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			 
			 
			  while(!lastItem.equals(intermediateListOut.getList().get(intermediateListOut.getList().size() - 1))) {
				
				  try {
					Thread.sleep(0,20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  
			  while(lastItem.equals(intermediateListOut.getList().get(intermediateListOut.getList().size() - 1))) {
				  synchronized (this) {
					 
					  intermediateListOut.getList().addAll(list);
				    }
				  lastItem = "done";
				  } 
			
		  }
		  
	 }

	public List<String> getList() {
		return this.list;
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		ChangeNames(this.list, this.intermediateListOut, this.lastItem);
	}
}
