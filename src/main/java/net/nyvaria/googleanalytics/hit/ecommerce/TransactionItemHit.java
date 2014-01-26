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
package net.nyvaria.googleanalytics.hit.ecommerce;

import java.util.List;

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.googleanalytics.hit.Hit;
import net.nyvaria.url.parameter.CurrencyParameter;
import net.nyvaria.url.parameter.IntegerParameter;
import net.nyvaria.url.parameter.Parameter;
import net.nyvaria.url.parameter.TextParameter;

/**
 * @author Paul Thompson
 *
 */
public class TransactionItemHit extends Hit {
	private static final TextParameter HIT_TYPE = new TextParameter(MeasurementProtocol.HIT_TYPE_PARAMETER, "item");
	
	// Transaction Parameters
	private TextParameter     transaction_id;
	private TextParameter     currency_code;
	
	// Item Parameters
	private TextParameter     item_name;
	private CurrencyParameter item_price;
	private IntegerParameter  item_quantity;
	private TextParameter     item_code;
	private TextParameter     item_category;
	
	public TransactionItemHit(TextParameter client_id, String transaction_id, String item_name) {
		super(client_id, TransactionItemHit.HIT_TYPE);
		this.setTransactionID(transaction_id);
		this.setItemName(item_name);
	}
	
	/* (non-Javadoc)
	 * @see net.nyvaria.googleanalytics.hit.Hit#getParameterList()
	 */
	@Override
	public List<Parameter> getParameterList() {
		List<Parameter> list = super.getParameterList();
		
		// Add the required parameters
		list.add(transaction_id);
		list.add(item_name);
		
		// Add the optional parameters
		if (currency_code != null) list.add(currency_code);
		if (item_name     != null) list.add(item_name);
		if (item_price    != null) list.add(item_price);
		if (item_quantity != null) list.add(item_quantity);
		if (item_code     != null) list.add(item_code);
		if (item_category != null) list.add(item_category);
		
		return list;
	}

	// Transaction Parameters
	public void setTransactionID(String transaction_id) {
		this.transaction_id = new TextParameter(MeasurementProtocol.TRANSACTION_ID_PARAMETER, transaction_id);
	}
	
	public void setCurrencyCode(String currency_code) {
		this.currency_code = new TextParameter(MeasurementProtocol.CURRENCY_CODE_PARAMETER, currency_code);
	}
	
	// Item Parameters
	public void setItemName(String item_name) {
		this.item_name = new TextParameter(MeasurementProtocol.ITEM_NAME_PARAMETER, item_name);
	}
	
	public void setItemPrice(float item_price) {
		this.item_price = new CurrencyParameter(MeasurementProtocol.ITEM_PRICE_PARAMETER, item_price);
	}
	
	public void setItemQuantity(int item_quantity) {
		this.item_quantity = new IntegerParameter(MeasurementProtocol.ITEM_QUANTITY_PARAMETER, item_quantity);
	}
	
	public void setItemCode(String item_code) {
		this.item_code = new TextParameter(MeasurementProtocol.ITEM_CODE_PARAMETER, item_code);
	}
	
	public void setItemCategory(String item_category) {
		this.item_category = new TextParameter(MeasurementProtocol.ITEM_CATEGORY_PARAMETER, item_category);
	}
}
