package com.yubacraft.Broadcast5000;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Braoadcast5000 extends JavaPlugin{
	
	public void onEnable(){
		saveDefaultConfig();
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		@SuppressWarnings("unused")
		int delay = getConfig().getInt("message-delay") * 20;
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run(){
				Random rand = new Random();
				int max = getConfig().getInt("how-many-messages");
				int n = rand.nextInt(max) + 1;
				broadcast(n);
			}
		}, this.getConfig().getInt("start-message-delay") * 20, this.getConfig().getInt("message-delay") * 20);
	}
	
	public void onDisable(){
		//TODO Code for when the plugin is disabled
	}
	
	public void broadcast(int whichOne){
		String header = this.getConfig().getString("prefix").replace('&', '§');
		int i = whichOne;
		String ii = Integer.toString(i);
		String message = this.getConfig().getString(ii).replace('&', '§');
		if (header == "none"){
			Bukkit.getServer().broadcastMessage(message);
		}
		Bukkit.getServer().broadcastMessage(header + " " + message);
	}
	
	public boolean tooHigh (int i){
		if (i < this.getConfig().getInt("how-many-messages")){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("bc")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				if (player.hasPermission("broadcast5000.bc")){
					int i = Integer.parseInt(args[0]);
					int i2 = this.getConfig().getInt("how-many-messages");
					if(i > 0 && 1 <= i2){
						int i3 = Integer.parseInt(args[0]);
						broadcast(i3);
						return true;
					}
					else{
						player.sendMessage(ChatColor.RED + "Either too many or not enough. The second parameter should me the number of a message that exists.");
						return true;
					}
				}
			}
			else{
				int i = Integer.parseInt(args[0]);
				int i2 = this.getConfig().getInt("how-many-messages");
				if(i > 0 && 1 <= i2){
					int i3 = Integer.parseInt(args[0]);
					broadcast(i3);
					return true;
				}
				else{
					sender.sendMessage(ChatColor.RED + "Either too many or not enough. The second parameter should me the number of a message that exists.");
					return true;
				}
			}
		}
		return false;
	}
}
