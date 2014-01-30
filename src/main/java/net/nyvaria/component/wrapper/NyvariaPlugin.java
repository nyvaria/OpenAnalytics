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
package net.nyvaria.component.wrapper;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Paul Thompson
 *
 */
public abstract class NyvariaPlugin extends JavaPlugin {
	public void log(String msg) {
		this.log(Level.INFO, msg);
	}
	
	public void log(String msg, Object ... args) {
		this.log(String.format(msg, args));
	}
	
	public void log(Level level, String msg) {
		this.getLogger().log(level, msg);
	}
	
	public void log(Level level, String msg, Object ... args) {
		this.log(level, String.format(msg, args));
	}
	
	protected String getNameAndVersion() {
		return this.getName() + " v" + this.getDescription().getVersion();
	}
}
