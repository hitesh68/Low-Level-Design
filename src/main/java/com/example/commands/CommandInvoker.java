package com.example.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.exceptions.CommandNotFoundException;

public class CommandInvoker {
    
    private final Map<CommandName, ICommand> commandMap;

    public CommandInvoker() {
        this.commandMap = new HashMap<>();
    }

    public void registerCommand(CommandName commandName, ICommand command) {

        this.commandMap.put(commandName, command);
    }

    public void executeCommand(String commandString, List<String> tokens) throws CommandNotFoundException{

        try{

            CommandName commandName = CommandName.valueOf(commandString);
            ICommand command = getCommand(commandName);
            command.execute(tokens);
            
        }catch(NullPointerException nullPointerException) {
            throw new CommandNotFoundException("COMMAND_NOT_FOUND");
        }
        
    }

    private ICommand getCommand(CommandName commandName) {
        
        return this.commandMap.get(commandName);
    }

}
