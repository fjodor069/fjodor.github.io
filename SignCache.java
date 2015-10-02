package io.github.fjodor;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Sign;

///
// this class keeps track of known signs and their lift status
// the signs details are processed once and kept in a hashmap
// when a lift sign is placed or destroyed, iterate through
// the existing lifts to find possible matches
//
public class SignCache 
{
	private final Map<String, SignDetail> signs = new HashMap<String, SignDetail>();
	
	
	
	public SignCache()
	{
		
	}
	
	//return the hashmap object to debug
	public HashMap<String,SignDetail> getMap()
	{
		return (HashMap<String,SignDetail>)signs;
	}
	
	public SignDetail getCachedSignDetail(Location location)
	{
		final String locationString = getLocationKey(location);
		
		
		return signs.get(locationString);
	}

	
	//to be called when a new sign is created or an existing sign is noticed in game
	//and needs to be added to the cache
	public SignDetail newSignCreated(Sign sign)
	{
		//check if there is a signDetail object at this location
		SignDetail signDetail = getCachedSignDetail(sign.getLocation());
		
		if (signDetail != null)
		{
			//if we overwrite an existing signdetail
			//remove it first
			invalidateCacheLocation(signDetail);
			signs.remove(getLocationKey(signDetail));
		}
		
		
		//create a signdetail and add it to the cache
		signDetail = new SignDetail(sign,null);
		signs.put(getLocationKey(signDetail), signDetail);
		invalidateCacheLocation(signDetail);
		
		return signDetail;
	}
	
	//the same but now the SignDetail object is already created
	public SignDetail newSignCreated(SignDetail signDetail)
	{
		//check if there is a signDetail object in the cache
		SignDetail cached = signs.get(getLocationKey(signDetail));
		
		
		if (cached != null)
		{
			//the object is in the cache
			invalidateCacheLocation(cached);
			signs.remove(getLocationKey(cached));
		}
		
		signs.put(getLocationKey(signDetail), signDetail);
		invalidateCacheLocation(signDetail);
		
		return signDetail;
	}
	
	
	public void existingSignDestroyed(Sign sign)
	{
		SignDetail signDetail = getCachedSignDetail(sign.getLocation());
		
		if (signDetail != null)
		{
			signs.remove(getLocationKey(signDetail));
			if (signDetail.isLiftSign())
			{
				invalidateCacheLocation(signDetail);
			}
		}
	}
	
	private void invalidateCacheLocation(SignDetail signDetail)
	{
		for (SignDetail val : signs.values())
		{
			val.clearCache(signDetail);
		
		}
	}
	
	private String getLocationKey(final Location l)
	{
		return l.getWorld().getName() + "," + l.getBlockX() + ","  + l.getBlockY() + "," + l.getBlockZ();
		
	
	}
	
	private String getLocationKey(final SignDetail signDetail)
	{
		return getLocationKey(signDetail.getLocation());
		
	
	}
	
	
}
