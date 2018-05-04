package parallelprocessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CityCountryCheck implements Runnable {

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	private List<String> list = new ArrayList<>();
	private Map<String,ArrayList<String>> indexMap = new HashMap<>();
	private FinalLister finalList = new FinalLister();
	private String lastItem = new String();

	public String getLastItem() {
		return lastItem;
	}

	public void setLastItem(String lastItem) {
		this.lastItem = lastItem;
	}

	public FinalLister getFinalList() {
		return finalList;
	}

	public void setFinalList(FinalLister finalList) {
		this.finalList = finalList;
	}

	public Map<String, ArrayList<String>> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(Map<String, ArrayList<String>> indexMap) {
		this.indexMap = indexMap;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			checkerIndex(this.list, this.indexMap, this.finalList, this.lastItem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkerIndex(List<String> list, Map<String, ArrayList<String>> IndexMap, FinalLister finalList, String lastItem) throws InterruptedException {
		Iterator<String> itr = list.iterator();
		List<String> newList = new ArrayList<>();
		/*System.out.println(list.subList(0, 10));
		System.out.println(list.size());*/
		while(itr.hasNext()) {
			
			String[] lines = itr.next().split(",",-1);
			
			String elementsLine = lines[1];
			List<String> elements = Arrays.asList(lines[1].split(" "));
			
			int word = 0;
			 while(word<elements.size()) {
				 for(int i=0; i< elements.size(); i++) {
					 String chk = "";
					 for(int j=i; j<= word; j++) {
						 chk += elements.get(j) +" ";
					 }
					 chk = chk.trim();
					 
					 if(chk.length() != 0) {
						 	
							if(IndexMap.containsKey(chk.substring(0, 1).toUpperCase())) {
								List<String> CC = IndexMap.get(chk.substring(0, 1).toUpperCase());
								
								
								if(CC.contains(chk.toUpperCase())) {
									
									String[] elm = chk.split(" ",-1);
									for(String el : elm) {
										
										String replacement = el.substring(0, 1).toUpperCase() + el.substring(1);
										elementsLine = elementsLine.replaceAll(el, replacement);
										
										}
								}
							}
						 
					 }
				
				 }
				 word++;
			 }
			
				 newList.add(lines[0]+","+elementsLine);
				 
				
		}
			
		
		if(lastItem == null & finalList.getList().size() == 0) {
			  synchronized (this) {
				  
				  finalList.getList().addAll(newList);
			    }
			  lastItem = "done";
			
		  }else {
			  while(finalList.getList().size() == 0) {
				  try {
					Thread.sleep(0,20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			 
			 
			  while(!lastItem.toLowerCase().equals(finalList.getList().get(finalList.getList().size() - 1).toLowerCase())) {
				  try {
					Thread.sleep(0,20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			  
			  while(lastItem.toLowerCase().equals(finalList.getList().get(finalList.getList().size() - 1).toLowerCase())) {
				  synchronized (this) {
					 
					  finalList.getList().addAll(newList);
				    }
				  lastItem = "done";
				  }
			
		  }
		
		
	}

	/**
	 * @param list
	 */
	public CityCountryCheck(List<String> list, Map<String, ArrayList<String>> IndexMap, FinalLister finalList, String lastItem) {
		super();
		this.list = list;
		this.indexMap = IndexMap;
		this.finalList = finalList;
		this.lastItem = lastItem;
	}
	
}
