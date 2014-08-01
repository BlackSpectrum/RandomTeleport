package eu.blackspectrum.randomteleport.commands;

import org.bukkit.command.*;

import eu.blackspectrum.randomteleport.*;

import java.util.*;

public abstract class AbstractCommand
{
    protected final RandomTeleport plugin;
    protected final String name;
    
    public AbstractCommand(final RandomTeleport instance, final String commandName) {
        super();
        this.plugin = instance;
        this.name = commandName;
    }
    
    public abstract boolean onCommand(final CommandSender p0, final List<String> p1);
    
    public boolean onConsoleCommand(final CommandSender sender, final List<String> args) {
        return this.onCommand(sender, args);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        try {
            return this.plugin.getCommand(this.name).getDescription();
        }
        catch (NullPointerException e) {
            return null;
        }
    }
    
    public String getUsage() {
        try {
            return this.plugin.getCommand(this.name).getUsage();
        }
        catch (NullPointerException e) {
            return null;
        }
    }
    
    public String getPermission() {
        try {
            return this.plugin.getCommand(this.name).getPermission();
        }
        catch (NullPointerException e) {
            return null;
        }
    }
    
    public List<String> getAliases() {
        try {
            return (List<String>)this.plugin.getCommand(this.name).getAliases();
        }
        catch (NullPointerException e) {
            return null;
        }
    }
}
