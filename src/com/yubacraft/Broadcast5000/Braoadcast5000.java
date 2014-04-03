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
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
			/**
			 * Scheduler that broadcasts
			 */
			@Override
			public void run(){
				Random rand = new Random();
				int max = getConfig().getInt("how-many-messages");
				int n = rand.nextInt(max) + 1;
				broadcast(n);
			}
		}, this.getConfig().getInt("start-message-delay") * 20, this.getConfig().getInt("message-delay") * 20);
	}
	
	/**
	 * The universal method to chop up the config to a point where
	 * it can broadcast a message
	 * @param whichOne
	 */
	public void broadcast(int whichOne){
		String header = this.getConfig().getString("prefix");
		int i = whichOne;
		String ii = Integer.toString(i);
		String message = this.getConfig().getString(ii).replace('&', '§');
		// Check if the prefix is 'none'
		if (header.equalsIgnoreCase("none")){
			if (message.split("%new").length == 1){
				Bukkit.getServer().broadcastMessage(message);
				return;
			}
			else{
				for (String message2 : message.split("%new")){
					Bukkit.getServer().broadcastMessage(message2);
				}
				return;
			}
		}
		//If the config isn't set to 'none'
		else{
			if (message.split("%new").length == 1){
				Bukkit.getServer().broadcastMessage(header.replace('&', '§') + " " + message);
				return;
			}
			else{
				for (String message2 : message.split("%new")){
					Bukkit.getServer().broadcastMessage(header.replace('&', '§') + " " + message2);
				}
				return;
			}
		}
	}
	
	/**
	 * Broadcast a message manually
	 * @param message
	 */
	public void broadcastManual (String message){
		String header = this.getConfig().getString("prefix");
		String message2 = message.replace('&', '§');
		// Check if the prefix is 'none'
		if (header.equalsIgnoreCase("none")){
			if (message.split("%new").length == 1){
				Bukkit.getServer().broadcastMessage(message2);
				return;
			}
			else{
				for (String message3 : message.split("%new")){
					Bukkit.getServer().broadcastMessage(message3);
				}
				return;
			}
		}
		//If the config isn't set to 'none'
		else{
			if (message.split("%new").length == 1){
				Bukkit.getServer().broadcastMessage(header.replace('&', '§') + " " + message2);
				return;
			}
			else{
				for (String message3 : message.split("%new")){
					Bukkit.getServer().broadcastMessage(header.replace('&', '§') + " " + message3);
				}
				return;
			}
		}
	}
	
	/**
	 * A method to see if a number is higher than the
	 * highest broadcast number
	 * @param i
	 * @return
	 */
	public boolean tooHigh (int i){
		if (i < this.getConfig().getInt("how-many-messages")){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * All Commands
	 */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		/**
		 * Command to directly load a preloaded message
		 */
		if (cmd.getName().equalsIgnoreCase("bc")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				if (player.hasPermission("broadcast5000.bc")){
					try {
						Integer.parseInt(args[0]);
					}
					catch (NumberFormatException e){
						return false;
					}
					int i = Integer.parseInt(args[0]);//TODO Check if it is an int first
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
			return false;
		}
		/**
		 * Say direct command
		 */
		if (cmd.getName().equalsIgnoreCase("say")){
			if (sender instanceof Player){
				Player player = (Player) sender;
				if (player.hasPermission("broadcast5000.say")){
					if (args.length >= 1){
						StringBuilder string = new StringBuilder();
						for (int n = 0; n < args.length; n++){
							string.append(args[n] + " ");
						}
						String message = string.toString();
						broadcastManual(message);
						return true;
					}
				}
			}
			else{
				if (args.length >= 1){
					StringBuilder string = new StringBuilder();
					for (int n = 0; n < args.length; n++){
						string.append(args[n] + " ");
					}
					String message = string.toString();
					broadcastManual(message);
					return true;
				}
			}
		}
		return false;
	}
}
