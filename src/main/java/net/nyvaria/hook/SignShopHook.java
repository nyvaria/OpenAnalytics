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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.googleanalytics.hit.Hit;
import net.nyvaria.googleanalytics.hit.ecommerce.TransactionHit;
import net.nyvaria.googleanalytics.hit.ecommerce.TransactionItemHit;
import net.nyvaria.openanalytics.OpenAnalytics;
import net.nyvaria.openanalytics.client.Client;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.wargamer2010.signshop.SignShop;
import org.wargamer2010.signshop.events.SSPostTransactionEvent;

/**
 * @author Paul Thompson
 *
 */
public class SignShopHook {
	private final JavaPlugin plugin;
    private static SignShop signshop = null;
	
    private SignShopHook(JavaPlugin plugin) {
    	this.plugin = plugin;
    	
        // Try to hook SignShop
        Plugin signshopPlugin = this.plugin.getServer().getPluginManager().getPlugin("SignShop");
        
        if (signshopPlugin != null) {
        	this.plugin.getLogger().log(Level.INFO, "SignShop detected:" + signshopPlugin.getDescription().getVersion());
        	signshop = (SignShop) signshopPlugin;
        }
    }
    
	public static SignShopHook initialise(JavaPlugin plugin) {
		return new SignShopHook(plugin);
	}
	
    @EventHandler(priority = EventPriority.MONITOR)
    public void onSSPostTransactionEvent(SSPostTransactionEvent event) {
    	if (signshop != null) {
    		List<Hit> hitList = new ArrayList<Hit>();
    		
    		// Find the player
    		Client client = OpenAnalytics.getInstance().clientList.get(event.getPlayer().getPlayer());
    		
    		// Create transaction hits
    		TransactionHit transactionHit = new TransactionHit(client, UUID.randomUUID().toString());
    		transactionHit.transaction_affiliation = event.getOwner().getName(); // Should we do this?
    		transactionHit.transaction_revenue = event.getPrice();
    		transactionHit.currency_code = "USD";
    		
    		// Add the transaction hit to our hit list
    		hitList.add(transactionHit);
    		
    		// Create transaction item hits
    		for (ItemStack stack : event.getItems()) {
    			// Create the transaction item hit
    			TransactionItemHit itemHit = new TransactionItemHit(client, transactionHit.transaction_id, stack.getType().name());
    			itemHit.item_code = String.format("%d", stack.getType().hashCode());
    			itemHit.item_quantity = stack.getAmount();
    			
        		// Add the transaction item hit to our hit list
        		hitList.add(itemHit);
    		}
    		
    		// Sent the hit list
    		MeasurementProtocolClient.getInstance().sendAsynchronously(hitList);
    	}
    }
}
