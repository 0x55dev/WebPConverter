package org.example;

import com.sun.source.tree.NewArrayTree;

import java.awt.color.ICC_ColorSpace;
import java.io.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int convertedFiles = 0;
        int filesSkipped = 0; // these are non-webp files.
        boolean initialDirectoryExists = false;
        boolean outputDirectoryExists = false;
        
        // string paths (this might need to be passed to ffmpeg or appended to an existing string for direct access to files.)
        String path;
        String outputPath;
        
        // the directories for input and outputting files from.
        File dir;
        File outputDir;
        
        System.out.println("WEBP Converter");
        while(!initialDirectoryExists) { //TODO: might be a cleaner way of doing this, also do we need to store both the path strings and File where the directory is?
            System.out.print("Enter path of folder with webp's to convert");
            path = scanner.nextLine();

            dir = new File(path);
            if(!dir.exists() && !dir.isDirectory()) {
                System.out.println("Directory confirmed");
            } else {
                initialDirectoryExists = true;
            }
        }

        while(!outputDirectoryExists) {
            System.out.print("Enter path of folder to save converted images");
            outputPath = scanner.nextLine();
            
            outputDir = new File(outputPath);
            if(!outputDir.exists() && !outputDir.isDirectory()) {
                System.out.println("Directory confirmed");
            } else {
                outputDirectoryExists = true;
            }
        }
        
        System.out.println("Directories confirmed, starting file conversion...");
        convertFiles();
        
        //TODO: so we need to get all paths and validate them,
        // Then we need to go through every file and check if it's a webp, if it is then we convert it
        // so find out how to loop through initial directory, then output to other directory.
        // Track all converted files.
        
    }
    
    private static void convertFiles(String inputPath, String outputPath)
    {
        // utilize processBuilder to access external commands.
        //        ProcessBuilder processBuilder = new ProcessBuilder(
        //                "ffmpeg", "-i", inputPath, 
        //        );
    }
    
    private static String getWebPEncoding(File file)
    {
        try(FileInputStream fis = new FileInputStream(file)) { // get a file input stream
            byte[] header = new byte[20]; // get the first 20 bytes of the files header
            if(fis.read(header) != header.length) { // not entirely sure what this is doing
                return null; // figure out if this is still properly functioning.
            }
            String headerString = new String(header);
            if(!headerString.startsWith("RIFF") && !headerString.contains("WEBP")) {
                return null;
            }
            int position = headerString.indexOf("WEBP") + 4; // why are we adding 4?
            if(position + 4 <= header.length) { // not entirely sure the purpose of this line
                String type = new String(header, position, 4); // read the override docs for the String method.
                switch(type) {
                    case "VP8 ":
                        return "VP8";
                    case "VP8L":
                        return "VP8L";
                    case "VP8X":
                        return "VP8X";
                    default:
                        return null; // not a webp
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}