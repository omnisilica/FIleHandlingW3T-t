package com.FileHandlingW3T.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

public class MainController {
	
	private static Logger logger = Logger.getLogger(MainController.class.getName());

	public static void main(String[] args) {
		
		String baseDirectory = System.getProperty("user.dir");
		logger.log(Level.INFO, baseDirectory + ".\n");
		
		String fileName = "filename.txt";
		
		String fileDestination = baseDirectory + "\\src\\resources\\";
		logger.log(Level.INFO, fileDestination + ".\n");
		logger.log(Level.INFO, fileDestination + fileName + ".\n");
		
		File directory = new File(fileDestination);
		
		String fileNamePath = fileDestination + fileName;
		
		File objectFile = new File(fileNamePath);
		
		JSONObject jsonFile = new JSONObject(); 
		
		createTextFile(directory, objectFile);
		
		writeToFile(fileNamePath, "File content.");
		
		readFile(objectFile);
		
		try {
			jsonFile = getFileInfo(objectFile);
		} catch (IOException e) {
			logger.log(Level.WARNING, "File probably doesn't exist.\n");
			e.printStackTrace();
		}
		
		updateFileInfo(jsonFile, objectFile);

	}
	
	public static void createTextFile(File directory, File objectFile) {
		
		try {
			
			if(!directory.exists()) {
				directory.mkdir();
			}
			
			if(objectFile.createNewFile()) {
				logger.log(Level.INFO, "File created: " + objectFile.getName() + ".\n");
			} else {
				logger.log(Level.INFO, "File already exists." + "\n");
			}
			
			
		} catch(IOException e) {
			logger.log(Level.WARNING, "Something went wrong." + "\n");
			e.printStackTrace();
		}
		
	}
	
	public static void writeToFile(String fileName, String fileContent) {
		
		try {
			FileWriter fileWriter = new FileWriter(fileName);
			fileWriter.write(fileContent);
			fileWriter.close();
			
		} catch(IOException e) {
			logger.log(Level.WARNING, "Could not write to file." + "\n");
			e.printStackTrace();
		}
	}
	
	public static void readFile(File file) {
		try {
			Scanner fileReader = new Scanner(file);
			
			while(fileReader.hasNextLine()) {
				String fileLine = fileReader.nextLine();
				System.out.println(fileLine);
			}
			
			fileReader.close();
		} catch(IOException e) {
			logger.log(Level.WARNING, "Could not read file.\n");
			e.printStackTrace();
		}
		
	}
	
	public static JSONObject getFileInfo(File file) throws IOException {
		
		JSONObject jsonObject = new JSONObject();
		
		if(file.exists()) {
			jsonObject.put("File name", file.getName());
			jsonObject.put("Writeable", file.canWrite());
			jsonObject.put("Readable", file.canRead());
			jsonObject.put("File size in bytes", file.length());
			
			logger.log(Level.INFO, jsonObject.toString(4));
		}
		
		return jsonObject;
				
	}
	
	public static void updateFileInfo(JSONObject jsonFile, File file) {
		jsonFile.put("Can Execute", file.canExecute());
		
		logger.log(Level.INFO, "Updated file information.\n" + jsonFile.toString(4));
	}

}