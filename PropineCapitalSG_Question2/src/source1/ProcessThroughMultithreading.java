package source1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import parallelprocessing.CityCountryCheck;
import parallelprocessing.FinalLister;
import parallelprocessing.Lister;
import parallelprocessing.FirstLetterToUpperCase;

public class ProcessThroughMultithreading {

	private int arbitraryCoresNumber = 50;
	private String inputFilePath = new String();
	private String supplementDataSetFilePath = new String();
	private String outputFilePath = new String();
	public int getArbitraryCoresNumber() {
		return arbitraryCoresNumber;
	}

	public void setArbitraryCoresNumber(int arbitraryCoresNumber) {
		this.arbitraryCoresNumber = arbitraryCoresNumber;
	}

	public String getInputFilePath() {
		return inputFilePath;
	}

	public void setInputFilePath(String inputFilePath) {
		this.inputFilePath = inputFilePath;
	}

	public String getSupplementDataSetFilePath() {
		return supplementDataSetFilePath;
	}

	public void setSupplementDataSetFilePath(String supplementDataSetFilePath) {
		this.supplementDataSetFilePath = supplementDataSetFilePath;
	}

	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	public boolean isDoesTheInputFileContainHeader() {
		return doesTheInputFileContainHeader;
	}

	public void setDoesTheInputFileContainHeader(boolean doesTheInputFileContainHeader) {
		this.doesTheInputFileContainHeader = doesTheInputFileContainHeader;
	}

	public boolean isDoesTheSupplementDataSetContainHeader() {
		return doesTheSupplementDataSetContainHeader;
	}

	public void setDoesTheSupplementDataSetContainHeader(boolean doesTheSupplementDataSetContainHeader) {
		this.doesTheSupplementDataSetContainHeader = doesTheSupplementDataSetContainHeader;
	}

	private boolean doesTheInputFileContainHeader = false;
	private boolean doesTheSupplementDataSetContainHeader = false;
	public ProcessThroughMultithreading(String inputFileDataSetPath, String supplementDataSetPath, String outputFilePath, int divideMultipleForCores) {
		this.arbitraryCoresNumber = divideMultipleForCores;
		this.inputFilePath = inputFileDataSetPath;
		this.supplementDataSetFilePath = supplementDataSetPath;
		this.outputFilePath = outputFilePath;
	}
	
	public long processDataSet() throws InterruptedException, ExecutionException, TimeoutException {
		long startTime = System.currentTimeMillis();
		
	    DataFeedsInputFile dfinput = (this.doesTheInputFileContainHeader) ? new DataFeedsInputFile(this.inputFilePath, this.doesTheInputFileContainHeader) : new DataFeedsInputFile(this.inputFilePath);
	    dfinput.readFileInList();
	    List<String> l = dfinput.getLines();
	    
	    int limitValue = l.size()/arbitraryCoresNumber;
	    
	    DataFeedsCountriesAndCities dfcc = (this.doesTheSupplementDataSetContainHeader) ? new DataFeedsCountriesAndCities(this.supplementDataSetFilePath, this.doesTheSupplementDataSetContainHeader) : new DataFeedsCountriesAndCities(this.supplementDataSetFilePath);
	    dfcc.putIntoMap();
		Map<String, ArrayList<String>> Index = dfcc.getIndex();
	   
		Lister intermediateListOut = new Lister();
	    String lastItem = null;
	    int i =0;
	    
	    while(i < l.size()) { 
	    	if((i==0 || i%limitValue == 0)) {
	    		
	    		if(i+limitValue < l.size()) {
	    			FirstLetterToUpperCase fi = new FirstLetterToUpperCase(l.subList(i, i+limitValue), intermediateListOut, lastItem);
	    			i += limitValue;
	    			new Thread(fi).start();
		    		lastItem = l.get(i - 1);
		    		lastItem = lastItem.split(",",-1)[0] +","+ lastItem.split(",",-1)[1].replaceFirst(lastItem.split(",",-1)[1].substring(0, 1), lastItem.split(",",-1)[1].substring(0, 1).toUpperCase());
	    		}else {
	    			FirstLetterToUpperCase fi = new FirstLetterToUpperCase(l.subList(i, l.size()), intermediateListOut, lastItem);
	    			i += l.size() -i;
	    			new Thread(fi).start();
		    		lastItem = l.get(i - 1);
		    		lastItem = lastItem.split(",",-1)[0] +","+ lastItem.split(",",-1)[1].replaceFirst(lastItem.split(",",-1)[1].substring(0, 1), lastItem.split(",",-1)[1].substring(0, 1).toUpperCase());
	    		}

	    	}
	    }
	    while(intermediateListOut.getList().size() != l.size()) {
	    	 try {
					Thread.sleep(0,20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	    lastItem = null;
	    int x =0;
	    FinalLister finalListOut = new FinalLister();
	    while(x < intermediateListOut.getList().size()) {
	    	 
	    	if((x==0 || x%limitValue == 0)) {
	    		if(x+limitValue < intermediateListOut.getList().size()) {
	    	
	    			CityCountryCheck fi = new CityCountryCheck(intermediateListOut.getList().subList(x, x+limitValue), Index, finalListOut, lastItem);
	   
	    			x += limitValue;
	    			new Thread(fi).start();
	    			
	    			lastItem = intermediateListOut.getList().get(x - 1);
		    		lastItem = lastItem.split(",",-1)[0] +","+ lastItem.split(",",-1)[1].replaceFirst(lastItem.split(",",-1)[1].substring(0, 1), lastItem.split(",",-1)[1].substring(0, 1).toUpperCase());
	    			
	    		}else {
	    
	    			CityCountryCheck fi = new CityCountryCheck(intermediateListOut.getList().subList(x, intermediateListOut.getList().size()), Index, finalListOut, lastItem);
	    			x += intermediateListOut.getList().size() -x;
	    			new Thread(fi).start();
	    			
	    			lastItem = intermediateListOut.getList().get(x - 1);
		    		lastItem = lastItem.split(",",-1)[0] +","+ lastItem.split(",",-1)[1].replaceFirst(lastItem.split(",",-1)[1].substring(0, 1), lastItem.split(",",-1)[1].substring(0, 1).toUpperCase());
	    			
	    		}

	    	}
	    }
	      
	    while(finalListOut.getList().size() != intermediateListOut.getList().size()) {
	    	 try {
					Thread.sleep(0,20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	  	BufferedWriter bw = null;
		FileWriter fw = null;

		try {

			Iterator<String> itr = finalListOut.getList().iterator();
			StringBuilder line = new StringBuilder();
			if(this.doesTheInputFileContainHeader) {
				line.append(dfinput.getHeader()+"\n");
			}
			while(itr.hasNext()) {
				line.append(itr.next());
				if(itr.hasNext()) {
					line.append("\n");
				}
			}

			fw = new FileWriter(this.outputFilePath);
			bw = new BufferedWriter(fw);
			bw.write(line.toString());


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
	
		return (System.currentTimeMillis() - startTime);
		
	  }
	
	
		
}
