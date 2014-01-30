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
package net.nyvaria.openanalytics.command;

import java.util.Arrays;

import net.nyvaria.component.wrapper.NyvariaCommand;
import net.nyvaria.openanalytics.OpenAnalytics;
import net.nyvaria.openanalytics.client.Client;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author pault
 *
 */
public class AnalyticsCommand extends NyvariaCommand implements CommandExecutor {
	public static final String CMD = "analytics";
	
	public static final String PERMISSION_ADMIN  = OpenAnalytics.PERMISSION_BASE + ".admin";
	public static final String PERMISSION_OPTOUT = OpenAnalytics.PERMISSION_BASE + ".optout";
	
	/* (non-Javadoc)
	 * @see org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender, org.bukkit.command.Command, java.lang.String, java.lang.String[])
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		// Check if we have enough arguments
		if (args.length < 1) {
			return false;
		}
		
		String   subcmd     = args[0];
		String[] subcmdArgs = (args.length == 1 ? new String[0] : Arrays.copyOfRange(args, 1, args.length-1));
		
		// The admin sub-command
		if (subcmd.equalsIgnoreCase("admin")) {
			return this.onCommandAdmin(sender, cmd, subcmd, label, subcmdArgs);
		}
		
		// The optout sub-command
		if (subcmd.equalsIgnoreCase("optout") || subcmd.equalsIgnoreCase("opt-out")) {
			return this.onCommandOptout(sender, cmd, subcmd, label, subcmdArgs, true);
		}
		
		// The optin sub-command
		if (subcmd.equalsIgnoreCase("optin") || subcmd.equalsIgnoreCase("opt-in")) {
			return this.onCommandOptout(sender, cmd, subcmd, label, subcmdArgs, false);
		}
		
		// Must not have matched a command
		return false;
	}
	
	private boolean onCommandAdmin(CommandSender sender, Command cmd, String subcmd, String label, String[] args) {
		if (!NyvariaCommand.hasCommandPermission(sender, AnalyticsCommand.PERMISSION_ADMIN)) {
			return true;
		}
		
		return true;
	}
	
	private boolean onCommandOptout(CommandSender sender, Command cmd, String subcmd, String label, String[] args, boolean optout) {
		if (!NyvariaCommand.hasCommandPermission(sender, AnalyticsCommand.PERMISSION_OPTOUT)) {
			return true;
		}
		
		if (args.length == 0) {
			Client.setOptOut((Player) sender, optout);
			return true;
		}
		
		return true;
	}
}
