/**
 * Copyright (c) 2013-2014
 * Paul Thompson <captbunzo@gmail.com> / Nyvaria <geeks@nyvaria.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 */
package net.nyvaria.openanalytics.cmd.analytics;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import net.nyvaria.component.wrapper.NyvariaPlayer;
import net.nyvaria.component.wrapper.cmd.NyvariaCommand;
import net.nyvaria.component.wrapper.cmd.NyvariaSubCommand;
import net.nyvaria.openanalytics.OpenAnalytics;
import net.nyvaria.openanalytics.client.Client;
import net.nyvaria.openanalytics.cmd.AnalyticsCommand;

/**
 * @author Paul Thompson
 *
 */
public class OptOutSubCommand extends NyvariaSubCommand {
	public static final String CMD_OPTOUT = "optout";
	public static final String CMD_OPTIN  = "optin";
	
	public static final String   PERM_OPTOUT       = OpenAnalytics.PERM_ROOT + ".optout";
	public static final String   PERM_OPTOUT_OTHER = OpenAnalytics.PERM_ROOT + ".optout.other";
	
	public OptOutSubCommand(NyvariaCommand parentCmd) {
		super(parentCmd);
	}
	
	@Override
	public boolean match(String subCmdName) {
		return subCmdName.equalsIgnoreCase(CMD_OPTOUT) || subCmdName.equalsIgnoreCase(CMD_OPTIN);
	}
	
	private boolean getOptOutInFromSubCmd(String subCmdName) {
		return subCmdName.equalsIgnoreCase(CMD_OPTOUT);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String[] args, int nextArgIndex) {
		String cmdName = args[nextArgIndex-1];
		boolean optout = getOptOutInFromSubCmd(cmdName);
		
		// Check if the sender has permission to run this command
		if (!NyvariaCommand.hasCommandPermission(sender, PERM_OPTOUT)) {
			return true;
		}
		
		// Process commands lacking a player argument
		if (args.length == nextArgIndex) {
			if (sender instanceof Player) {
				sender.sendMessage(ChatColor.YELLOW + String.format("Setting %1$s to %2$s of analytics", "yourself", cmdName));
				Client.setOptOut((Player) sender, optout);
				return true;
			}
			
		// We have a player name argument and the sender can opt out/in other people
		} else if ((args.length == nextArgIndex+1) && sender.hasPermission(PERM_OPTOUT_OTHER)) {
			String partialPlayerName = args[nextArgIndex];
			List<OfflinePlayer> matches = NyvariaPlayer.matchOfflinePlayer(partialPlayerName, sender);
			
			if (matches.size() == 1) {
				OfflinePlayer match = matches.get(0);
				Client client;
				
				if (match.isOnline()) {
					client = OpenAnalytics.getInstance().getClientList().get(match);
				} else {
					client = new Client(match);
				}
				
				sender.sendMessage(ChatColor.YELLOW + String.format("Setting %1$s to %2$s of analytics", client.getWrappedName() + ChatColor.YELLOW, cmdName));
				Client.setOptOut(matches.get(0), optout);
			}
			
			return true;
		}
		
		// If we have gotten this far, then there was a problem with the command usage
		usage(sender, cmd, args, nextArgIndex);
		return true;
	}

	@Override
	public void usage(CommandSender sender, Command cmd, String[] args, int nextArgIndex) {
		if (!sender.hasPermission(PERM_OPTOUT)) {
			return;
		}
		
		String cmdName = (args.length >= nextArgIndex ? args[nextArgIndex-1] : null);
		
		if ((cmdName == null) || !match(cmdName)) {
			cmdName = "<" + CMD_OPTOUT + "|" + CMD_OPTIN + ">";
		}
		
		// Player usage
		if (sender instanceof Player) {
			if (sender.hasPermission(PERM_OPTOUT_OTHER)) {
				sender.sendMessage(ChatColor.YELLOW + String.format("Usage: /%1$s %2$s [<player>]", AnalyticsCommand.CMD, cmdName));
			} else {
				sender.sendMessage(ChatColor.YELLOW + String.format("Usage: /%1$s %2$s", AnalyticsCommand.CMD, cmdName));
			}
			return;
		}
		
		// Console usage
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage(ChatColor.YELLOW + String.format("Console Usage: /%1$s %2$s <player>", AnalyticsCommand.CMD, cmdName));
			return;
		}
	}
}
