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
package net.nyvaria.openanalytics.cmd.analytics.admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.nyvaria.component.wrapper.cmd.NyvariaCommand;
import net.nyvaria.component.wrapper.cmd.NyvariaSubCommand;
import net.nyvaria.openanalytics.OpenAnalytics;
import net.nyvaria.openanalytics.OpenAnalyticsConfig;
import net.nyvaria.openanalytics.cmd.AnalyticsCommand;
import net.nyvaria.openanalytics.cmd.analytics.AdminSubCommand;

/**
 * @author Paul Thompson
 *
 */
public class SetSubCommand extends NyvariaSubCommand {
	public static final String CMD_SET = "set";
	
	public SetSubCommand(NyvariaCommand parentCmd, NyvariaSubCommand parentSubCmd) {
		super(parentCmd, parentSubCmd);
	}
	
	@Override
	public boolean match(String subCmdName) {
		if (subCmdName == null) return false;
		return subCmdName.equalsIgnoreCase(CMD_SET);
	}
	
	@Override
	public List<String> getCommands() {
		return getCommands(null);
	}
	
	@Override
	public List<String> getCommands(String prefix) {
		List<String> commands = new ArrayList<String>();
		if ((prefix == null) || CMD_SET.startsWith(prefix.toLowerCase())) commands.add(CMD_SET);
		return commands;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String[] args, int nextArgIndex) {
		List<String> completions = new ArrayList<String>();
		
		// Check if we have enough arguments
		if ((args.length < nextArgIndex+1) || args[nextArgIndex].isEmpty()) {
			completions.add(OpenAnalyticsConfig.HOST_NAME);
			completions.add(OpenAnalyticsConfig.TRACKING_ID);
			completions.add(OpenAnalyticsConfig.USE_METRICS);
			
		} else if (args.length < nextArgIndex+2) {
			String option = args[nextArgIndex].toLowerCase();
			
			if (option.equalsIgnoreCase(OpenAnalyticsConfig.HOST_NAME) || option.equals(OpenAnalyticsConfig.TRACKING_ID) || option.equalsIgnoreCase(OpenAnalyticsConfig.USE_METRICS)) {
				return completions;
			}
			
			if (OpenAnalyticsConfig.HOST_NAME.startsWith(option)) {
				completions.add(OpenAnalyticsConfig.HOST_NAME);
			} else if (OpenAnalyticsConfig.TRACKING_ID.startsWith(option)) {
				completions.add(OpenAnalyticsConfig.TRACKING_ID);
			} else if (OpenAnalyticsConfig.USE_METRICS.startsWith(option)) {
				completions.add(OpenAnalyticsConfig.USE_METRICS);
			}
			
		} else {
			String option = args[nextArgIndex].toLowerCase();
			String value  = args[nextArgIndex+1].toLowerCase();
			
			if (option.equalsIgnoreCase(OpenAnalyticsConfig.USE_METRICS)) {
				if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
					return completions;
				}
				
				if (value.isEmpty()) {
					completions.add("true");
					completions.add("false");
				} else if ("true".startsWith(value)) {
					completions.add("true");
				} else if ("false".startsWith(value)) {
					completions.add("false");
				}
			}
		}
		
	    return completions;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String[] args, int nextArgIndex) {
		// No permission check as that will already have been done
		
		// Check if we have enough arguments
		if (args.length < nextArgIndex+2) {
			usage(sender, cmd, args, nextArgIndex);
			return true;
		}
		
		// Get the sub-command name
		String option = args[nextArgIndex];
		String value  = args[nextArgIndex+1];
		
		// Check the valid list of options
		if (option.equalsIgnoreCase(OpenAnalyticsConfig.TRACKING_ID)) {
			OpenAnalyticsConfig.setTrackingID(value);
			OpenAnalytics.getInstance().getTracker().initializeMeasurementProtocol();
			
		} else if (option.equalsIgnoreCase(OpenAnalyticsConfig.HOST_NAME)) {
			OpenAnalyticsConfig.setHostName(value);
			OpenAnalytics.getInstance().getTracker().initializeMeasurementProtocol();
			
		} else if (option.equalsIgnoreCase(OpenAnalyticsConfig.USE_METRICS)) {
			if (value.equalsIgnoreCase("true")) {
				OpenAnalyticsConfig.setUseMetrics(true);
			} else if (value.equalsIgnoreCase("false")) {
				OpenAnalyticsConfig.setUseMetrics(false);
			} else {
				// TODO: Do something here
			}
			
		} else {
			usage(sender, cmd, args, nextArgIndex);
		}
		
		return true;
	}

	@Override
	public void usage(CommandSender sender, Command cmd, String[] args, int nextArgIndex) {
		sender.sendMessage(ChatColor.YELLOW + String.format("Usage: /%1$s %2$s %3$s %4$s %5$s", AnalyticsCommand.CMD, AdminSubCommand.CMD_ADMIN, CMD_SET, OpenAnalyticsConfig.TRACKING_ID, "UA-XXXXXXXX-Y"));
		sender.sendMessage(ChatColor.YELLOW + String.format("Usage: /%1$s %2$s %3$s %4$s %5$s", AnalyticsCommand.CMD, AdminSubCommand.CMD_ADMIN, CMD_SET, OpenAnalyticsConfig.HOST_NAME,   "server.com"));
		sender.sendMessage(ChatColor.YELLOW + String.format("Usage: /%1$s %2$s %3$s %4$s %5$s", AnalyticsCommand.CMD, AdminSubCommand.CMD_ADMIN, CMD_SET, OpenAnalyticsConfig.USE_METRICS, "<true|false>"));
	}
}
