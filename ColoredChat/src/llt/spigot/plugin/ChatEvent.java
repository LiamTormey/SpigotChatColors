package llt.spigot.plugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatEvent implements Listener {

	private Plugin _plugin; 
	private HashMap<Player, ChatColor> color_mappings; //#TODO mappings need to be cleared when players leave. should be moved to different handler class
	private ChatColor[] valid_colors; 
	
	public ChatEvent(Plugin plugin) {
		color_mappings = new HashMap<>(); 
		valid_colors =  new ChatColor[] {
				ChatColor.DARK_GREEN, ChatColor.GREEN, 
				ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE,
				ChatColor.BLUE, ChatColor.DARK_BLUE,
				ChatColor.DARK_RED, ChatColor.RED, 
				ChatColor.AQUA, ChatColor.DARK_AQUA
		};
		
				
		this._plugin = plugin; 
		
	}
	
	
	@EventHandler
	public void handleChatMessage(AsyncPlayerChatEvent event) { 
		
		
		Player sendingPlayer = event.getPlayer();
		String message = event.getMessage();
		Set<Player> recipients = event.getRecipients();
		
		//when the player first types a message, assign a color 
		if(!color_mappings.containsKey(sendingPlayer)) { 
			ChatColor randomColor = this.valid_colors[CommonMethods.getRandomNumber(0, this.valid_colors.length-1)];
			color_mappings.put(sendingPlayer, randomColor);
		}
		ChatColor playerColor = color_mappings.get(sendingPlayer);
		
		//function to fire when player sends message 
		Function func = new Function<Void, Void>() {
			@Override
			public Void apply(Void t) {
				System.out.println("func");
				TextComponent txt_name = new TextComponent(sendingPlayer.getName()); 
				txt_name.setColor(playerColor);
				txt_name.setBold(true);
				
				TextComponent txt_contents = new TextComponent(": " + message); 
				
				TextComponent txt_main = new TextComponent(); 
				txt_main.addExtra(txt_name);
				txt_main.addExtra(txt_contents);
				
				Bukkit.getServer().spigot().broadcast(txt_main);
				return null;
			}
		};
		
		//callable to make above func function async 
		Callable<Object> callable = new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				System.out.println("callable");
				func.apply(null);
				return null;
			}
		};
		
		//if async call the callable 
		if(event.isAsynchronous()) { 
			System.out.println("is async");
			Bukkit.getServer().getScheduler().callSyncMethod(this._plugin, callable);
		} else { //if not async, dont do callable 
			System.out.println("is sync");
			func.apply(null);
		}
		
		//cancel the org message 
		event.setCancelled(true);
			
			
			
			
			
			
		
	}
	
	
}
