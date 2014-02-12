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
package net.nyvaria.openanalytics.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.nyvaria.googleanalytics.MeasurementProtocolClient;
import net.nyvaria.googleanalytics.hit.Hit;
import net.nyvaria.googleanalytics.hit.ecommerce.TransactionHit;
import net.nyvaria.googleanalytics.hit.ecommerce.TransactionItemHit;
import net.nyvaria.openanalytics.OpenAnalytics;
import net.nyvaria.openanalytics.client.Client;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.wargamer2010.signshop.events.SSPostTransactionEvent;

/**
 * @author Paul Thompson
 *
 */
public class SignShopListener implements Listener {
	private static SignShopListener instance;

	public SignShopListener(OpenAnalytics plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public static SignShopListener getInstance() {
		return instance;
	}
	
    @EventHandler(priority = EventPriority.MONITOR)
    public void onSSPostTransactionEvent(SSPostTransactionEvent event) {
		List<Hit> hitList = new ArrayList<Hit>();
		
		// Find the player
		Client client = OpenAnalytics.getInstance().getClientList().get(event.getPlayer().getPlayer());
		
		// Proceed if the player is not opted out
		if (client.isOptedIn()) {
	    	
			// Create transaction hits
			TransactionHit transactionHit = new TransactionHit(client, UUID.randomUUID().toString());
			transactionHit.transaction_revenue = event.getPrice();
			transactionHit.transaction_affiliation = event.getOwner().getName();
			transactionHit.currency_code = "USD";
			
			// Add the transaction hit to our hit list
			hitList.add(transactionHit);
			
			// Count the total items
			int totalItems = 0;
			for (ItemStack stack : event.getItems()) {
				totalItems += stack.getAmount();
			}
			
			// Derive the per item price
			float itemPrice = event.getPrice() / totalItems;
			
			// Create transaction item hits
			for (ItemStack stack : event.getItems()) {
				// Create the transaction item hit
				TransactionItemHit itemHit = new TransactionItemHit(client, transactionHit.transaction_id, stack.getType().name());
				itemHit.item_code = stack.getType().name();
				itemHit.item_quantity = stack.getAmount();
				itemHit.item_price = itemPrice;
				
	    		// Add the transaction item hit to our hit list
	    		hitList.add(itemHit);
			}
			
			// Sent the hit list
			MeasurementProtocolClient.getInstance().sendAsynchronously(hitList);
		}
	}
}
