package io.github.fjodor;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

public class SignDetail 
{
//	private final SignCache cache;
//	private final Util util;
	
	private Location location;
	private World world;
	private int x;
	private int y;
	private int z;
	
	private boolean isLiftSign;
	private boolean isLiftUp;
	private boolean isLiftDown;
	
	private SignDetail targetLift;
	
	
	public SignDetail(Sign sign, String[] inputLines)
	{
	//	this.cache = cache;
	//	this.util = util;
		
		//get the location of the sign
		this.location = sign.getLocation();
		this.world = location.getWorld();
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
		
		//get text lines from the sign
		String[] lines = inputLines;
		if (lines == null)
			lines = sign.getLines();
	
		isLiftSign = false;
		
		if (lines != null && lines.length > 1)
		{
			if (lines[0].equalsIgnoreCase("[lift up]"))
			{
				isLiftUp = true;
				isLiftSign = true;
			}
			else if (lines[0].equalsIgnoreCase("[lift down]"))
			{
				isLiftDown = true;
				isLiftSign = true;
			}
			else if (lines[0].equalsIgnoreCase("[lift]"))
			{
				isLiftSign = true;
			}
		}
	}
	
	
	public boolean isPossibleTargetMatch(SignDetail targetSign)
	{
		//check if we have two lift signs
		if (!isLiftSign )
			return false;
		
		if (!targetSign.isLiftSign)
			return false;
		
		Location signLocation = targetSign.getLocation();
		
		//check if X and Z are the same and in the same world
		if (!signLocation.getWorld().equals(world) ||
		    signLocation.getBlockX() != x ||
		    signLocation.getBlockZ() != z)
		      	return false;
		   
		if (isLiftUp && signLocation.getBlockY() > y)
			return true;
		
		else if (isLiftDown && signLocation.getBlockY() < y)
			return true;
		
		return false;
	}
	
	public SignDetail getTargetLift(SignCache cache, Logger log)
	{
		if (!isLiftSign)
		{
			//log.info("getTargetLift: sign is not a lift sign");
			return null;
		}
		if (!isLiftUp && !isLiftDown)
		{
			//log.info("getTargetLift: sign is a destination lift sign; has no target");
			return null;
		
		}
		
		//search for a possible target lift sign
		if (targetLift == null)
		{
			//log.info("getTargetLift: searching for possible target");
			Block b = getLocation().getBlock();
			
			BlockFace face = BlockFace.UP;
			
			int max = world.getMaxHeight() - 1;
			if (!isLiftUp)
			{
				face = BlockFace.DOWN;
				max = 1;
			}
			
			//log.info(String.format("getTargetLift: isLiftup =  %b  face = %s   max = %d ", isLiftUp,face.name(),max));
			
			Block next = b;
			
			//look up and down for a possible target sign
			for (int i = y; i != max;)
			{
				//log.info(String.format("getTargetLift: i = %d ",i));
				//get the next block up or down
				next = next.getRelative(face);
				//check if it is a sign
				Sign sign = Util.getSignState(next);
				
			//	log.info(String.format("getTargetLift: i = %d block = %s",i,next.toString()));
				
				if (sign != null)
				{
					//it is a sign
					//log.info(String.format("getTargetLift: i = %d block = %s",i,next.toString()));
					//log.info("---SIGN FOUND---");
					//if not cached , create a new signdetail
					SignDetail detail = cache.getCachedSignDetail(sign.getLocation());
					if (detail == null)
						detail = cache.newSignCreated(sign);
					
					if (isPossibleTargetMatch(detail))
					{
						//log.info(String.format("getTargetLift: detail = %s ",detail.toString()));
						targetLift = detail;
						break;
					}
					//else
						//log.info("---SIGN is not a match ---");
					
				}
				
				if (isLiftUp)
					i++;
				else
					i--;
					
				
			}
			
		}
		//else
		//	log.info("getTargetLift: no target found");
		
		
		
		return targetLift;
	}
	
	public void clearCache(SignDetail signDetail)
	{
		
		if (!(isLiftSign && signDetail.isLiftSign))
			return;
		
		if (signDetail.world == world &&
				signDetail.x == x &&
				signDetail.z == z)
		{
			targetLift = null;
		}
	}
	
	
	public World getWorld()
	{
		return world;
	}
	
	
	public boolean isLiftSign()
	{
		return isLiftSign;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
}
