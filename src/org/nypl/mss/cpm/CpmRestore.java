package org.nypl.mss.cpm;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CpmRestore {
    private List<String> files = new ArrayList<String>();    
    private String diskdef;
    private String image;
    private String outputDirectory;
    private boolean create;
    private boolean preserveTime;
    private boolean convertText;
    private boolean deleteContents;
    
    public CpmRestore() {
        
    }
    
    public void process() throws IOException, InterruptedException{
        getFiles();
        listFiles();
    }

    private void getFiles() throws IOException, InterruptedException{
        System.err.println(image);
        File imageFile = new File(image);
        if(!imageFile.exists()){
            System.err.println("IMAGE FILE DOES NOT EXIST");
            System.exit(1);
        }
        Process p = Runtime.getRuntime().exec("/usr/local/bin/cpmls -f kpiv " + new File(image).getAbsolutePath());
        p.waitFor();
        BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
        String line=reader.readLine(); 
        
        while(line!=null) { 
            files.add(line); 
            line=reader.readLine(); 
        } 
    }
    
    private void listFiles(){
        for(String filename: files){
            System.out.println(filename);
        }
    }
    /*
    
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
    * 
    * */

    public boolean isConvertText() {
        return convertText;
    }

    public void setConvertText(boolean convertText) {
        this.convertText = convertText;
    }

    public String getDiskdef() {
        return diskdef;
    }

    public void setDiskdef(String diskdef) {
        this.diskdef = diskdef;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public boolean isPreserveTime() {
        return preserveTime;
    }

    public void setPreserveTime(boolean preserveTime) {
        this.preserveTime = preserveTime;
    }

    private void checkImage() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    
    
}
