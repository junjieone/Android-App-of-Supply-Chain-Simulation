package client;

/**
 * Created by Janter on 2015/6/3.
 */
public class AverageProfit {

    public static Double calculateProfit
    (String retailer1, String supplier1, String manufacturer1, String market1,
     String retailer2, String supplier2, String manufacturer2, String market2,
     String retailerOverstock0, String retailerShortage0, String retailerHolding0, String retailerPrice0,
     String supplierOverstock0, String supplierShortage0, String supplierHolding0, String supplierPrice0,
     String manufacturerOverstock0, String manufacturerShortage0, String manufacturerHolding0, String manufacturerPrice0)
    {
        Double retailerProfit = 0.0, supplierProfit = 0.0, manufacturerProfit = 0.0, averageProfit;

        Double retailer_last = Double.parseDouble(retailer1);
        Double supplier_last = Double.parseDouble(supplier1);
        Double manufacturer_last = Double.parseDouble(manufacturer1);
        Double market_last = Double.parseDouble(market1);
        Double retailer_this = Double.parseDouble(retailer2);
        Double supplier_this = Double.parseDouble(supplier2);
        Double manufacturer_this = Double.parseDouble(manufacturer2);
        Double market_this = Double.parseDouble(market2);
        Double retailerOverstock = Double.parseDouble(retailerOverstock0);
        Double retailerShortage = Double.parseDouble(retailerShortage0);
        Double retailerHolding = Double.parseDouble(retailerHolding0);
        Double retailerPrice = Double.parseDouble(retailerPrice0);
        Double supplierOverstock = Double.parseDouble(supplierOverstock0);
        Double supplierShortage = Double.parseDouble(supplierShortage0);
        Double supplierHolding = Double.parseDouble(supplierHolding0);
        Double supplierPrice = Double.parseDouble(supplierPrice0);
        Double manufacturerOverstock = Double.parseDouble(manufacturerOverstock0);
        Double manufacturerShortage = Double.parseDouble(manufacturerShortage0);
        Double manufacturerHolding = Double.parseDouble(manufacturerHolding0);
        Double manufacturerPrice = Double.parseDouble(manufacturerPrice0);

        //零售商利润情况
        if(retailer_last <= market_last && retailer_this < market_this)
            retailerProfit = retailerShortage * (2 * retailer_this - market_this);

        if(retailer_last <= market_last && retailer_this > market_this)
            retailerProfit = retailerShortage * retailer_this - (retailerOverstock + retailerHolding + retailerPrice) * (retailer_this - market_this);

        if(retailer_last > market_last && retailer_this <= market_this
                && (retailer_this + retailer_last) < (market_this + market_last ))
            retailerProfit = retailerShortage * (2 * retailer_this - market_this)
                    + (retailerPrice + retailerShortage) * (retailer_last - market_last);

        if(retailer_last > market_last && retailer_this <= market_this
                && (retailer_this + retailer_last) > (market_this + market_last ))
            retailerProfit = retailerShortage * retailer_this - (retailerOverstock + retailerHolding + retailerPrice) * (retailer_this - market_this)
                    - (retailerOverstock + retailerHolding) * (retailer_last - market_last);

        if(retailer_last > market_last && retailer_this > market_this)
            retailerProfit = retailerShortage * retailer_this - (retailerOverstock + retailerHolding + retailerPrice) * (retailer_this - market_this)
                    - (retailerOverstock + retailerHolding) * (retailer_last - market_last);

        //供应商利润情况
        if(supplier_last <= retailer_last && supplier_this < retailer_this)
            supplierProfit = supplierShortage * (2 * supplier_this - retailer_this);

        if(supplier_last <= retailer_last && supplier_this > retailer_this)
            supplierProfit = supplierShortage * supplier_this - (supplierOverstock + supplierHolding + supplierPrice) * (supplier_this - retailer_this);

        if(supplier_last > retailer_last && supplier_this <= retailer_this
                && (supplier_this + supplier_last) < (retailer_this + retailer_last ))
            supplierProfit = supplierShortage * (2 * supplier_this - retailer_this)
                    + (supplierPrice + supplierShortage) * (supplier_last - retailer_last);

        if(supplier_last > retailer_last && supplier_this <= retailer_this
                && (supplier_this + supplier_last) > (retailer_this + retailer_last ))
            supplierProfit = supplierShortage * supplier_this - (supplierOverstock + supplierHolding + supplierPrice) * (supplier_this - retailer_this)
                    - (supplierOverstock + supplierHolding) * (supplier_last - retailer_last);

        if(supplier_last > retailer_last && supplier_this > retailer_this)
            supplierProfit = supplierShortage * supplier_this - (supplierOverstock + supplierHolding + supplierPrice) * (supplier_this - retailer_this)
                    - (supplierOverstock + supplierHolding) * (supplier_last - retailer_last);

        //生产商利润情况
        if(manufacturer_last <= supplier_last && manufacturer_this < supplier_this)
            manufacturerProfit = manufacturerShortage * (2 * manufacturer_this - supplier_this);

        if(manufacturer_last <= supplier_last && manufacturer_this > supplier_this)
            manufacturerProfit = manufacturerShortage * manufacturer_this - (manufacturerOverstock + manufacturerHolding + manufacturerPrice) * (manufacturer_this - supplier_this);

        if(manufacturer_last > supplier_last && manufacturer_this <= supplier_this
                && (manufacturer_this + manufacturer_last) < (supplier_this + supplier_last ))
            manufacturerProfit = manufacturerShortage * (2 * manufacturer_this - supplier_this)
                    + (manufacturerPrice + manufacturerShortage) * (manufacturer_last - supplier_last);

        if(manufacturer_last > supplier_last && manufacturer_this <= supplier_this
                && (manufacturer_this + manufacturer_last) > (supplier_this + supplier_last ))
            manufacturerProfit = manufacturerShortage * manufacturer_this - (manufacturerOverstock + manufacturerHolding + manufacturerPrice) * (manufacturer_this - supplier_this)
                    - (manufacturerOverstock + manufacturerHolding) * (manufacturer_last - supplier_last);

        if(manufacturer_last > supplier_last && manufacturer_this > supplier_this)
            manufacturerProfit = manufacturerShortage * manufacturer_this - (manufacturerOverstock + manufacturerHolding + manufacturerPrice) * (manufacturer_this - supplier_this)
            - (manufacturerOverstock + manufacturerHolding) * (manufacturer_last - supplier_last);

        //计算平均利润
        averageProfit = (retailerProfit + supplierProfit + manufacturerProfit) / 3;
        return averageProfit;
    }
}
