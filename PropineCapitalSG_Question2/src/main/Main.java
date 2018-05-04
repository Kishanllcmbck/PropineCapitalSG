package main;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import source1.ProcessThroughMultithreading;
import source1.ProcessWithOutMultithreading;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**
		 * inputFilePath :- Please provide the filepath of the input file
		 * **/
		
		String inputFilePath = "abcnews-date-text.csv";
		
		/**
		 * supplementDataSetFilePath :- Please provide the filepath of the supplement dataset file
		 * **/
		
		String supplementDataSetFilePath = "simplemaps-worldcities-basic.csv";
		
		/**
		 * outputFilePath :- Please provide the filepath for output
		 * **/
		
		String outputFilePath = "FinalOut.csv";
		
		/**
		 * divideMultipleBasingOnCPUCores :- Please an int variable to produce a number to cap the records processed by a single thread.
		 * Example : if the number of records supposed to be processed are 1000000,
		 * 			 By fixing the int variable at 100 - 10000 records will be processed per thread. A new thread will be created for every 10000 records.
		 * 
		 * Approximately 50% decrease in processing time through multithreading with System specification of i7 processor with 4 cores.
		 * The difference would be more substantial with hardware specs of a server processor with more cores
		 * 
		 * Note: you can chose to ignore this if you do not wish to multithread 
		 * **/
		
		int divideMultipleBasingOnCPUCores = 50;
		
		System.out.println("With Multithreading: "+processThroughMultithreads(inputFilePath, true, supplementDataSetFilePath, true, outputFilePath, divideMultipleBasingOnCPUCores)/1000);
		System.out.println("WithOut Multithreading: "+processWithOutMultithreads(inputFilePath, true, supplementDataSetFilePath, true, outputFilePath)/1000);
	}
	
	@SuppressWarnings("finally")
	private static long processThroughMultithreads(String inputFilePath, boolean doesInputFileHasHeader, String supplementDataSetFilePath, boolean doesSupplementFileHasHeaders, String outputFilePath, int divideMultipleBasingOnCPUCores) {
		
		long processTime = 0L;
		ProcessThroughMultithreading processThroughMultithreading = new ProcessThroughMultithreading(inputFilePath, supplementDataSetFilePath, outputFilePath, divideMultipleBasingOnCPUCores);
		processThroughMultithreading.setDoesTheInputFileContainHeader(doesInputFileHasHeader);
		processThroughMultithreading.setDoesTheSupplementDataSetContainHeader(doesSupplementFileHasHeaders);
		try {
			processTime = processThroughMultithreading.processDataSet();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return processTime;
		}
		
	}
	
	@SuppressWarnings("finally")
	private static long processWithOutMultithreads(String inputFilePath, boolean doesInputFileHasHeader, String supplementDataSetFilePath, boolean doesSupplementFileHasHeaders, String outputFilePath) {
		
		long processTime = 0L;
		ProcessWithOutMultithreading processWithOutMultithreading = new ProcessWithOutMultithreading(inputFilePath, supplementDataSetFilePath, outputFilePath);
		processWithOutMultithreading.setDoesTheInputFileContainHeader(doesInputFileHasHeader);
		processWithOutMultithreading.setDoesTheSupplementDataSetContainHeader(doesSupplementFileHasHeaders);
		try {
			processTime = processWithOutMultithreading.processDataSet();
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			return processTime;
		}
		
	}
	
}
