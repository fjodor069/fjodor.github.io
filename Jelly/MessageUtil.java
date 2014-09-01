package nl.Jelly;

import java.util.Locale;

import org.bukkit.command.CommandSender;

public class MessageUtil 
{
//	private Locale locale;
	
	public MessageUtil(Locale locale)
	{
	//	this.locale = locale;
	}
	
	public void sendLocalizedMessage(CommandSender target, String msgKey, Object... args)
	{
		target.sendMessage(msgKey);
	}

}
