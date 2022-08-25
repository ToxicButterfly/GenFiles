package com.company.businesslogic;

import com.company.Const;
import com.sun.prism.image.CachingCompoundImage;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class FileManager {

    //generate files and fill them with created lines
    public void create() throws IOException {

        //create 100 files
        for(int i = 1; i<= Const.FILES_COUNT ; i++){
            String fileName = "file" + i + ".txt";

            File myFile = new File("Files",fileName);
            if(myFile.createNewFile())
                System.out.println("File created");
            else
                System.out.println("Not");

            FileWriter writer = new FileWriter(myFile, true);
            //generate lines to write down
            for(int j=0;j<Const.FILES_LINES_COUNT;j++) {

                writer.write(genDate() + "||" + genEng() + "||" + genRu()+ "||" + genInt() + "||" + genDou() + "||\n");
                writer.flush();
            }
            writer.close();
        }
    }

    //all the info from 99 files are stored in "file1.txt". All other files are deleted
    public void unite() throws IOException {
        File mainFile = new File("Files", "file1.txt");
        FileWriter writer = new FileWriter(mainFile, true);
        for(int i=2;i<=100;i++) {
            String fileName = "file" + i + ".txt";

            File myFile = new File("Files",fileName);
            FileReader reader = new FileReader(myFile);
            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                writer.write(scan.nextLine() + "\n");
                writer.flush();
            }
            scan.close();
            reader.close();
            myFile.delete();
        }
        writer.close();
    }

    //get all the info into the first file but also delete all the lines that corresponds to condition
    public int uniteDel(String str) throws IOException {
        String base;
        int count = 0;
        File mainFile = new File("Files", "file1.txt");
        FileWriter writer = new FileWriter(mainFile, true);
        for(int i=2;i<=Const.FILES_COUNT;i++) {
            String fileName = "file" + i + ".txt";
            File myFile = new File("Files",fileName);
            FileReader reader = new FileReader(myFile);

            Scanner scan = new Scanner(reader);
            while (scan.hasNextLine()) {
                base = scan.nextLine();
                //if there's a line with entered character sequence, it will not be written down in first file
                if(base.indexOf(str) !=-1)
                {
                    count++;
                    continue;
                }
                writer.write(base + "\n");
                writer.flush();
            }
            scan.close();
            reader.close();
            myFile.delete();
        }
        System.out.println("Number of lines deleted is " + count + ".");
        writer.close();
        return Const.FILES_COUNT*Const.FILES_LINES_COUNT - count;
    }

    //Method to generate first part of the line: Date
    public static String genDate() {
        //get current date
        LocalDate to = LocalDate.now();
        //get date 5 years ago
        LocalDate from = to.minusYears(5);

        long days = from.until(to, ChronoUnit.DAYS);
        long randomDays = ThreadLocalRandom.current().nextLong(days + 1);
        //get random date for last 5 years
        LocalDate randomDate = from.plusDays(randomDays);
        String finDate = randomDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return finDate;
    }

    //Method to generate second part of the line: English string
    public static String genEng() {
        Random rnd = new Random();
        char nextChar;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 10; i++) {
            //Choose whether the character will be capitalized or not
            int a = rnd.nextInt(2);
            if(a == 0) {
                //get a random number from 0 to 26 that corresponds to letter number from english alphabet. Capitalized
                nextChar = (char) (rnd.nextInt(26) + 97);
                sb.append(nextChar);
            }
            else {
                //get a random number from 0 to 26 that corresponds to letter number from english alphabet. Not capitalized
                nextChar = (char) (rnd.nextInt(26) + 65);
                sb.append(nextChar);
            }
        }
        return sb.toString();
    }

    //Method to generate third part of the line: Russian string
    public static String genRu() {
        Random rnd = new Random();
        //String of all russian letters
        String dict = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
        char nextChar;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 10; i++) {
            //get a random letter from string of russian letters
            nextChar = dict.charAt(rnd.nextInt(66));
            sb.append(nextChar);
        }
        return sb.toString();
    }

    //Method to generate fourth part of the line: Integer value
    public static String genInt() {
        Random rnd = new Random();
        //get a random int
        int a = rnd.nextInt(Const.INT_UPPER_BOUND)+1;
        //if the number is odd, increase it by 1
        if(a%2 == 1) {
            a += 1;
        }
        return String.valueOf(a);
    }

    //Method to generate fifth part of the line: Double value
    public static BigDecimal genDou() {
        Random rnd = new Random();
        double d =(rnd.nextDouble() * (Const.DOUBLE_UPPER_BOUND - Const.DOUBLE_LOWER_BOUND)) + Const.DOUBLE_LOWER_BOUND;
        //parse double to String to get 8 decimal places. Parse back to BigDecimal cause it does not remove trailing zeroes.
        return new BigDecimal(String.format("%." + Const.DOUBLE_DECIMAL_PLACES + "f", d));

    }
}

