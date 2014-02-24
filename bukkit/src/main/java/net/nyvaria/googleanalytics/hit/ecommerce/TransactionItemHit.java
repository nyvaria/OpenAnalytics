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
import net.nyvaria.googleanalytics.Parameter;
import net.nyvaria.googleanalytics.hit.Hit;
import net.nyvaria.openanalytics.bukkit.client.Client;

/**
 * @author Paul Thompson
 */
public class TransactionItemHit extends Hit {
    @Parameter(format = Parameter.FORMAT_TEXT, required = true, name = MeasurementProtocol.HIT_TYPE)
    private static final String HIT_TYPE = "item";

    /**
     * Constructor & Methods
     */

    public TransactionItemHit(Client client, String transaction_id, String item_name) {
        super(client, TransactionItemHit.HIT_TYPE);
        this.transaction_id = transaction_id;
        this.item_name = item_name;
    }

    /**
     * Transaction Parameters
     */

    @Parameter(format = Parameter.FORMAT_TEXT, required = true, name = MeasurementProtocol.TRANSACTION_ID)
    public String transaction_id;

    @Parameter(format = Parameter.FORMAT_TEXT, required = false, name = MeasurementProtocol.CURRENCY_CODE)
    public String currency_code;

    /**
	 * Item Parameters
     */

    @Parameter(format = Parameter.FORMAT_TEXT, required = true, name = MeasurementProtocol.ITEM_NAME)
    public String item_name;

    @Parameter(format = Parameter.FORMAT_CURRENCY, required = false, name = MeasurementProtocol.ITEM_PRICE)
    public Float item_price;

    @Parameter(format = Parameter.FORMAT_INTEGER, required = false, name = MeasurementProtocol.ITEM_QUANTITY)
    public Integer item_quantity;

    @Parameter(format = Parameter.FORMAT_TEXT, required = false, name = MeasurementProtocol.ITEM_CODE)
    public String item_code;

    @Parameter(format = Parameter.FORMAT_TEXT, required = false, name = MeasurementProtocol.ITEM_CATEGORY)
    public String item_category;
}
