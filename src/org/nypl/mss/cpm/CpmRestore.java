package org.nypl.mss.cpm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CpmRestore {
    private List<String> files = new ArrayList<String>();
    private File imageFile;
    private File restoreDirectory; 
    private String user;
    
    public CpmRestore() throws IOException, InterruptedException {
        imageFile = new File("images/M2654-0001.001");
        restoreDirectory = new File("restored_files/");
        getFiles();
        getUser();
        //listFiles();
        restoreFiles();
        
    }
    
    private void getFiles() throws IOException, InterruptedException{
        Process p = Runtime.getRuntime().exec("/usr/local/bin/cpmls -f kpiv " + imageFile.getAbsolutePath());
        p.waitFor();
        BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
        String line=reader.readLine(); 
        
        while(line!=null) { 
            files.add(line); 
            line=reader.readLine(); 
        } 
    }
    
    private void listFiles(){
        for(String file: files){
            System.out.println(file);
        }
    }
    
    private void getUser(){
        user = files.get(0);
        files.remove(0);
        System.out.println("User: " + user);
    }
    
    private void restoreFiles() throws IOException, InterruptedException{
        for(String file: files){
            Process p = Runtime.getRuntime().exec("/usr/local/bin/cpmcp -f kpiv -p -t " 
                    + imageFile.getAbsolutePath()
                    + " " 
                    + user
                    + file
                    + " "
                    + restoreDirectory.getAbsolutePath() + "/" + file
                    );
            p.waitFor();
        }
    }
}
