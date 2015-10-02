package io.github.fjodor;

import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

//
//this class is handling the /sign command
//
//usage /sign
//
// this gives a list of lift signs
// useful for debugging
//
public class SignCommand implements CommandExecutor 
{
	
	private Jelly plugin;
	
	public SignCommand(Jelly instance)
	{
		//we need access to the plugin members
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		//this outputs all the signdetail objects in the hashmap
		Map<String, SignDetail> myMap = plugin.getSignCache().getMap();
		if (myMap != null)
			sender.sendMessage("Entries in signs:  " + myMap);
		else
			sender.sendMessage("Map is null");
		
		
		return true;
		
	}

}
