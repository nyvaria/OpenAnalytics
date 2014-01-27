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

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.googleanalytics.hit.Hit;
import net.nyvaria.googleanalytics.Parameter;

/**
 * @author Paul Thompson
 *
 */
public class TransactionItemHit extends Hit {
	@Parameter(format="text", required=true, name=MeasurementProtocol.HIT_TYPE)
	private static final String HIT_TYPE = "item";
	
	/**************************/
	/* Transaction Parameters */
	/**************************/
	
	@Parameter(format="text", required=true,  name=MeasurementProtocol.TRANSACTION_ID)
	public String transaction_id;
	
	@Parameter(format="text", required=false, name=MeasurementProtocol.CURRENCY_CODE)
	public String currency_code;
	
	/*******************/
	/* Item Parameters */
	/*******************/
	
	@Parameter(format="text",    required=true,  name=MeasurementProtocol.ITEM_NAME)
	public String item_name;
	
	@Parameter(format="float",   required=false, name=MeasurementProtocol.ITEM_PRICE)
	public Float item_price;
	
	@Parameter(format="integer", required=false, name=MeasurementProtocol.ITEM_QUANTITY)
	public Integer item_quantity;
	
	@Parameter(format="text",    required=false, name=MeasurementProtocol.ITEM_CODE)
	public String item_code;
	
	@Parameter(format="text",    required=false, name=MeasurementProtocol.ITEM_CATEGORY)
	public String item_category;
	
	/*************************/
	/* Constructor & Methods */
	/*************************/
	
	public TransactionItemHit(String client_id, String transaction_id, String item_name) {
		super(client_id, TransactionItemHit.HIT_TYPE);
		this.transaction_id = transaction_id;
		this.item_name      = item_name;
	}
}
