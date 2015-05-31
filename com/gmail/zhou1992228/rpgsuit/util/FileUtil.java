package com.gmail.zhou1992228.rpgsuit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileUtil {
	@SuppressWarnings({ "resource" })
	public static Object readObjectFrom(String fileName) {
		createFileIfNotExist(fileName);
		FileInputStream freader;
		try {
			freader = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(freader);
			return objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void saveObjectTo(Object o, String fileName) {
		createFileIfNotExist(fileName);
		try {
			FileOutputStream outStream = new FileOutputStream(fileName);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					outStream);
			objectOutputStream.writeObject(o);
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void createFileIfNotExist(String fileName) {
		File yourFile = new File(fileName);
		if(!yourFile.exists()) {
		    try {
				yourFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
	}
}
