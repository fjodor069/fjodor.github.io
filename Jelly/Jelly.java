package io.github.fjodor;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Jelly extends JavaPlugin
{
	private static final Logger log = Logger.getLogger("Minecraft");
	
	private SignCache cache;
	private Util util;
	
	@Override
	public void onEnable()
	{
		this.saveDefaultConfig();
		
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(this), this);
		
		cache = new SignCache();
		
		getCommand("rules").setExecutor(new RulesCommand(this));
		
		
		log.info(String.format("[%s] - Enabled version %s", 
				getDescription().getName(),getDescription().getVersion() ));
		
		
    
    }
	
	@Override
	public void onDisable()
	{
		log.info(String.format("[%s] - Disabled version %s", 
				getDescription().getName(),getDescription().getVersion() ));
		
	}
	
	public SignCache getSignCache()
	{
		return cache;
	}
	
	public Util getUtil()
	{
		return util;
	}

}
