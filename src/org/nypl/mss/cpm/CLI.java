package org.nypl.mss.cpm;

import org.apache.commons.cli.*;

public class CLI {
    
    private String[] arguments;
    private Options options;
    private String diskdef;
    private String image;
    private String outputDirectory;
    private Boolean create;
    private Boolean preserveTime;
    private Boolean convertText;
    
    public CLI(String[] args) throws ParseException{
        this.arguments = args;
        options = createOptions();
        testOptions();
        printOptions();
        
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
    
}
