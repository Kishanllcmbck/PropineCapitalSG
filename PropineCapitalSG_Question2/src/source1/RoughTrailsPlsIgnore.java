package source1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import parallelprocessing.Lister;

public class RoughTrailsPlsIgnore {

/*	public static Map<String, ArrayList<String>> Index = new HashMap<>();
	//public static Map<String, ArrayList<String>> CountryIndex = new HashMap<>();	
	public static List<String> readFileInList(String fileName, String citiesAndCountriesFeed) {
	 
	    List<String> lines = Collections.emptyList();
	    List<String> citiesAndCountries = Collections.emptyList();
	    try {
	      lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
	      citiesAndCountries = Files.readAllLines(Paths.get(citiesAndCountriesFeed), StandardCharsets.UTF_8);
	      citiesAndCountries.remove(0);
	      lines.remove(0);
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	    	
	    	Iterator<String> itr = citiesAndCountries.iterator();

	    	List<String> Cities = new ArrayList<String>();
	    	List<String> Countries = new ArrayList<String>();
	    	
	    	while(itr.hasNext()) {
	    		String[] arr = itr.next().split(",");
	    		
	    		Cities.add(arr[1].toUpperCase());
	    		Countries.add(arr[5].toUpperCase());
	    		
	    	}
	    	
	    	
	    	for(String city: new ArrayList<String>((Cities.stream().collect(Collectors.toSet())))) {
	    		if(Index.containsKey(city.substring(0, 1))) {
	    			ArrayList<String> list = new ArrayList<>();
	    			list = Index.get(city.substring(0, 1));
	    			list.add(city);
	    			Index.put(city.substring(0, 1), list);
	    		}else {
	    			ArrayList<String> list = new ArrayList<>();
	    			list.add(city);
	    			Index.put(city.substring(0, 1), list);
	    		}
	    	}
	    	
	    	for(String city: new ArrayList<String>((Countries.stream().collect(Collectors.toSet())))) {
	    		if(Index.containsKey(city.substring(0, 1))) {
	    			ArrayList<String> list = new ArrayList<>();
	    			list = Index.get(city.substring(0, 1));
	    			list.add(city);
	    			Index.put(city.substring(0, 1), list);
	    		}else {
	    			ArrayList<String> list = new ArrayList<>();
	    			list.add(city);
	    			Index.put(city.substring(0, 1), list);
	    		}
	    	}
	    	
	    }
	    
	    
	    return lines;
	  }*/
	
	
	  public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
	    //List<String> lb4 = readFileInList("C:\\Users\\madhueswar\\Desktop\\Propine SG\\million-headlines\\abcnews-date-text.csv", "C:\\Users\\madhueswar\\Desktop\\Propine SG\\simplemaps-worldcities-basic.csv");
	    DataFeedsInputFile dfinput = new DataFeedsInputFile("C:\\Users\\madhueswar\\Desktop\\Propine SG\\million-headlines\\abcnews-date-text.csv");
	    dfinput.readFileInList();
	    List<String> l = dfinput.getLines();
	    
	    DataFeedsCountriesAndCities dfcc = new DataFeedsCountriesAndCities("C:\\\\Users\\\\madhueswar\\\\Desktop\\\\Propine SG\\\\simplemaps-worldcities-basic.csv");
	    dfcc.putIntoMap();
		//Map<String, ArrayList<String>> Index = dfcc.getIndex();
	   // List<LineFromCSV> StartChangedList = new ArrayList<>();
		Lister intermediateListOut = new Lister();
	    
	    int i =0;
	   // ExecutorService executor = Executors.newCachedThreadPool();
	    while(i < l.size()) {
	    	
	    	 
	    	 
	    	if((i==0 || i%50000 == 0)) {
	    		
	    		//Future<List<LineFromCSV>> futureCall = null;
	    		if(i+50000 < l.size()) {
	    			//System.out.println(i);
	    			//System.out.println("l - sublist: "+l.subList(i, i+10000));
	    			//FirstLetterToUpperCase fi = new FirstLetterToUpperCase(l.subList(i, i+50000), intermediateListOut);
	    			//System.out.println(fi.getList());
	    			//futureCall = executor.submit(new FirstLetterToUpperCase(l.subList(i, i+10000)));
	    			i += 50000;
	    			//new Thread(fi).start();
	    			
	    		}else {
	    			//System.out.println("l - sublist: "+l.subList(i, l.size()));
	    			//futureCall = executor.submit(new FirstLetterToUpperCase(l.subList(i, l.size())));
	    			//FirstLetterToUpperCase fi = new FirstLetterToUpperCase(l.subList(i, l.size()), intermediateListOut);
	    			i += l.size() -i;
	    			//new Thread(fi).start();
	    		}
	    		
	    		//StartChangedList.addAll(futureCall.get());
	    	}
	    }
	   // executor.shutdownNow();
	   // List<String> list = l.stream().filter(predicate)
	    
	    while(intermediateListOut.getList().size() != l.size()) {
	    	Thread.sleep(500);
	    	//System.out.println("Waiting Loop 1..."+l.size()+" "+intermediateListOut.getList().size());
	    }
	 // System.out.println("Inter List: "+intermediateListOut.getList().subList(0, 10));
	
	  
	  int x =0;
	  Lister lister = new Lister();
	    while(x < intermediateListOut.getList().size()) {
	    	 
	    	if((x==0 || x%50000 == 0)) {
	    		

	    		if(x+50000 < intermediateListOut.getList().size()) {
	    	
	    			//CityCountryCheck fi = new CityCountryCheck(intermediateListOut.getList().subList(x, x+50000), Index, lister);
	   
	    			x += 50000;
	    			//new Thread(fi).start();
	    			
	    		}else {
	    
	    			//CityCountryCheck fi = new CityCountryCheck(intermediateListOut.getList().subList(x, intermediateListOut.getList().size()), Index, lister);
	    			x += intermediateListOut.getList().size() -x;
	    			//new Thread(fi).start();
	    		}

	    	}
	    }
	      
	    while(lister.getList().size() != intermediateListOut.getList().size()) {
	    	Thread.sleep(500);
	    	//System.out.println("Waiting Loop 2..."+intermediateListOut.getList().size()+" "+finalListOut.getList().size());
	    }
	 // System.out.println("Final List: "+finalListOut.getList().subList(0, 10));
	  
	  
	  	BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			Iterator<String> itr = lister.getList().iterator();
			StringBuilder line = new StringBuilder();
			while(itr.hasNext()) {
				line.append(itr.next()+"\n");
			}

			fw = new FileWriter("E:/Android_workspace/FinalOut.csv");
			bw = new BufferedWriter(fw);
			bw.write(line.toString());

			System.out.println("Done");

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
	
	  }
	
}
