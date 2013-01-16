package org.nypl.mss.cpm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CpmRestore {
    private List<String> files = new ArrayList<String>();    
    private String diskdef;
    private String image;
    private String outputDirectory;
    private String user;
    private boolean create;
    private boolean preserveTime;
    private boolean convertText;
    private boolean deleteContents;
    private File imageFile;
    
    public CpmRestore() {
        
    }
    
    public void process() throws IOException, InterruptedException{
        getFiles();
        listFiles();
        restoreDialog();
    }

    private void getFiles() throws IOException, InterruptedException{
        System.err.println(image);
        imageFile = new File(image);
        if(!imageFile.exists()){
            System.err.println("IMAGE FILE DOES NOT EXIST");
            System.exit(1);
        }
        Process p = Runtime.getRuntime().exec("/usr/local/bin/cpmls -f " + diskdef + " " + new File(image).getAbsolutePath());
        p.waitFor();
        BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream())); 
        String line=reader.readLine(); 
        int count = 0;
        while(line!=null) { 
            if(count == 0){
                user = line;
            } else {
                files.add(line); 
            }
            ++count;
            line=reader.readLine(); 
        } 
    }
    
    private void listFiles(){
        int count = 1;
        
        for(String filename: files){
            System.out.println(count + "\t" + filename);
            ++count;
        }
    }

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

    private void restoreDialog() throws IOException, InterruptedException {
        System.out.println("\nRestore a single file (Enter file number) or all ('A'): ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String s = in.readLine();
        if(s.equalsIgnoreCase("a")){
            restoreAll();
        } else {
            int i = Integer.parseInt(s);
            System.out.println("restoring\n" + i + "\t" + files.get(i - 1));
            restoreFile(i - 1);
            printFile(i - 1);
        }
    }
    
    private void restoreFile(int i) throws IOException, InterruptedException{
        Process p = Runtime.getRuntime().exec("/usr/local/bin/cpmcp -f kpiv -p -t " 
                    + imageFile.getAbsolutePath()
                    + " " 
                    + user
                    + files.get(i)
                    + " "
                    + new File(outputDirectory).getAbsolutePath() + "/" + files.get(i)
                    );
        p.waitFor();
    }

    private void printFile(int i) throws FileNotFoundException, IOException {
        System.out.println("");
        File f = new File(new File(outputDirectory).getAbsolutePath() + "/" + files.get(i));
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while((line = br.readLine()) != null){
            System.out.println(line);
        }
    }
    
    private void restoreAll() throws IOException, InterruptedException{
        for(String file: files){
            Process p = Runtime.getRuntime().exec("/usr/local/bin/cpmcp -f kpiv -p -t " 
                    + imageFile.getAbsolutePath()
                    + " " 
                    + user
                    + file
                    + " "
                    + new File(outputDirectory).getAbsolutePath() + "/" + file
                    );
            p.waitFor();
        }
    }

    
    
}
