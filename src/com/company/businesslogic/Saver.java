package com.company.businesslogic;

import com.company.database.Database;
import com.company.model.Line;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Saver {

    private List<Line> entities;
    private int count = 0;

    //reading file, creating an object for every line and collecting them in sets of 1000
    public void readData(String file, int num) throws ClassNotFoundException, SQLException {
        try (Scanner input = new Scanner(new File("Files/" + file))) {
            Database db = new Database();
            entities=new ArrayList<>();

            while (input.hasNextLine()) {
                Line saveLine;
                //Getting an array of delimiter-separated strings
                String[] words = input.nextLine().split("\\|\\|");
                saveLine = new Line(words[0], words[1], words[2], words[3], new BigDecimal(words[4]));
                entities.add(saveLine);
                //saving 1000 lines at a time
                if(entities.size()==1000){
                    count+=1000;
                    db.Input(entities, count, num);
                    entities.clear();
                }
            }
            if(entities.size()>0){
                count += entities.size();
                db.Input(entities, count, num);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}