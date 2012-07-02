package org.nypl.mss.cpm;

import org.apache.commons.cli.*;

public class CLI {
    
    private String[] arguments;
    private Options options;
    
    public CLI(String[] args) throws ParseException{
        this.arguments = args;
        options = createOptions();
        testOptions();
        
    }

    private static Options createOptions() {
 
        Options mOptions = new Options();

        OptionBuilder.withArgName("String");
        OptionBuilder.hasArg(true);
        OptionBuilder.withDescription("cpm disk definition for image");
        OptionBuilder.isRequired(true);
        mOptions.addOption(OptionBuilder.create("f"));


        mOptions.addOption(new Option("log", "log activities to the console"));

        return mOptions;
    }

    private void testOptions() throws ParseException {
        CommandLineParser parser = new PosixParser();
        CommandLine cmd = parser.parse( options, arguments);
    }
    
        
    public static void main(String[] args) throws ParseException{
        CLI cli = new CLI(args);
    }
    
}
