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
package net.nyvaria.openanalytics.cmd;

import java.util.ArrayList;
import java.util.List;

import net.nyvaria.component.wrapper.cmd.NyvariaCommand;
import net.nyvaria.component.wrapper.cmd.NyvariaSubCommand;
import net.nyvaria.openanalytics.cmd.analytics.AdminSubCommand;
import net.nyvaria.openanalytics.cmd.analytics.OptOutSubCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

/**
 * @author Paul Thompson
 *
 */
public class AnalyticsCommand extends NyvariaCommand implements CommandExecutor, TabCompleter {
	public static final String CMD = "analytics";
	private List<NyvariaSubCommand> subcmds;
	private final int nextArgIndex = 0;
	
	public AnalyticsCommand() {
		subcmds = new ArrayList<NyvariaSubCommand>();
		subcmds.add(new AdminSubCommand(this));
		subcmds.add(new OptOutSubCommand(this));
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		return NyvariaCommand.onTabCompleteForSubCommands(sender, cmd, subcmds, args, nextArgIndex);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Check if we have enough arguments
		if (args.length < 1) {
			usage(sender, cmd, args);
			return true;
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
		usage(sender, cmd, args);
		return true;
	}
	
	public void usage(CommandSender sender, Command cmd, String[] args) {
		for (NyvariaSubCommand subcmd : subcmds) {
			subcmd.usage(sender, cmd, args, nextArgIndex+1);
		}
	}
}
