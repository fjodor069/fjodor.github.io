package nl.Jelly;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//
// this class is handling the /pos command
// 
// usage /pos : gives the players position
//       /pos x,y,z : teleports player to this location
//		this command can only be given by a player
//
//     typing in upper and/or lowercase is allowed
//		note: dit commando bestaat al in essentials (/tppos)
//
public class PosCommand implements CommandExecutor 
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] split) 
	{
		// check if the command was issued by a player
		if (!(sender instanceof Player))
		{
			sender.sendMessage("This command requires a player!");
			return true;
		}
		Player player = (Player) sender;
		
		if (split.length == 0)
		{
			Location location = player.getLocation();
			//location.getWorld();
			//geeft locatie van de speler (netjes op 2 cijfers afgerond)
			//getallen kunnen soms groot worden 
			player.sendMessage(String.format("You are currently at %8.2f, %8.2f, %8.2f  with %6.2f yaw and %6.2f pitch",
					location.getX(),
					location.getY(),
					location.getZ(),
					location.getYaw(),
					location.getPitch()));
			return true;
		} 
		else if (split.length == 3)
		{
			try
			{
				double x = Double.parseDouble(split[0]);
				double y = Double.parseDouble(split[1]);
				double z = Double.parseDouble(split[2]);
				
				player.teleport(new Location(player.getWorld(),x,y,z));
			}
			catch (NumberFormatException ex)
			{
				player.sendMessage("Given location is invalid");
			}
			return true;
		}
		
		
		
		return false;
	}

}
