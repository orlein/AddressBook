package com.pcj.AddresssBook.lib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileManager {
	 
	
	public static String fileName_AB = "Table_AddressBook";
	public static String fileName_G = "Table_Group";
	public static String fileName_CGR = "Table_ContactGroupRelation";
	private static int curIndex = 0; 
	static File path = new File(".");
	static String absolutePath = path.getAbsolutePath() + "\\resource\\";
	
	public static void logOutput(String msg){
		System.out.println(msg);
	}
	
	
	public static void initiate(){
		while(true){
			File f = new File(absolutePath);
			if (!f.exists()){
				f.mkdirs();
			}
		}
	}
	public static String readFileAndShow(String fileName){
		try {
			BufferedReader reader;
			reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(absolutePath+fileName), "UTF-8")
					);
			String line = null;
			while((line = reader.readLine()) != null){
				logOutput(line);
			}
			reader.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static String makeLatestFileName(String fileName){
		while (new File(absolutePath+fileName_AB+curIndex+".JSON").exists()){
			logOutput("exsisting file name:" +fileName_AB+curIndex+". Making filename...");
			curIndex++;
		}
		return fileName+curIndex+".JSON";
	}
	public static String getFileName(String fileName){
		return fileName+curIndex+".JSON";
	}
	
	
		
	public static void saveStringIntoFile(String string,String fileName){
		try {
			File f = new File(absolutePath+fileName);
			if (f.exists() == false){
				f.createNewFile();
			}
			logOutput("Saving File:"+absolutePath+fileName);
			BufferedWriter outputFile = new BufferedWriter(new FileWriter(absolutePath+fileName,true));
			outputFile.write(string);
			outputFile.flush();
			outputFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String[] findExistingFiles(){
		int index = 0;
		String tempFileName;
		String[] result;
		do{
			tempFileName = fileName_AB+index+".JSON";
			index++;
		}while(new File(absolutePath+tempFileName).exists());
		result = new String[index-1];
		for (int i = 0; i<index-1; i++){
			result[i] = fileName_AB+i+".JSON";
		}
		return result;
	}
	public static String loadStringFromFile(String fileName){
		String result;
		if (new File(absolutePath+fileName).exists()){
			logOutput("File Loading: "+absolutePath+fileName);
			try {
				BufferedReader reader;
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(absolutePath+fileName)));
				result = reader.readLine();
				reader.close();
				return result;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				logOutput("File Not Found");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			saveStringIntoFile("",absolutePath+getFileName(fileName));
		}
		return null;
	}
	
}
