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

import java.lang.reflect.Field;
import java.util.List;

import net.nyvaria.googleanalytics.MeasurementProtocol;
import net.nyvaria.googleanalytics.hit.Hit;
import net.nyvaria.googleanalytics.Parameter;

/**
 * @author Paul Thompson
 *
 */
public class TransactionHit extends Hit {
	@Parameter(format="text", required=true, name=MeasurementProtocol.HIT_TYPE)
	private static final String HIT_TYPE = "transaction";
	
	/**************************/
	/* Transaction Parameters */
	/**************************/
	
	@Parameter(format="text",    required=true,   name=MeasurementProtocol.TRANSACTION_ID)
	private String transaction_id;
	
	@Parameter(format="text",     required=false, name=MeasurementProtocol.TRANSACTION_AFFILIATION)
	private String transaction_affiliation;
	
	@Parameter(format="text",     required=false, name=MeasurementProtocol.TRANSACTION_REVENUE)
	private String transaction_revenue;
	
	@Parameter(format="currency", required=false, name=MeasurementProtocol.TRANSACTION_SHIPPING)
	private Float transaction_shipping;
	
	@Parameter(format="currency", required=false, name=MeasurementProtocol.TRANSACTION_TAX)
	private Float transaction_tax;
	
	@Parameter(format="text",     required=false, name=MeasurementProtocol.CURRENCY_CODE)
	private String currency_code;
	
	/*************************/
	/* Constructor & Methods */
	/*************************/
	
	public TransactionHit(String client_id, String transaction_id) {
		super(client_id, TransactionHit.HIT_TYPE);
		this.transaction_id = transaction_id;
	}
	
	/* (non-Javadoc)
	 * @see net.nyvaria.googleanalytics.hit.Hit#getParameterList()
	 */
	@Override
	public List<String> getParameterList() {
		List<String> list = super.getParameterList();
		
		for (Field field : this.getClass().getFields()) {
			Parameter parameter = (Parameter) field.getAnnotation(Parameter.class);
			
			if (parameter != null) {
				Object value = null;
				
				try {
					value = field.get(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				String result = formatParameter(parameter, value);
				if (result != null) {
					list.add(result);
				}
			}
		}
		
		return list;
	}
}
