package org.nypl.mss.cpm;

import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.*;

public class CLI {
    
    private String[] arguments;
    private Options options;
    private String diskdef;
    private String image;
    private String outputDirectory;
    private boolean create;
    private boolean preserveTime;
    private boolean convertText;
    private boolean deleteContents;
    
    public CLI(String[] args) throws ParseException{
        this.arguments = args;
        options = createOptions();
        testOptions();
        printOptions();
        createOutput();
        
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
        
        diskdef = cmd.getOptionValue("f");    
        image = cmd.getOptionValue("i");
        outputDirectory = cmd.getOptionValue("o");
        
        if(cmd.hasOption("c"))
            create = true; 
        else
            create = false;
        
        if(cmd.hasOption("p"))
            preserveTime = true;
        else
            preserveTime = false;
        
        if(cmd.hasOption("t"))
            convertText = true;
        else
            convertText = false;
        
        if(cmd.hasOption("d"))
            deleteContents = true;
        else
            deleteContents = false;
        
    }
    
        
    public static void main(String[] args) throws ParseException{
        CLI cli = new CLI(args);
    }

    private void printOptions() {
        System.out.println("DiskDef: " + diskdef);
        System.out.println("Image Location: " + image);
        System.out.println("Output Directory Location: " + outputDirectory);
        System.out.println("Create Directory: " + create);
        System.out.println("Preserve timestamps: " + preserveTime);
        System.out.println("Convert to unix: " + convertText);
    }

    private void createOutput() {
        if(create == true){
            File newDir = new File(outputDirectory);
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
