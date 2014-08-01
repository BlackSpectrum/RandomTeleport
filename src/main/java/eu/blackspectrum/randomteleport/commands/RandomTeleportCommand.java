package eu.blackspectrum.randomteleport.commands;

import java.util.List;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.*;

import com.massivecraft.factions.*;
import com.massivecraft.factions.entity.*;
import com.massivecraft.massivecore.ps.*;

import eu.blackspectrum.randomteleport.RandomTeleport;

public class RandomTeleportCommand extends AbstractCommand {

	public RandomTeleportCommand(final RandomTeleport instance) {
        super(instance, "randomteleport");
    }

	@Override
    public boolean onCommand(final CommandSender sender, final List<String> args) {
    	double x = 0, y = 0, z = 0, zMin, zMax, radiusMin, radiusMax, CotWx, CotWz;
		int t, attempts;
		Player target = null;
		World world = null;
		Location location = null;
		boolean lock = true;
		if(args.size() == 0)
			target = (Player) sender;
		else if(args.size() == 1)
		{
            final List<Player> players = (List<Player>)this.plugin.getServer().matchPlayer((String)args.get(0));
            if (players.isEmpty()) {
                sender.sendMessage("No player named: " + args.get(0) + " found.");
                return true;
            }
            target = players.get(0);
		}
		else
			return false;
		world = plugin.getServer().getWorld("world");
		radiusMin = plugin.yamlHandler.config.getDouble("config.radiusMin", 0);
		radiusMax = plugin.yamlHandler.config.getDouble("config.radiusMax", 100);
		CotWx = plugin.yamlHandler.config.getDouble("config.CotWx", 0);
		CotWz = plugin.yamlHandler.config.getDouble("config.CotWz", 0);
        if(radiusMin > radiusMax)
			return true;
        while(lock)
        {
        	attempts = 0;
        	lock = false;
			x = Math.random() * radiusMax;
			zMax = (radiusMax * Math.sin(Math.acos(x / radiusMax)));
			if(x < radiusMin)
				zMin = (radiusMin * Math.sin(Math.acos(x / radiusMin)));
			else
				zMin = 0;
			z = Math.random() * (zMax - zMin) + zMin;
			if(Math.random() * 2 > 1)
				x *= -1;
			if(Math.random() * 2 > 1)
				z *= -1;
			x += CotWx;
			z += CotWz;
			x = (Math.floor(x) + Math.ceil(x)) / 2;
			z = (Math.floor(z) + Math.ceil(z)) / 2;
			location = new Location(world, x, 0, z);
			world.getChunkAt(location).load();
			y = world.getHighestBlockAt(location).getY();
			location = new Location(world, x, y - 1, z);
			t = world.getBlockTypeIdAt(location);
			if(y < 63 || (t >= 8 && t <= 11) || t == 51 || t == 81 || t == 119)
				lock = true;
			Faction faction = BoardColls.get().getFactionAt(PS.valueOf(location));
			if(!faction.getName().contentEquals("§2Wilderness"))
				lock = true;
			if(++attempts >= 0.1 * Math.PI * (Math.pow(radiusMax, 2) - Math.pow(radiusMin, 2)))
			{
				sender.sendMessage("Teleportation failed! Is the teleport area valid?");
				return true;
			}
        }
        location = new Location(world, x, y, z);
		target.teleport(location);
		return true;
	}
}
