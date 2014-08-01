package eu.blackspectrum.randomteleport.commands;

import org.bukkit.command.*;

import eu.blackspectrum.randomteleport.*;

import java.util.*;

public class ReloadCommand extends AbstractCommand
{
    public ReloadCommand(final RandomTeleport instance) {
        super(instance, "randomteleportreload");
    }
    
    @Override
    public boolean onCommand(final CommandSender sender, final List<String> args) {
        if (args.size() == 0) {
            this.plugin.yamlHandler.loadConfig();
            sender.sendMessage("Random Teleport config file reloaded.");
            return true;
        }
        return false;
    }
    
    
}
