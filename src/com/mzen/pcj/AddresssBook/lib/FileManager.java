package com.mzen.pcj.AddresssBook.lib;

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
	 
	
	public static String fileName = "AddressBook";
	private static int curIndex = 0; 
	static File path = new File(".");
	static String absolutePath = path.getAbsolutePath() + "\\resource\\";
	public static void initiate(){
		while(true){
			String[] filenames = {"Interface_main", "Interface_load", "Interface_list", "Interface_add"};
			File f = new File(absolutePath);
			if (!f.exists()){
				f.mkdirs();
			}else{
				File[] interface_files = new File[filenames.length];
				for (int i=0; i<filenames.length; i++){
					interface_files[i] = new File(absolutePath + filenames[i]);
				}
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
				System.out.println(line);
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
	public static String makeLatestFileName(){
		while (new File(absolutePath+fileName+curIndex+".JSON").exists()){
			System.out.println("exsisting file name:" +fileName+curIndex+". Making filename...");
			curIndex++;
		}
		return fileName+curIndex+".JSON";
	}
	public static String getFileName(){
		return fileName+curIndex+".JSON";
	}
		
	public static void saveStringIntoFile(String string,String fileName){
		try {
			File f = new File(absolutePath+fileName);
			if (f.exists() == false){
				f.createNewFile();
			}
			System.out.println("Saving File:"+absolutePath+fileName);
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
	public static void saveStringIntoFile(String string){
		saveStringIntoFile(string, getFileName());
	}
	public static String[] findExistingFiles(){
		int index = 0;
		String tempFileName;
		String[] result;
		do{
			tempFileName = fileName+index+".JSON";
			index++;
		}while(new File(absolutePath+tempFileName).exists());
		result = new String[index-1];
		for (int i = 0; i<index-1; i++){
			result[i] = fileName+i+".JSON";
		}
		return result;
	}
	public static String loadStringFromFile(String fileName){
		String result;
		if (new File(absolutePath+fileName).exists()){
			System.out.println("File Loading: "+absolutePath+fileName);
			try {
				BufferedReader reader;
				reader = new BufferedReader(new InputStreamReader(
						new FileInputStream(absolutePath+fileName)));
				result = reader.readLine();
				reader.close();
				return result;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("File Not Found");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			saveStringIntoFile("",absolutePath+getFileName());
		}
		return null;
	}
}
