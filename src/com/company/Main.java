package com.company;

import com.company.businesslogic.FileManager;
import com.company.businesslogic.Saver;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws IOException {
		FileManager Fm = new FileManager();
		Saver saver = new Saver();
		Fm.create();
		int num;
//		Fm.unite();
		//argument is the string, lines with that will be deleted
		num = Fm.uniteDel("5");
		try {
			saver.readData("file1.txt", num);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
