package io.github.fjodor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//
//this class is handling the /ignite command
//
//usage /ignite <player> <seconds> : set a player on fire
//
// note: dit werkt alleen in SURVIVAL of ADVENTURE mode
// met dank aan: Essentials
//
public class IgniteCommand implements CommandExecutor 
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) 
	{
		if (args.length < 1)
			return false;

		// check if the command was issued by a player
		if (!(sender instanceof Player)) 
		{
			sender.sendMessage("This command requires a player!");
			return true;
		}

		// get player name from command parameter
		Player target = (Player) sender.getServer().getPlayer(args[0]);

		// make sure this player is online
		if (target == null) 
		{
			sender.sendMessage(args[0] + " is not currently online.");
			return true;
		}

		int seconds;
		if (args.length == 2) 
		{
			seconds = Integer.parseInt(args[1]);

		} else 
		{
			// set player on fire for default 200 ticks (20 tps so 10 sec)
			seconds = 10;
		}

		target.setFireTicks(seconds * 20);
		target.sendMessage(String.format("You were ignited by %s ",
				sender.getName()));

		return true;
	}

}
