package nl.Jelly;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class Util 
{
	//private MessageUtil messageUtil;
	
	public static final String MSG_NO_PERMISSION = "no permission";
	public static final String MSG_DESTINATION_NOT_SAFE = "destination not safe";
	public static final String MSG_NO_VALID_LIFT_TARGET = "no valid lift target";
	public static final String MSG_NO_PERM_CREATE_LIFT_SIGN = "no permission to create lift sign";
	public static final String MSG_UP_ONE_FLOOR = "up one floor";
	public static final String MSG_DOWN_ONE_FLOOR = "down one floor";
	
	
	public Util(MessageUtil messageUtil)
	{
		//this.messageUtil = messageUtil;
	}
	
	//
	// check if the block is a sign and return a sign state object
	
	public static Sign getSignState(Block b)
	{
		Sign sign = null;
		
		final Material typeId = b.getType();
		
		
		if (typeId == Material.SIGN_POST  ||  typeId == Material.WALL_SIGN)
		{
			//cast the block to a Sign object
			BlockState bs = b.getState();
			sign = (Sign)bs;
		}
		
		return sign;
	}
	
	public String getBlockName(Block b)
	{
		
		return b.getType().toString();
		
			
		
	}
	
	
}
