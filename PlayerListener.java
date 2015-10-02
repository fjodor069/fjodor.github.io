package io.github.fjodor;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener 
{
	private Logger log ;
	//this is a pointer to the main class
	//which you need to get the config.yml 
	//or other members like a logger
	private Jelly plugin;
	
	//lift members
		private SignCache cache;
	
	
	public PlayerListener(Jelly instance)
	{
		plugin = instance;
		log = plugin.getLogger();
		
		this.cache = plugin.getSignCache();
		//if (cache == null)
		//	log.info("Playerlistener: cache is null");
	}
	
	
	
//	@EventHandler
//	public void playerJoin(PlayerJoinEvent event)
//	{
//		//take the message string from the config.yml file
//		//located in the plugin folder as default
//		
//		event.getPlayer().sendMessage( plugin.getConfig().getString("message") );
//		
//	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent event)
	{
		//make sure player is right clickin else exit
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		
		//check if the block that was clicked is a sign
		Sign sign = Util.getSignState(event.getClickedBlock());
		
		//log.info(String.format("block name interact : %s", event.getClickedBlock().toString()  ));
		//log.info(String.format("player interact : %s", event.getAction().toString()));
		
		//if it is a sign then start processing it
		if (sign != null )
		{
			Player player = event.getPlayer();
			//we clicked on a sign...
		//	player.sendMessage("sign right clicked");
			
		//	log.info("Sign right clicked");
			
			//check if there is a signdetail object at this location
			SignDetail signDetail = cache.getCachedSignDetail(sign.getLocation());
			//log.info("get cached sign detail");
			
			//log.info("get sign detail");
		
			// sign is not already in the cache : make a signdetail and add it
			if (signDetail == null)
			{
				//make a signdetail from this sign
				signDetail = cache.newSignCreated(sign);
				
				//log.info("sign create");
				//player.sendMessage("signdetail created");
			}
			
			//if the sign is not a lift sign return now
			if (!signDetail.isLiftSign())
			{
				//log.info("Sign is not a lift sign");
				return;
			}
			
			//now we have clicked a valid liftsign;
			//check if there is a corresponding target liftsign 
			//player.sendMessage("searching for a target signdetail");
			
			//if (cache == null)
			//	log.info("PlayerInteract: cache is null");
						
			SignDetail targetLift = null;
			targetLift = signDetail.getTargetLift(cache,log);
			
			Sign targetSign = null;
			World world = null;
			
			
			if (targetLift != null)
			{
				//we found a target; get the sign
				world = targetLift.getWorld();
				Block signBlock = world.getBlockAt(targetLift.getLocation());
				targetSign = Util.getSignState(signBlock);
			}
			//else
			//	player.sendRawMessage("targetLift is null ");
			
			
			if (targetSign != null)
			{
				//check permissions if necessary
				
				//check to make sure targetblock is safe
				
				Location playerLocation = player.getLocation();
				Location finalLocation = null;
				for (int y = targetSign.getY();y >= targetSign.getY() -1; y--)
				{
					Location newLocation = new Location(playerLocation.getWorld(),
														playerLocation.getX(),
														y,
														playerLocation.getZ(),
														playerLocation.getYaw(),
														playerLocation.getPitch());
					//Block testBlock = world.getBlockAt(newLocation);
					//if (teleport) check if block is safe
					finalLocation = newLocation;
				}
				
				if (finalLocation != null)
				{
					player.teleport(finalLocation);
					
					if (finalLocation.getBlockY() > playerLocation.getBlockY())
						player.sendMessage( Util.MSG_UP_ONE_FLOOR );
					else
						player.sendMessage( Util.MSG_DOWN_ONE_FLOOR);
						
					event.setCancelled(true);
				}
				else
					player.sendMessage(Util.MSG_DESTINATION_NOT_SAFE);
				
			}
			else
				player.sendMessage(Util.MSG_NO_VALID_LIFT_TARGET);
			//end if targetSign
			
		}//end if sign
		
	}
	
	
	

}
