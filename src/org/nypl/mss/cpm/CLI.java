package org.nypl.mss.cpm;

import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.*;

public class CLI {
    
    private String[] arguments;
    private Options options;
    private boolean create;
    private boolean deleteContents;
    private final CpmRestore cpr;
    
    public static void main(String[] args) throws ParseException, IOException, InterruptedException{
        CLI cli = new CLI(args);
    }
    
    public CLI(String[] args) throws ParseException, IOException, InterruptedException{
        for(String s: args){
            System.out.println(s);
        }
        System.out.println("HELLO");
        this.arguments = args;
        options = createOptions();
        cpr = new CpmRestore();       
        testOptions();
        printOptions();
        createOutput();
        cpr.process();

        
        
    }
    
    private String[] getTestArgs(){
        String[] a = {"-f","kpiv", "-i", "M2654-0001.001", "-o" , "output"};
        return a;
    }

    private static Options createOptions() {
 
        Options mOptions = new Options();

        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(true);
        OptionBuilder.withDescription("cpm disk definition for image");
        OptionBuilder.isRequired(false);
        mOptions.addOption(OptionBuilder.create("f"));
        
        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(true);
        OptionBuilder.withDescription("location of image to have files restored from");
        OptionBuilder.isRequired(true);
        mOptions.addOption(OptionBuilder.create("i"));
        
        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(true);
        OptionBuilder.withDescription("location of directory to restore files to");
        OptionBuilder.isRequired(true);
        mOptions.addOption(OptionBuilder.create("o"));
        
        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription("Create the output directory");
        OptionBuilder.isRequired(false);
        mOptions.addOption(OptionBuilder.create("c"));
        
        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription("Preserve timestamps");
        OptionBuilder.isRequired(false);
        mOptions.addOption(OptionBuilder.create("p"));
        
        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription("Preserve timestamps");
        OptionBuilder.isRequired(false);
        mOptions.addOption(OptionBuilder.create("t"));
        
        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(false);
        OptionBuilder.withDescription("Delete contents of output directory");
        OptionBuilder.isRequired(false);
        mOptions.addOption(OptionBuilder.create("d"));

        mOptions.addOption(new Option("log", "log activities to the console"));

        return mOptions;
    }

    private void testOptions() throws ParseException {
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse( options, arguments);
        
        cpr.setDiskdef(cmd.getOptionValue("f"));    
        cpr.setImage(cmd.getOptionValue("i"));
        cpr.setOutputDirectory(cmd.getOptionValue("o"));
        
        if(cmd.hasOption("c"))
            create = true; 
        else
            create = false;
        
        if(cmd.hasOption("d"))
            deleteContents = true;
        else
            deleteContents = false;
        
        if(cmd.hasOption("p"))
            cpr.setPreserveTime(true);
        else
            cpr.setPreserveTime(false);
        
        if(cmd.hasOption("t"))
            cpr.setConvertText(true);
        else
            cpr.setConvertText(true);
        
        
        
    }
    
        
    

    private void printOptions() {
        System.out.println("DiskDef: " + cpr.getDiskdef());
        System.out.println("Image Location: " + cpr.getImage());
        System.out.println("Output Directory Location: " + cpr.getOutputDirectory());
        System.out.println("Create Directory: " + create);
        System.out.println("Delete directory contents: " + deleteContents);
        System.out.println("Preserve timestamps: " + cpr.isPreserveTime());
        System.out.println("Convert to unix: " + cpr.isConvertText());
    }

    private void createOutput() {
        if(create == true){
            File newDir = new File(cpr.getOutputDirectory()).getAbsoluteFile();
            if(newDir.exists()){
                System.out.println("The output directory already exists");
            }
            else {
                try{
                    newDir.mkdirs();
                    System.out.println("Output directory created");
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        }
    }
    
}
