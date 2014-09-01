package nl.Jelly;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//
//this class is handling the /rules command
//
//usage /rules : display the rules
//
//note: dit werkt in alle gamemodes (ook CREATIVE)
//note: dit commando bestaat al (als OP command)
//maar wordt overruled door deze versie
//
public class RulesCommand implements CommandExecutor
{
	private Jelly plugin;
	
	//we need access to the config from the plugin
	public RulesCommand(Jelly instance)
	{
		plugin = instance;
	}
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		// TODO Auto-generated method stub
		// check if the command was issued by a player
		if (!(sender instanceof Player))
		{
			sender.sendMessage("This command requires a player!");
			return true;
		}
		//get player who issued the command
		Player player = (Player) sender;
		
		player.sendMessage( plugin.getConfig().getString("rules") );
		
		return false;
	}

}
