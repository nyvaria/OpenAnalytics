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
package net.nyvaria.hook;

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.onarandombox.MultiverseCore.MultiverseCore;

/**
 * @author Paul Thompson
 *
 */
public class MultiverseHook {
	private final JavaPlugin plugin;
    private static MultiverseCore multiverseCore = null;
    
    private MultiverseHook(JavaPlugin plugin) {
    	this.plugin = plugin;
    	
        // Try to hook Multiverse-Core
        Plugin multiversePlugin = this.plugin.getServer().getPluginManager().getPlugin("Multiverse-Core");
        
        if (multiversePlugin != null) {
        	this.plugin.getLogger().log(Level.INFO, "Multiverse-Core detected:" + multiversePlugin.getDescription().getVersion());
        	multiverseCore = (MultiverseCore) multiversePlugin;
        }
    }
    
	public static MultiverseHook initialise(JavaPlugin plugin) {
		return new MultiverseHook(plugin);
	}
	
    public static String getWorldAlias(String name) {
    	String alias = name;
    	
    	if (multiverseCore != null) {
    		alias = multiverseCore.getMVWorldManager().getMVWorld(alias).getAlias();
    	}
    	
    	return alias;
    }
    
    public static String getWorldAlias(World world) {
    	return getWorldAlias(world.getName());
    }
    
    public static String getWorldAlias(Location location) {
    	return getWorldAlias(location.getWorld().getName());
    }
    
    public static String getWorldAlias(Player player) {
    	return getWorldAlias(player.getLocation().getWorld().getName());
    }
}
