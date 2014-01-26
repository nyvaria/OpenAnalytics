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
import net.nyvaria.url.parameter.Parameter;
import net.nyvaria.url.parameter.TextParameter;

/**
 * @author Paul Thompson
 *
 */
public class TransactionHit extends Hit {
	private static final TextParameter HIT_TYPE = new TextParameter(MeasurementProtocol.HIT_TYPE_PARAMETER, "transaction");
	
	// Transaction Parameters
	private TextParameter     transaction_id;
	private TextParameter     transaction_affiliation;
	private TextParameter     transaction_revenue;
	private CurrencyParameter transaction_shipping;
	private CurrencyParameter transaction_tax;
	private TextParameter     currency_code;
	
	public TransactionHit(TextParameter client_id, String transaction_id) {
		super(client_id, TransactionHit.HIT_TYPE);
		this.setTransactionID(transaction_id);
	}
	
	/* (non-Javadoc)
	 * @see net.nyvaria.googleanalytics.hit.Hit#getParameterList()
	 */
	@Override
	public List<Parameter> getParameterList() {
		List<Parameter> list = super.getParameterList();
		
		// Add the required parameters
		list.add(transaction_id);
		
		// Add the optional parameters
		if (transaction_affiliation != null) list.add(transaction_affiliation);
		if (transaction_revenue     != null) list.add(transaction_revenue);
		if (transaction_shipping    != null) list.add(transaction_shipping);
		if (transaction_tax         != null) list.add(transaction_tax);
		if (currency_code           != null) list.add(currency_code);
		
		return list;
	}

	// Transaction Parameters
	public void setTransactionID(String transaction_id) {
		this.transaction_id = new TextParameter(MeasurementProtocol.TRANSACTION_ID_PARAMETER, transaction_id);
	}
	
	public void setTransactionAffiliation(String transaction_affiliation) {
		this.transaction_affiliation = new TextParameter(MeasurementProtocol.TRANSACTION_AFFILIATION_PARAMETER, transaction_affiliation);
	}
	
	public void setTransactionRevenue(String transaction_revenue) {
		this.transaction_revenue = new TextParameter(MeasurementProtocol.TRANSACTION_REVENUE_PARAMETER, transaction_revenue);
	}
	
	public void setTransactionShipping(float transaction_shipping) {
		this.transaction_shipping = new CurrencyParameter(MeasurementProtocol.TRANSACTION_SHIPPING_PARAMETER, transaction_shipping);
	}
	
	public void setTransactionTax(float transaction_tax) {
		this.transaction_tax = new CurrencyParameter(MeasurementProtocol.TRANSACTION_TAX_PARAMETER, transaction_tax);
	}
	
	public void setCurrencyCode(String currency_code) {
		this.currency_code = new TextParameter(MeasurementProtocol.CURRENCY_CODE_PARAMETER, currency_code);
	}
}
