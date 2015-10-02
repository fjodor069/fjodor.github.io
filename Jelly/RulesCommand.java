package io.github.fjodor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RulesCommand implements CommandExecutor
{
	private Jelly plugin;
	
	public RulesCommand(Jelly instance)
	{
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage("This command requires a player! ");
			return true;
		}
		Player player = (Player) sender;
		player.sendMessage(plugin.getConfig().getString("rules"));
		
		return false;
	}

	
}
