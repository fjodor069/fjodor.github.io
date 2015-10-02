package io.github.fjodor;

import java.util.logging.Logger;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;


//
// this blocklistener checks for two specific events:
//
// - a sign is created or changed
// - a sign is destroyed (broken)
//
//
public class BlockListener implements Listener
{
	private Logger log ;
	
	private Jelly plugin;
	
	//lift members
	private SignCache cache;
	//private SignFactory factory;
	//private Util util;
	
	public BlockListener(Jelly instance)
	{
		plugin = instance;
		log = plugin.getLogger();
		
		cache = plugin.getSignCache();
		//factory = plugin.getFactory();
		//util = plugin.getUtil();
		if (cache == null)
			log.info("Blocklistener: cache is null");
		
		
	}
	
	@EventHandler 
	public void onSignChange(SignChangeEvent e)
	{
		// a sign is changed by a player
		Sign sign = Util.getSignState(e.getBlock());
		if (sign != null)
		{
			String[] lines = e.getLines();
			
			//SignDetail signDetail = factory.create(sign, lines);
			SignDetail signDetail = new SignDetail(sign,lines);
			
			if (signDetail.isLiftSign())
			{
				cache.newSignCreated(signDetail);
			}
			
			
		}
		
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e)
	{
		Sign sign = Util.getSignState(e.getBlock());
		if (sign != null)
			cache.existingSignDestroyed(sign);
	}
	
	
	
	
}
