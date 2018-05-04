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

public class ProcessWithOutMultithreading {
	
	private String inputFilePath = new String();
	private String supplementDataSetFilePath = new String();
	private String outputFilePath = new String();
	
	
	/**
	 * @param inputFilePath
	 * @param supplementDataSetFilePath
	 * @param outputFilePath
	 * @param doesTheInputFileContainHeader
	 * @param doesTheSupplementDataSetContainHeader
	 */
	public ProcessWithOutMultithreading(String inputFilePath, String supplementDataSetFilePath, String outputFilePath) {
		super();
		this.inputFilePath = inputFilePath;
		this.supplementDataSetFilePath = supplementDataSetFilePath;
		this.outputFilePath = outputFilePath;
		
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
	
	@SuppressWarnings("unused")
	public long processDataSet() throws InterruptedException, ExecutionException, TimeoutException {
		long startTime = System.currentTimeMillis();
		DataFeedsInputFile dfinput = (this.doesTheInputFileContainHeader) ? new DataFeedsInputFile(this.inputFilePath, this.doesTheInputFileContainHeader) : new DataFeedsInputFile(this.inputFilePath);
	    dfinput.readFileInList();
	    List<String> l = dfinput.getLines();
	    
	    DataFeedsCountriesAndCities dfcc = (this.doesTheSupplementDataSetContainHeader) ? new DataFeedsCountriesAndCities(this.supplementDataSetFilePath, this.doesTheSupplementDataSetContainHeader) : new DataFeedsCountriesAndCities(this.supplementDataSetFilePath);
	    dfcc.putIntoMap();
		Map<String, ArrayList<String>> Index = dfcc.getIndex();
	   
		Lister intermediateListOut = new Lister();
		FinalLister finalListOut = new FinalLister();
		
		
		FirstLetterToUpperCase fi = new FirstLetterToUpperCase(l, intermediateListOut, null);
		fi.run();
		
		
		CityCountryCheck fi2 = new CityCountryCheck(intermediateListOut.getList(), Index, finalListOut, null);
		fi2.run();
		
		
		
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
	
		return System.currentTimeMillis() - startTime;
		
	  }
	
	
}
