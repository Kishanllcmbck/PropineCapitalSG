package source1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataFeedsCountriesAndCities extends DataFeedsInputFile{

	public DataFeedsCountriesAndCities(String filename, boolean headerFlag) {
		super(filename, headerFlag);
		super.readFileInList();
		this.citiesAndCountries = super.getLines();
		// TODO Auto-generated constructor stub
	}
	
	public DataFeedsCountriesAndCities(String filename) {
		super(filename);
		super.readFileInList();
		this.citiesAndCountries = super.getLines();
		// TODO Auto-generated constructor stub
	}
	
	public Map<String, ArrayList<String>> getIndex() {
		return this.Index;
	}
	public void setIndex(Map<String, ArrayList<String>> index) {
		this.Index = index;
	}

	private Map<String, ArrayList<String>> Index = new HashMap<>();
	private List<String> citiesAndCountries = new ArrayList<>();
	public void putIntoMap() {
	
	    	
	    	Iterator<String> itr = this.citiesAndCountries.iterator();

	    	List<String> Cities = new ArrayList<String>();
	    	
	    	
	    	while(itr.hasNext()) {
	    		String[] arr = itr.next().split(",",-1);
	    		
	    		Cities.add(arr[1].toUpperCase());
	    		Cities.add(arr[5].toUpperCase());
	    		//Cities.add(arr[6].toUpperCase());
	    		Cities.add(arr[7].toUpperCase());
	    		Cities.add(arr[8].toUpperCase());
	    	}
	    	Cities = new ArrayList<String>((Cities.stream().collect(Collectors.toSet())));
	    	
	    	Cities.removeAll(Collections.singleton(""));
	    	
	    	
	    	
	    	for(String city: Cities) {
	    		
	    		
	    		if(this.Index.containsKey(city.substring(0, 1))) {
	    			ArrayList<String> list = new ArrayList<>();
	    			list = this.Index.get(city.substring(0, 1));
	    			list.add(city);
	    			this.Index.put(city.substring(0, 1), list);
	    		}else {
	    			ArrayList<String> list = new ArrayList<>();
	    			list.add(city);
	    			this.Index.put(city.substring(0, 1), list);
	    		}
	    	}
	    	
	    	
	  }
	
}
