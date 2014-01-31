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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.nyvaria.component.wrapper.cmd.NyvariaCommand;
import net.nyvaria.component.wrapper.cmd.NyvariaSubCommand;
import net.nyvaria.openanalytics.OpenAnalytics;
import net.nyvaria.openanalytics.cmd.analytics.admin.SetSubCommand;

/**
 * @author Paul Thompson
 *
 */
public class AdminSubCommand extends NyvariaSubCommand {
	public static final String CMD_ADMIN  = "admin";
	public static final String PERM_ADMIN = OpenAnalytics.PERM_ROOT + ".admin";
	
	private List<NyvariaSubCommand> subcmds;
	
	public AdminSubCommand(NyvariaCommand parentCmd) {
		super(parentCmd);
		subcmds = new ArrayList<NyvariaSubCommand>();
		subcmds.add(new SetSubCommand(parentCmd, this));
	}
	
	@Override
	public boolean match(String subCmdName) {
		return subCmdName.equalsIgnoreCase(CMD_ADMIN);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String[] args, int nextArgIndex) {
		if (!NyvariaCommand.hasCommandPermission(sender, PERM_ADMIN)) {
			return true;
		}
		
		// Check if we have enough arguments
		if (args.length < nextArgIndex+1) {
			usage(sender, cmd, args, nextArgIndex);
			return false;
		}
		
		// Get the sub-command name
		String subCmdName = args[nextArgIndex];
		
		// Iterate through the sub-commands
		for (NyvariaSubCommand subcmd : subcmds) {
			if (subcmd.match(subCmdName)) {
				return subcmd.onCommand(sender, cmd, args, nextArgIndex+1);
			}
		}
		
		// Must not have matched a sub-command, show the usage
		for (NyvariaSubCommand subcmd : subcmds) {
			subcmd.usage(sender, cmd, args, nextArgIndex+1);
		}
		return true;
	}

	@Override
	public void usage(CommandSender sender, Command cmd, String[] args, int nextArgIndex) {
		if (!sender.hasPermission(PERM_ADMIN)) {
			return;
		}
		
		for (NyvariaSubCommand subcmd : subcmds) {
			subcmd.usage(sender, cmd, args, nextArgIndex+1);
		}
	}
}
