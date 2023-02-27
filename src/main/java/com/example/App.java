package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.example.appConfig.ApplicationConfig;
import com.example.commands.CommandInvoker;
import com.example.exceptions.CommandNotFoundException;

public class App {

    public static void main(String[] args) {

        List<String> commandLineArgs = Arrays.asList(args);
        run(commandLineArgs);
    }

    private static void run(List<String> commandLineArgs) {

        ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();

        String inputFile = commandLineArgs.get(0);
        BufferedReader reader;

        try {

            reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();

            while(line != null) {
                
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(tokens.get(0), tokens);
                line = reader.readLine();

            }

        } catch (IOException | CommandNotFoundException exception) {
            // TODO: handle exception

            exception.getMessage();
        }
        
    }

}
