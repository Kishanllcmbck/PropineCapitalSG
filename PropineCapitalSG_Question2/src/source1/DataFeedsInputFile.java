package source1;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataFeedsInputFile {
	private List<String> lines = new ArrayList<>();
	private String filename = new String();
	private String header = new String();
	private boolean headerFlag = false;
	public List<String> getLines() {
		return this.lines;
	}
	
	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public void readFileInList() {
	 
	    try {
	      this.lines = Files.readAllLines(Paths.get(this.filename), StandardCharsets.UTF_8);
	      if(headerFlag) {
	    	  this.header = this.lines.get(0);
	    	  this.lines.remove(0);  
	      }
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	    }

	  }

	public String getHeader() {
		return this.header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	/**
	 * @param lines
	 */
	public DataFeedsInputFile(String filename) {
		super();
		this.filename = filename;
	}
	
	public DataFeedsInputFile(String filename, boolean headerFlag) {
		super();
		this.filename = filename;
		this.headerFlag = headerFlag;
	}

}
