package eu.blackspectrum.randomteleport;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.*;

import eu.blackspectrum.randomteleport.handlers.*;

public class RandomTeleport extends JavaPlugin
{
    public YamlHandler yamlHandler;
    CommandHandler commandHandler;
    
    public void onEnable() {
        this.yamlHandler = new YamlHandler(this);
        this.logInfo("Yamls loaded!");
        this.commandHandler = new CommandHandler(this);
        this.logInfo("Commands registered!");
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
    	return commandHandler.onCommand(sender, cmd, label, args);
    }
    
    public void logInfo(final String message) {
        this.getLogger().info(message);
    }
    
    public void logWarning(final String message) {
        this.getLogger().warning(message);
    }
    
}
