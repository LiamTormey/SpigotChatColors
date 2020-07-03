package llt.spigot.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	@Override
	public void onEnable() {
		super.onEnable();
		getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	
	
}
