package nl.Jelly;
// this is a minecraft server plugin 
//
// for a Bukkit server 
//
// example see wiki.bukkit.org/plugin_tutorial
//
// and https://github.com//Milkbowl/Vault
//
// added liftsign code

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Jelly extends JavaPlugin 
{
	private static final Logger log = Logger.getLogger("Minecraft");

		
	//
	//lift members
			private SignCache cache;
	
			private Util util;

	
	@Override
	public void onDisable()
	{
		log.info(String.format("[%s] - Disabled Version %s ",getDescription().getName(),getDescription().getVersion()));
		
	}

	@Override
	public void onEnable()
	{
		//note : this assumes config.yml is present 
		// and creates a file in the plugin folder
		this.saveDefaultConfig();
		
		PluginManager pm = this.getServer().getPluginManager();
		
		//init 
		cache = new SignCache();
		//if (cache != null)
		//  log.info("onEnable: sign cache created");
		
		//register listeners
		pm.registerEvents(new PlayerListener(this), this);
		pm.registerEvents(new BlockListener(this),this);
		//register your commands
	//	getCommand("pos").setExecutor(new PosCommand());
		getCommand("ignite").setExecutor(new IgniteCommand());
		getCommand("sign").setExecutor(new SignCommand(this));
		getCommand("rules").setExecutor(new RulesCommand(this));
		
		log.info(String.format("[%s] Enabled Version %s ", getDescription().getName(),getDescription().getVersion()));
		
		
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
