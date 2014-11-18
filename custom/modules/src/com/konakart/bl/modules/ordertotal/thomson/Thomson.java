//
// (c) 20014 DS Data Systems UK Ltd, All rights reserved.
//
// DS Data Systems and KonaKart and their respective logos, are 
// trademarks of DS Data Systems UK Ltd. All rights reserved.
//
// The information in this document is free software; you can redistribute 
// it and/or modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
// 
// This software is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//

package com.konakart.bl.modules.ordertotal.thomson;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.xml.namespace.QName;

import org.apache.torque.TorqueException;

import com.konakart.app.KKConfiguration;
import com.konakart.app.KKException;
import com.konakart.app.Order;
import com.konakart.app.OrderTotal;
import com.konakart.appif.CustomerIf;
import com.konakart.appif.KKEngIf;
import com.konakart.appif.OrderIf;
import com.konakart.appif.OrderProductIf;
import com.konakart.appif.ShippingQuoteIf;
import com.konakart.bl.modules.BaseModule;
import com.konakart.bl.modules.ordertotal.BaseOrderTotalModule;
import com.konakart.bl.modules.ordertotal.OrderTotalInterface;
import com.konakart.util.Utils;
import com.sabrix.services.taxcalculationservice._2011_09_01.IndataInvoiceType;
import com.sabrix.services.taxcalculationservice._2011_09_01.IndataLineType;
import com.sabrix.services.taxcalculationservice._2011_09_01.IndataType;
import com.sabrix.services.taxcalculationservice._2011_09_01.MessageType;
import com.sabrix.services.taxcalculationservice._2011_09_01.QuantitiesType;
import com.sabrix.services.taxcalculationservice._2011_09_01.QuantityType;
import com.sabrix.services.taxcalculationservice._2011_09_01.RegistrationsType;
import com.sabrix.services.taxcalculationservice._2011_09_01.TaxCalculationRequest;
import com.sabrix.services.taxcalculationservice._2011_09_01.TaxCalculationResponse;
import com.sabrix.services.taxcalculationservice._2011_09_01.TaxCalculationService;
import com.sabrix.services.taxcalculationservice._2011_09_01.TaxCalculationService_Service;
import com.sabrix.services.taxcalculationservice._2011_09_01.UserElementType;
import com.sabrix.services.taxcalculationservice._2011_09_01.VersionType;
import com.sabrix.services.taxcalculationservice._2011_09_01.ZoneAddressType;
import com.sabrix.services.zonelookupservice._2011_09_01.ZoneLookupFault_Exception;
import com.sabrix.services.zonelookupservice._2011_09_01.ZoneLookupRequest;
import com.sabrix.services.zonelookupservice._2011_09_01.ZoneLookupRequestAddressType;
import com.sabrix.services.zonelookupservice._2011_09_01.ZoneLookupResponseAddressType;
import com.sabrix.services.zonelookupservice._2011_09_01.ZoneLookupResponseWrapper;
import com.sabrix.services.zonelookupservice._2011_09_01.ZoneLookupService;
import com.sabrix.services.zonelookupservice._2011_09_01.ZoneLookupService_Service;
import com.workingdogs.village.DataSetException;

/**
 * Module that creates an OrderTotal object based on tax information received from the Avalara web
 * service
 */
public class Thomson extends BaseOrderTotalModule implements OrderTotalInterface
{
    private static String code = "Thomson";

    private static String bundleName = BaseModule.basePackage + ".ordertotal.thomson.Thomson";

    private static HashMap<Locale, ResourceBundle> resourceBundleMap = new HashMap<Locale, ResourceBundle>();

    private static String mutex = "otThomsonMutex";

    private static SimpleDateFormat thomsonDateFormat = new SimpleDateFormat("yyyyMMdd");

    /** Hash Map that contains the static data */
    private static Map<String, StaticData> staticDataHM = Collections
            .synchronizedMap(new HashMap<String, StaticData>());

    // Configuration Keys

    private final static String MODULE_ORDER_TOTAL_THOMSON_SORT_ORDER = "MODULE_ORDER_TOTAL_THOMSON_SORT_ORDER";

    private final static String MODULE_ORDER_TOTAL_THOMSON_STATUS = "MODULE_ORDER_TOTAL_THOMSON_STATUS";

    private final static String MODULE_ORDER_TOTAL_THOMSON_COMPANY_NAME = "MODULE_ORDER_TOTAL_THOMSON_COMPANY_NAME";

    private final static String MODULE_ORDER_TOTAL_THOMSON_COMPANY_ROLE = "MODULE_ORDER_TOTAL_THOMSON_COMPANY_ROLE";

    private final static String MODULE_ORDER_TOTAL_THOMSON_EXT_COMPANY_ID = "MODULE_ORDER_TOTAL_THOMSON_EXT_COMPANY_ID";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SELLER_ROLE = "MODULE_ORDER_TOTAL_THOMSON_SELLER_ROLE";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SECURE = "MODULE_ORDER_TOTAL_THOMSON_SECURE";

    private final static String MODULE_ORDER_TOTAL_THOMSON_USERNAME = "MODULE_ORDER_TOTAL_THOMSON_USERNAME";

    private final static String MODULE_ORDER_TOTAL_THOMSON_PASSWORD = "MODULE_ORDER_TOTAL_THOMSON_PASSWORD";

    private final static String MODULE_ORDER_TOTAL_THOMSON_USCOMMODITY_CODE_FIELD = "MODULE_ORDER_TOTAL_THOMSON_USCOMMODITY_CODE_FIELD";

    private final static String MODULE_ORDER_TOTAL_THOMSON_CUSTOM_CODE_FIELD = "MODULE_ORDER_TOTAL_THOMSON_CUSTOM_CODE_FIELD";

    private final static String MODULE_ORDER_TOTAL_THOMSON_COMMODITY_CODE_FIELD = "MODULE_ORDER_TOTAL_THOMSON_COMMODITY_CODE_FIELD";

    private final static String MODULE_ORDER_TOTAL_THOMSON_MOSS_CODE_FIELD = "MODULE_ORDER_TOTAL_THOMSON_MOSS_CODE_FIELD";

    private final static String MODULE_ORDER_TOTAL_THOMSON_TAX_IDENTIFIER_FIELD = "MODULE_ORDER_TOTAL_THOMSON_TAX_IDENTIFIER_FIELD";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SELLER_STREET = "MODULE_ORDER_TOTAL_THOMSON_SELLER_STREET";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SELLER_CITY = "MODULE_ORDER_TOTAL_THOMSON_SELLER_CITY";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SELLER_COUNTY = "MODULE_ORDER_TOTAL_THOMSON_SELLER_COUNTY";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SELLER_STATE = "MODULE_ORDER_TOTAL_THOMSON_SELLER_STATE";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SELLER_POSTCODE = "MODULE_ORDER_TOTAL_THOMSON_SELLER_POSTCODE";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SELLER_COUNTRY = "MODULE_ORDER_TOTAL_THOMSON_SELLER_COUNTRY";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_STREET = "MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_STREET";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_CITY = "MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_CITY";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_COUNTY = "MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_COUNTY";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_STATE = "MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_STATE";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_POSTCODE = "MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_POSTCODE";

    private final static String MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_COUNTRY = "MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_COUNTRY";

    private final static String MODULE_ORDER_TOTAL_THOMSON_TAX_URL = "MODULE_ORDER_TOTAL_THOMSON_TAX_URL";

    private final static String MODULE_ORDER_TOTAL_THOMSON_ZONE_URL = "MODULE_ORDER_TOTAL_THOMSON_ZONE_URL";

    private final static String MODULE_ORDER_TOTAL_THOMSON_CALLING_SYS = "MODULE_ORDER_TOTAL_THOMSON_CALLING_SYS";

    private final static String MODULE_ORDER_TOTAL_THOMSON_COMMIT_STATUS = "MODULE_ORDER_TOTAL_THOMSON_COMMIT_STATUS";

    // Message Catalog Keys

    private final static String MODULE_ORDER_TOTAL_THOMSON_TITLE = "module.order.total.thomson.title";

    private final String POINT_OF_TITLE_TRANSFER = "I";

    /**
     * Constructor
     * 
     * @param eng
     * 
     * @throws DataSetException
     * @throws KKException
     * @throws TorqueException
     * 
     */
    public Thomson(KKEngIf eng) throws TorqueException, KKException, DataSetException
    {
        super.init(eng);

        StaticData sd = staticDataHM.get(getStoreId());

        if (sd == null)
        {
            synchronized (mutex)
            {
                sd = staticDataHM.get(getStoreId());
                if (sd == null)
                {
                    setStaticVariables();
                }
            }
        }
    }

    /**
     * get Sort Order of module
     */
    public int getSortOrder()
    {
        StaticData sd;
        try
        {
            sd = staticDataHM.get(getStoreId());
            return sd.getSortOrder();
        } catch (KKException e)
        {
            log.error("Can't get the store id", e);
            return 0;
        }
    }

    public String getCode()
    {
        return code;
    }

    /**
     * Sets some static variables during setup
     * 
     * @throws KKException
     * 
     */
    public void setStaticVariables() throws KKException
    {
        KKConfiguration conf;
        StaticData staticData = staticDataHM.get(getStoreId());
        if (staticData == null)
        {
            staticData = new StaticData();
            staticDataHM.put(getStoreId(), staticData);
        }

        conf = getConfiguration(MODULE_ORDER_TOTAL_THOMSON_SORT_ORDER);
        if (conf == null)
        {
            staticData.setSortOrder(0);
        } else
        {
            staticData.setSortOrder(new Integer(conf.getValue()).intValue());
        }

        staticData.setCompanyName(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_COMPANY_NAME));
        staticData.setCompanyRole(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_COMPANY_ROLE));
        staticData
                .setExternalCompanyId(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_EXT_COMPANY_ID));
        staticData.setSellerRole(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SELLER_ROLE));

        staticData.setUsername(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_USERNAME));
        staticData.setPassword(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_PASSWORD));
        staticData.setSecure(getConfigurationValueAsBool(MODULE_ORDER_TOTAL_THOMSON_SECURE, false));

        staticData.setCommitStatus(getConfigurationValueAsBool(
                MODULE_ORDER_TOTAL_THOMSON_COMMIT_STATUS, false));

        staticData.setCustomCodeCustomField(getConfigurationValueAsIntWithDefault(
                MODULE_ORDER_TOTAL_THOMSON_CUSTOM_CODE_FIELD, -1));

        staticData.setUsCommodityCodeCustomField(getConfigurationValueAsIntWithDefault(
                MODULE_ORDER_TOTAL_THOMSON_USCOMMODITY_CODE_FIELD, 1));
        staticData.setCommodityCodeCustomField(getConfigurationValueAsIntWithDefault(
                MODULE_ORDER_TOTAL_THOMSON_COMMODITY_CODE_FIELD, 2));
        staticData.setMossCodeCustomField(getConfigurationValueAsIntWithDefault(
                MODULE_ORDER_TOTAL_THOMSON_MOSS_CODE_FIELD, 3));
        staticData.setTaxIdField(getConfigurationValueAsIntWithDefault(
                MODULE_ORDER_TOTAL_THOMSON_TAX_IDENTIFIER_FIELD, 0));

        staticData.setStreet(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SELLER_STREET));
        staticData.setCounty(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SELLER_COUNTY));
        staticData.setState(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SELLER_STATE));
        staticData.setCity(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SELLER_CITY));
        staticData.setPostcode(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SELLER_POSTCODE));
        staticData.setCountry(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SELLER_COUNTRY));

        staticData
                .setShipFromStreet(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_STREET));
        staticData
                .setShipFromCounty(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_COUNTY));
        staticData
                .setShipFromState(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_STATE));
        staticData.setShipFromCity(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_CITY));
        staticData
                .setShipFromPostcode(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_POSTCODE));
        staticData
                .setShipFromCountry(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_SHIPFROM_COUNTRY));

        staticData.setCallingSys(getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_CALLING_SYS));

        staticData.setDefaultCurrency(getConfigurationValue("DEFAULT_CURRENCY"));

        // Set up Tax Service WSDL URL
        String taxServiceUrl = getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_TAX_URL);
        try
        {
            staticData.setTaxServiceEndpoint(new URL(taxServiceUrl));
        } catch (MalformedURLException e)
        {
            log.warn("Unable to set up Tax Service Endpoint using URL : " + taxServiceUrl + " : "
                    + e.getMessage());
            e.printStackTrace();
        }

        // Set up Zone Service WSDL URL
        String zoneServiceUrl = getConfigurationValue(MODULE_ORDER_TOTAL_THOMSON_ZONE_URL);
        try
        {
            staticData.setZoneServiceEndpoint(new URL(zoneServiceUrl));
        } catch (MalformedURLException e)
        {
            log.warn("Unable to set up Zone Service Endpoint using URL : " + zoneServiceUrl + " : "
                    + e.getMessage());
            e.printStackTrace();
        }

        // Verify and cache the two defined addresses

        ZoneAddressType sellerAddr = new ZoneAddressType();

        sellerAddr.setADDRESS1(staticData.getStreet());
        sellerAddr.setCITY(staticData.getCity());
        sellerAddr.setCOUNTRY(staticData.getCountry());
        sellerAddr.setCOUNTY(staticData.getCounty());
        sellerAddr.setPOSTCODE(staticData.getPostcode());
        sellerAddr.setSTATE(staticData.getState());

        ZoneAddressType cleanSellerAddr = new ZoneAddressType();

        if (validateAddress(staticData, sellerAddr, cleanSellerAddr))
        {
            staticData.setSellerAddr(cleanSellerAddr);
        } else
        {
            staticData.setSellerAddr(sellerAddr);
        }

        // Now the SHIPFROM address

        ZoneAddressType shipFromAddr = new ZoneAddressType();

        shipFromAddr.setADDRESS1(staticData.getShipFromStreet());
        shipFromAddr.setCITY(staticData.getShipFromCity());
        shipFromAddr.setCOUNTRY(staticData.getShipFromCountry());
        shipFromAddr.setCOUNTY(staticData.getShipFromCounty());
        shipFromAddr.setPOSTCODE(staticData.getShipFromPostcode());
        shipFromAddr.setSTATE(staticData.getShipFromState());

        ZoneAddressType cleanShipFromAddr = new ZoneAddressType();

        if (validateAddress(staticData, shipFromAddr, cleanShipFromAddr))
        {
            staticData.setShipFromAddr(cleanShipFromAddr);
        } else
        {
            staticData.setShipFromAddr(shipFromAddr);
        }
    }

    /**
     * Returns true or false
     * 
     * @throws KKException
     */
    public boolean isAvailable() throws KKException
    {
        return isAvailable(MODULE_ORDER_TOTAL_THOMSON_STATUS);
    }

    /**
     * Create and return an OrderTotal object for the tax amount.
     * 
     * @param order
     * @param dispPriceWithTax
     * @param locale
     * @return Returns an OrderTotal object for this module
     * @throws Exception
     */
    public OrderTotal getOrderTotal(Order order, boolean dispPriceWithTax, Locale locale)
            throws Exception
    {
        OrderTotal ot;
        StaticData sd = staticDataHM.get(getStoreId());

        // Get the resource bundle
        ResourceBundle rb = getResourceBundle(mutex, bundleName, resourceBundleMap, locale);
        if (rb == null)
        {
            throw new KKException("A resource file cannot be found for the country "
                    + locale.getCountry());
        }
        ot = new OrderTotal();
        ot.setSortOrder(sd.getSortOrder());
        ot.setClassName(code);

        // Call the Thomson Reuters service - but don't commit here

        boolean commitOrder = false;
        callThomsonReuters(sd, order, ot, commitOrder);

        // Set the title of the order total
        StringBuffer title = new StringBuffer();
        title.append(rb.getString(MODULE_ORDER_TOTAL_THOMSON_TITLE));
        title.append(":");
        ot.setTitle(title.toString());

        return ot;
    }

    /**
     * Commit the Order transaction
     * 
     * @param order
     * @throws Exception
     *             if something unexpected happens
     */
    public void commitOrder(OrderIf order) throws Exception
    {
        if (!getConfigurationValueAsBool(MODULE_ORDER_TOTAL_THOMSON_COMMIT_STATUS, false))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Thomson commit status is false - so no commit will be executed");
            }
            return;
        }

        if (log.isDebugEnabled())
        {
            log.debug("Start of Thomson commit");
        }

        StaticData sd = staticDataHM.get(getStoreId());

        // Call the service - and commit it
        boolean commitOrder = true;
        callThomsonReuters(sd, (Order) order, null, commitOrder);
    }

    /**
     * Call the Thomson Reuters tax service which calculates the total tax for the order (including
     * shipping) and populates the Order Total and Order objects with the tax information.
     * 
     * @param sd
     * @param order
     * @param ot
     * @param commitOrder
     *            true if we should commit this Order
     * @throws Exception
     * @throws DataSetException
     * @throws TorqueException
     */
    private void callThomsonReuters(StaticData sd, Order order, OrderTotal ot, boolean commitOrder)
            throws TorqueException, DataSetException, Exception
    {
        URL taxServiceEndpoint = sd.getTaxServiceEndpoint();
        String customerId = String.valueOf(order.getCustomerId());
        CustomerIf customer = getCustMgr().getCustomerForId(order.getCustomerId());
        // String cartId = order.getLifecycleId();

        // Figure out whether there is a shipping charge
        boolean isShipping = false;
        ShippingQuoteIf shippingQuote = order.getShippingQuote();
        if (shippingQuote != null && shippingQuote.getTotalExTax() != null
                && shippingQuote.getTotalExTax().compareTo(new BigDecimal(0)) == 1)
        {
            isShipping = true;
        }

        String debugProperty = log.isDebugEnabled() ? "true" : "false";
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump",
                debugProperty);
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump",
                debugProperty);
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", debugProperty);
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", debugProperty);

        if (taxServiceEndpoint == null)
        {
            throw new KKException(
                    "TaxCalculationService Endpoint has not been successfully defined");
        }

        QName wsQName = new QName(
                "http://www.sabrix.com/services/taxcalculationservice/2011-09-01",
                "TaxCalculationService");

        TaxCalculationService_Service taxServiceObject = new TaxCalculationService_Service(
                taxServiceEndpoint, wsQName);

        HeaderHandlerResolver hhResolver = new HeaderHandlerResolver();

        hhResolver.setSecure(sd.isSecure());
        hhResolver.setUName(sd.getUsername());
        hhResolver.setPWord(sd.getPassword());

        taxServiceObject.setHandlerResolver(hhResolver);

        TaxCalculationService taxServicePort = taxServiceObject.getTaxCalculationServicePort();

        TaxCalculationRequest request = new TaxCalculationRequest();

        long calcStart = System.currentTimeMillis();

        IndataType inData = new IndataType();

        inData.setVersion(VersionType.G);

        IndataInvoiceType invoice = new IndataInvoiceType();

        invoice.setEXTERNALCOMPANYID(sd.getExternalCompanyId());
        invoice.setCOMPANYNAME(sd.getCompanyName());
        invoice.setCOMPANYROLE(sd.getCompanyRole());

        invoice.setHOSTSYSTEM("KONAKART");
        invoice.setCALLINGSYSTEMNUMBER(sd.getCallingSys());
        // invoice.setDOCUMENTTYPE("UNKNOWN");

        ZoneAddressType shipToAddr = new ZoneAddressType();
        shipToAddr.setADDRESS1(order.getDeliveryStreetAddress());
        shipToAddr.setCITY(order.getDeliveryCity());
        shipToAddr.setCOUNTRY(order.getDeliveryCountry());
        shipToAddr.setPOSTCODE(order.getDeliveryPostcode());
        if (!Utils.isBlank(order.getDeliveryState()))
        {
            shipToAddr.setSTATE(order.getDeliveryState());
        }

        invoice.setBUYERPRIMARY(shipToAddr);

        ZoneAddressType billToAddr = new ZoneAddressType();
        billToAddr.setADDRESS1(order.getBillingStreetAddress());
        billToAddr.setCITY(order.getBillingCity());
        billToAddr.setCOUNTRY(order.getBillingCountry());
        billToAddr.setPOSTCODE(order.getBillingPostcode());
        if (!Utils.isBlank(order.getBillingState()))
        {
            billToAddr.setSTATE(order.getBillingState());
        }

        invoice.setBILLTO(billToAddr);

        if (commitOrder)
        {
            invoice.setISAUDITED("true");
            invoice.setISREPORTED("true");
        } else
        {
            invoice.setISAUDITED("false");
            invoice.setISREPORTED("false");
        }
        invoice.setISREVERSED("false");

        invoice.setCALCULATIONDIRECTION("F");

        invoice.setCURRENCYCODE(sd.getDefaultCurrency());

        invoice.setCUSTOMERNAME(order.getCustomerName());
        invoice.setCUSTOMERNUMBER(customerId);
        // invoice.setDELIVERYTERMS("UNKNOWN");

        if (!Utils.isBlank(order.getOrderNumber()))
        {
            invoice.setINVOICENUMBER(order.getOrderNumber());
        } else if (order.getId() > 0)
        {
            invoice.setINVOICENUMBER(String.valueOf(order.getId()));
        } else
        {
            invoice.setINVOICENUMBER(String.valueOf(order.getLifecycleId()));
        }

        invoice.setINVOICEDATE(getInvoiceDate(order));
        invoice.setFISCALDATE(getInvoiceDate(order));
        invoice.setTRANSACTIONTYPE("GS"); // GS = Goods

        String buyerVAT = getBuyerRole(sd, customer);

        // Process each line in the Order
        int cartIndex = 1;
        for (int i = 0; i < order.getOrderProducts().length; i++)
        {
            OrderProductIf orderProduct = order.getOrderProducts()[i];
            IndataLineType line = new IndataLineType();

            line.setLINENUMBER(new BigDecimal(cartIndex));
            line.setID(String.valueOf(cartIndex));

            String partNumber = getPartNumber(sd, orderProduct);
            if (!Utils.isBlank(partNumber))
            {
                line.setPARTNUMBER(partNumber);
            }

            String commodityCode = getCommodityCode(sd, orderProduct);
            if (!Utils.isBlank(commodityCode))
            {
                line.setCOMMODITYCODE(commodityCode);
            }

            String usCommodityCode = getUSCommodityCode(sd, orderProduct);
            if (!Utils.isBlank(usCommodityCode))
            {
                line.setPRODUCTCODE(usCommodityCode);
            }

            String mossIndicator = getMossIndicator(sd, orderProduct);
            if (!Utils.isBlank(mossIndicator))
            {
                UserElementType ue = new UserElementType();
                ue.setNAME("ATTRIBUTE1");
                ue.setVALUE(mossIndicator);
                line.getUSERELEMENT().add(ue);
            }

            line.setSHIPFROM(sd.getShipFromAddr());
            line.setSELLERPRIMARY(sd.getSellerAddr());

            line.setDESCRIPTION(orderProduct.getName());
            line.setGROSSAMOUNT(orderProduct.getFinalPriceExTax().toString());
            line.setBUYERPRIMARY(shipToAddr);
            line.setSHIPTO(shipToAddr);
            line.setSUPPLY(shipToAddr);
            line.setDELIVERYTERMS("DDP");

            RegistrationsType regn = new RegistrationsType();
            regn.getSELLERROLE().add(sd.getSellerRole());
            // regn.getMIDDLEMANROLE().add("UNKNOWN");

            if (!Utils.isBlank(buyerVAT))
            {
                regn.getBUYERROLE().add(buyerVAT);
            }

            line.setREGISTRATIONS(regn);
            // line.setCOUNTRYOFORIGIN("UNKNOWN");
            line.setPOINTOFTITLETRANSFER(POINT_OF_TITLE_TRANSFER);
            line.setTRANSACTIONTYPE("GS"); // GS = Goods

            QuantityType qty = new QuantityType();
            qty.setAMOUNT(String.valueOf(orderProduct.getQuantity()));
            qty.setUOM("CPY");
            QuantitiesType qtys = new QuantitiesType();
            qtys.getQUANTITY().add(qty);
            line.setQUANTITIES(qtys);

            invoice.getLINE().add(line);
            cartIndex++;
        }

        if (isShipping)
        {
            // Add shipping charges to the cart (shipping charges are taxable)
            IndataLineType line = new IndataLineType();

            line.setLINENUMBER(new BigDecimal(cartIndex));
            line.setID(String.valueOf(cartIndex));

            line.setSHIPFROM(sd.getShipFromAddr());
            line.setSELLERPRIMARY(sd.getSellerAddr());

            line.setDESCRIPTION("Shipping Charge");
            line.setGROSSAMOUNT(shippingQuote.getTotalIncTax().toString());
            line.setBUYERPRIMARY(shipToAddr);
            line.setSHIPTO(shipToAddr);
            line.setSUPPLY(shipToAddr);
            line.setDELIVERYTERMS("DDP");

            RegistrationsType regn = new RegistrationsType();
            regn.getSELLERROLE().add(sd.getSellerRole());
            // regn.getMIDDLEMANROLE().add("UNKNOWN");

            if (!Utils.isBlank(buyerVAT))
            {
                regn.getBUYERROLE().add(buyerVAT);
            }

            line.setREGISTRATIONS(regn);

            // line.setCOUNTRYOFORIGIN("UNKNOWN");
            line.setPOINTOFTITLETRANSFER(POINT_OF_TITLE_TRANSFER);
            line.setTRANSACTIONTYPE("GS"); // GS = Goods

            QuantityType qty = new QuantityType();
            qty.setAMOUNT("1");
            qty.setUOM("CPY");
            QuantitiesType qtys = new QuantitiesType();
            qtys.getQUANTITY().add(qty);
            line.setQUANTITIES(qtys);

            invoice.getLINE().add(line);
        }

        inData.getINVOICE().add(invoice);

        request.setINDATA(inData);
        // log.info("Request =\n" + request.toString());
        TaxCalculationResponse response = taxServicePort.calculateTax(request);

        long calcEnd = System.currentTimeMillis();

        if (log.isDebugEnabled())
        {
            log.debug("Thomsons Tax Calculation Time : " + (calcEnd - calcStart) + "ms");
        }

        if (response.getOUTDATA().getREQUESTSTATUS().getERROR() != null
                && response.getOUTDATA().getREQUESTSTATUS().getERROR().size() > 0)
        {
            String problemStr = response.getOUTDATA().getREQUESTSTATUS().getERROR().get(0)
                    .getDESCRIPTION();
            log.warn("Response : " + problemStr);
            throw new KKException("Error getting Tax from Thomson Reuters : " + problemStr);
        }

        if (log.isDebugEnabled())
        {
            String msg = "\n\t Response : "
                    + "\n\t Success?         : "
                    + response.getOUTDATA().getREQUESTSTATUS().isISSUCCESS()
                    + "\n\t Partial Success? : "
                    + response.getOUTDATA().getREQUESTSTATUS().isISPARTIALSUCCESS()
                    + "\n\t Response under Invoice : "
                    + "\n\t Success?         : "
                    + response.getOUTDATA().getINVOICE().get(0).getREQUESTSTATUS().isISSUCCESS()
                    + "\n\t Partial Success? : "
                    + response.getOUTDATA().getINVOICE().get(0).getREQUESTSTATUS()
                            .isISPARTIALSUCCESS() + " (false is OK)";

            // Print any invoice messages of severity 1 or more

            msg += "\n\t Invoice Messages:";
            for (MessageType msgT : response.getOUTDATA().getINVOICE().get(0).getMESSAGE())
            {
                msg += "\n\t Severity " + msgT.getSEVERITY() + " = " + msgT.getMESSAGETEXT();
            }

            msg += "\n\t Line Messages:";
            for (MessageType msgT : response.getOUTDATA().getINVOICE().get(0).getLINE().get(0)
                    .getMESSAGE())
            {
                msg += "\n\t Severity " + msgT.getSEVERITY() + " = " + msgT.getMESSAGETEXT();
            }

            msg += "\n\t Total Tax Amount : "
                    + response.getOUTDATA().getINVOICE().get(0).getTOTALTAXAMOUNT();

            log.debug(msg);
        }

        double taxAmount = Double.parseDouble(response.getOUTDATA().getINVOICE().get(0)
                .getTOTALTAXAMOUNT());

        // Set the Order Total with the total tax amount
        BigDecimal taxAmountBD = new BigDecimal(taxAmount);

        if (ot != null)
        {
            ot.setValue(taxAmountBD);
            ot.setText(getCurrMgr().formatPrice(taxAmountBD, order.getCurrencyCode()));
        }

        // Set the order with tax information order.setTax(taxAmountBD);
        order.setTotalIncTax(order.getTotalExTax().add(taxAmountBD));
    }

    private String getMossIndicator(StaticData sd, OrderProductIf orderProduct)
    {
        String mossIndicator = null;

        if (sd.getMossCodeCustomField() == 1)
        {
            mossIndicator = orderProduct.getCustom1();
        } else if (sd.getMossCodeCustomField() == 2)
        {
            mossIndicator = orderProduct.getCustom2();
        } else if (sd.getMossCodeCustomField() == 3)
        {
            mossIndicator = orderProduct.getCustom3();
        } else if (sd.getMossCodeCustomField() == 4)
        {
            mossIndicator = orderProduct.getCustom4();
        } else if (sd.getMossCodeCustomField() == 5)
        {
            mossIndicator = getCustomField(MODULE_ORDER_TOTAL_THOMSON_MOSS_CODE_FIELD, sd,
                    orderProduct);
        }

        if (log.isDebugEnabled())
        {
            String msg = "\n\t Moss Code Field  = " + sd.getMossCodeCustomField()
                    + "\n\t oProd.Custom1    = " + orderProduct.getCustom1()
                    + "\n\t oProd.Custom2    = " + orderProduct.getCustom2()
                    + "\n\t oProd.Custom3    = " + orderProduct.getCustom3()
                    + "\n\t oProd.Custom4    = " + orderProduct.getCustom4()
                    + "\n\t Moss Code        = " + mossIndicator;
            log.debug(msg);
        }

        return mossIndicator;
    }

    /**
     * Custom method for retrieving values from complicated places... expected to be customised to
     * suit local requirements. In the default case we expect that the 3 tax code fields are
     * '|'-separated and set on a single custom field that is identified by the
     * MODULE_ORDER_TOTAL_THOMSON_CUSTON_CODE_FIELD configuration setting.
     * 
     * @param field to identify the field we are retrieving
     * @param sd the StaticData which caches configuration data for the store
     * @param orderProduct the order product.  An order will have one or more orderProducts
     * @return the value for the field identified
     */
    protected String getCustomField(String field, StaticData sd, OrderProductIf orderProduct)
    {
        String customCodeFieldValue = null;
        
        if (sd.getCustomCodeCustomField() == 1)
        {
            customCodeFieldValue = orderProduct.getCustom1();
        } else if (sd.getCustomCodeCustomField() == 2)
        {
            customCodeFieldValue = orderProduct.getCustom2();
        } else if (sd.getCustomCodeCustomField() == 3)
        {
            customCodeFieldValue = orderProduct.getCustom3();
        } else if (sd.getCustomCodeCustomField() == 4)
        {
            customCodeFieldValue = orderProduct.getCustom4();
        } 
        
        if (log.isDebugEnabled())
        {
            log.debug("Custom Code Field Value = " + customCodeFieldValue);
        }

        if (customCodeFieldValue == null)
        {
            return null;
        }
        
        String[] triplet = customCodeFieldValue.split("\\|");
        
        if (field == MODULE_ORDER_TOTAL_THOMSON_USCOMMODITY_CODE_FIELD)
        {
            return triplet[0];
        } else   if (field == MODULE_ORDER_TOTAL_THOMSON_COMMODITY_CODE_FIELD)
        {
            return triplet[1];
        } else   if (field == MODULE_ORDER_TOTAL_THOMSON_MOSS_CODE_FIELD)
        {
            return triplet[2];
        } else 
        {
            log.warn("Unknown field : " + field);
        }
         
        return null;
    }

    private String getCommodityCode(StaticData sd, OrderProductIf orderProduct)
    {
        String commodityCode = null;

        if (sd.getCommodityCodeCustomField() == 1)
        {
            commodityCode = orderProduct.getCustom1();
        } else if (sd.getCommodityCodeCustomField() == 2)
        {
            commodityCode = orderProduct.getCustom2();
        } else if (sd.getCommodityCodeCustomField() == 3)
        {
            commodityCode = orderProduct.getCustom3();
        } else if (sd.getCommodityCodeCustomField() == 4)
        {
            commodityCode = orderProduct.getCustom4();
        } else if (sd.getCommodityCodeCustomField() == 5)
        {
            commodityCode = getCustomField(MODULE_ORDER_TOTAL_THOMSON_COMMODITY_CODE_FIELD, sd,
                    orderProduct);
        }

        if (log.isDebugEnabled())
        {
            String msg = "\n\t EUComCode Field  = " + sd.getCommodityCodeCustomField()
                    + "\n\t oProd.Custom1    = " + orderProduct.getCustom1()
                    + "\n\t oProd.Custom2    = " + orderProduct.getCustom2()
                    + "\n\t oProd.Custom3    = " + orderProduct.getCustom3()
                    + "\n\t oProd.Custom4    = " + orderProduct.getCustom4()
                    + "\n\t EUComdty Code    = " + commodityCode;
            log.debug(msg);
        }

        return commodityCode;
    }

    private String getPartNumber(StaticData sd, OrderProductIf orderProduct)
    {
        return orderProduct.getSku();
    }

    private String getUSCommodityCode(StaticData sd, OrderProductIf orderProduct)
    {
        String usComCode = null;

        if (sd.getUsCommodityCodeCustomField() == 1)
        {
            usComCode = orderProduct.getCustom1();
        } else if (sd.getUsCommodityCodeCustomField() == 2)
        {
            usComCode = orderProduct.getCustom2();
        } else if (sd.getUsCommodityCodeCustomField() == 3)
        {
            usComCode = orderProduct.getCustom3();
        } else if (sd.getUsCommodityCodeCustomField() == 4)
        {
            usComCode = orderProduct.getCustom4();
        } else if (sd.getUsCommodityCodeCustomField() == 5)
        {
            usComCode = getCustomField(MODULE_ORDER_TOTAL_THOMSON_USCOMMODITY_CODE_FIELD, sd,
                    orderProduct);
        }

        if (log.isDebugEnabled())
        {
            String msg = "\n\t USComCode Field  = " + sd.getUsCommodityCodeCustomField()
                    + "\n\t oProd.Custom1    = " + orderProduct.getCustom1()
                    + "\n\t oProd.Custom2    = " + orderProduct.getCustom2()
                    + "\n\t oProd.Custom3    = " + orderProduct.getCustom3()
                    + "\n\t oProd.Custom4    = " + orderProduct.getCustom4()
                    + "\n\t USComdty Code    = " + usComCode;
            log.debug(msg);
        }
        return usComCode;
    }

    private String getBuyerRole(StaticData sd, CustomerIf customer)
    {
        // The taxIdentifier attribute is only available in 7.3.0.0
        // String buyerVAT = customer.getTaxIdentifier();

        String buyerVAT = null;
        if (sd.getTaxIdField() == 1)
        {
            buyerVAT = customer.getCustom1();
        } else if (sd.getTaxIdField() == 2)
        {
            buyerVAT = customer.getCustom2();
        } else if (sd.getTaxIdField() == 3)
        {
            buyerVAT = customer.getCustom3();
        } else if (sd.getTaxIdField() == 4)
        {
            buyerVAT = customer.getCustom4();
        }

        if (log.isDebugEnabled())
        {
            String msg = "\n\t TaxFieldId       = " + sd.getTaxIdField()
                    + "\n\t Customer.Custom1 = " + customer.getCustom1()
                    + "\n\t Customer.Custom2 = " + customer.getCustom2()
                    + "\n\t Customer.Custom3 = " + customer.getCustom3()
                    + "\n\t Customer.Custom4 = " + customer.getCustom4()
                    + "\n\t Buyer Role       = " + buyerVAT;
            log.debug(msg);
        }
        return buyerVAT;
    }

    /**
     * Checks that Thomson can validate the address and throw an exception if it can't
     * 
     * @param addr
     *            the address to validate.
     * @param cleanAddr
     *            the cleaned address if address validation was successful - it should be an
     *            instantiated ZoneAddressType that will be populated by this method
     * @return true if the address validation was successful otherwise false or an exception is
     *         thrown
     * @throws KKException
     *             if the zone lookup service throws an exception
     */
    private boolean validateAddress(StaticData sd, ZoneAddressType addr, ZoneAddressType cleanAddr)
            throws KKException
    {
        ZoneLookupRequestAddressType reqAddr = new ZoneLookupRequestAddressType();
        reqAddr.setCITY(addr.getCITY());
        reqAddr.setCOUNTRY(addr.getCOUNTRY());
        reqAddr.setCOUNTY(addr.getCOUNTY());
        reqAddr.setPOSTCODE(addr.getPOSTCODE());
        reqAddr.setSTATE(addr.getSTATE());

        URL zoneServiceEndpoint = sd.getZoneServiceEndpoint();

        QName wsQName = new QName("http://www.sabrix.com/services/zonelookupservice/2011-09-01",
                "ZoneLookupService");

        ZoneLookupService_Service zoneServiceObject = new ZoneLookupService_Service(
                zoneServiceEndpoint, wsQName);

        HeaderHandlerResolver hhResolver = new HeaderHandlerResolver();

        hhResolver.setSecure(sd.isSecure());
        hhResolver.setUName(sd.getUsername());
        hhResolver.setPWord(sd.getPassword());

        zoneServiceObject.setHandlerResolver(hhResolver);

        ZoneLookupService zoneServicePort = zoneServiceObject.getZoneLookupServicePort();

        ZoneLookupRequest request = new ZoneLookupRequest();

        request.setADDRESS(reqAddr);

        ZoneLookupResponseWrapper responseWrapper = null;
        try
        {
            responseWrapper = zoneServicePort.lookupZone(request);
        } catch (ZoneLookupFault_Exception e)
        {
            throw new KKException("Could not validate Address", e);
        }

        boolean validationResult = false;
        for (MessageType msg : responseWrapper.getZONELOOKUPRESPONSE().getMESSAGE())
        {
            if (msg.getCODE() != null && msg.getCODE().equals("AV_SUCCESSFUL_PROCESSING"))
            {
                validationResult = true;
            }
        }

        String respMessages = "Response Messages : \n";
        if (responseWrapper.getZONELOOKUPRESPONSE() != null
                && responseWrapper.getZONELOOKUPRESPONSE().getMESSAGE() != null)
        {
            for (MessageType msg : responseWrapper.getZONELOOKUPRESPONSE().getMESSAGE())
            {
                respMessages += " \t Severity " + msg.getSEVERITY() + " = " + msg.getMESSAGETEXT()
                        + "\n";
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug(respMessages);
        }

        // Create the clean address only if we get one address back

        if (responseWrapper.getZONELOOKUPRESPONSE() != null
                && responseWrapper.getZONELOOKUPRESPONSE().getADDRESS() != null
                && responseWrapper.getZONELOOKUPRESPONSE().getADDRESS().size() == 1)
        {
            for (ZoneLookupResponseAddressType addrT : responseWrapper.getZONELOOKUPRESPONSE()
                    .getADDRESS())
            {
                if (addrT.getCITY() != null && addrT.getCITY().getNAME() != null)
                {
                    cleanAddr.setCITY(addrT.getCITY().getNAME());
                }

                if (addrT.getCOUNTY() != null && addrT.getCOUNTY().getNAME() != null)
                {
                    cleanAddr.setCOUNTY(addrT.getCOUNTY().getNAME());
                }

                if (addrT.getSTATE() != null && addrT.getSTATE().getNAME() != null)
                {
                    cleanAddr.setSTATE(addrT.getSTATE().getNAME());
                }

                if (addrT.getPOSTCODE() != null && addrT.getPOSTCODE().getNAME() != null)
                {
                    cleanAddr.setPOSTCODE(addrT.getPOSTCODE().getNAME());
                }

                if (addrT.getGEOCODE() != null && addrT.getGEOCODE().getNAME() != null)
                {
                    cleanAddr.setGEOCODE(addrT.getGEOCODE().getNAME());
                }

                if (addrT.getCOUNTRY() != null && addrT.getCOUNTRY().getNAME() != null)
                {
                    cleanAddr.setCOUNTRY(addrT.getCOUNTRY().getNAME());
                }
            }
        }

        String respAddresses = "Response Addresses : \n";
        if (responseWrapper.getZONELOOKUPRESPONSE() != null
                && responseWrapper.getZONELOOKUPRESPONSE().getADDRESS() != null)
        {
            for (ZoneLookupResponseAddressType addrT : responseWrapper.getZONELOOKUPRESPONSE()
                    .getADDRESS())
            {
                respAddresses += " \t";

                if (addrT.getCITY() != null && addrT.getCITY().getNAME() != null)
                {
                    respAddresses += " City: " + addrT.getCITY().getNAME();
                }

                if (addrT.getCOUNTY() != null && addrT.getCOUNTY().getNAME() != null)
                {
                    respAddresses += " County: " + addrT.getCOUNTY().getNAME();
                }

                if (addrT.getSTATE() != null && addrT.getSTATE().getNAME() != null)
                {
                    respAddresses += " State: " + addrT.getSTATE().getNAME();
                }

                if (addrT.getPOSTCODE() != null && addrT.getPOSTCODE().getNAME() != null)
                {
                    respAddresses += " Postcode: " + addrT.getPOSTCODE().getNAME();
                }

                if (addrT.getGEOCODE() != null && addrT.getGEOCODE().getNAME() != null)
                {
                    respAddresses += " Geocode: " + addrT.getGEOCODE().getNAME();
                }

                if (addrT.getCOUNTRY() != null && addrT.getCOUNTRY().getNAME() != null)
                {
                    respAddresses += " Country: " + addrT.getCOUNTRY().getNAME() + "\n";
                }
            }
        }

        if (log.isDebugEnabled())
        {
            log.debug(respAddresses);
        }

        return validationResult;
    }

    private String getInvoiceDate(Order order)
    {
        if (order.getDatePurchased() != null)
        {
            return getThomsonsDate(order.getDatePurchased());
        }
        if (order.getDateFinished() != null)
        {
            return getThomsonsDate(order.getDateFinished());
        }

        return getThomsonsDate(new GregorianCalendar());
    }

    private String getThomsonsDate(Calendar dt)
    {
        return getThomsonDateFormat().format(dt.getTime());
    }

    /**
     * @return the Thomson date Format Formatter
     */
    public static SimpleDateFormat getThomsonDateFormat()
    {
        return thomsonDateFormat;
    }

    /**
     * Used to store the static data of this module
     */
    protected class StaticData
    {
        private int sortOrder = -1;

        private String companyName;

        /**
         * @return the companyName
         */
        public String getCompanyName()
        {
            return companyName;
        }

        /**
         * @param companyName
         *            the companyName to set
         */
        public void setCompanyName(String companyName)
        {
            this.companyName = companyName;
        }

        private String companyRole;

        private String externalCompanyId;

        private String sellerRole;

        private String username;

        private String password;

        private boolean secure;

        private int customCodeCustomField = -1;

        private int usCommodityCodeCustomField = -1;

        private int commodityCodeCustomField = -1;

        private int mossCodeCustomField = -1;

        private int taxIdField = -1;

        private URL taxServiceEndpoint;

        private URL zoneServiceEndpoint;

        private ZoneAddressType sellerAddr = null;

        private ZoneAddressType shipFromAddr = null;

        private String street;

        private String county;

        private String state;

        private String city;

        private String postcode;

        private String country;

        private String shipFromStreet;

        private String shipFromCounty;

        private String shipFromState;

        private String shipFromCity;

        private String shipFromPostcode;

        private String shipFromCountry;

        private boolean commitStatus = false;

        /**
         * @return the commitStatus
         */
        public boolean isCommitStatus()
        {
            return commitStatus;
        }

        /**
         * @param commitStatus
         *            the commitStatus to set
         */
        public void setCommitStatus(boolean commitStatus)
        {
            this.commitStatus = commitStatus;
        }

        /**
         * @return the sellerAddr
         */
        public ZoneAddressType getSellerAddr()
        {
            return sellerAddr;
        }

        /**
         * @param sellerAddr
         *            the sellerAddr to set
         */
        public void setSellerAddr(ZoneAddressType sellerAddr)
        {
            this.sellerAddr = sellerAddr;
        }

        /**
         * @return the shipFromAddr
         */
        public ZoneAddressType getShipFromAddr()
        {
            return shipFromAddr;
        }

        /**
         * @param shipFromAddr
         *            the shipFromAddr to set
         */
        public void setShipFromAddr(ZoneAddressType shipFromAddr)
        {
            this.shipFromAddr = shipFromAddr;
        }

        /**
         * @return the taxServiceEndpoint
         */
        public URL getTaxServiceEndpoint()
        {
            return taxServiceEndpoint;
        }

        /**
         * @param taxServiceEndpoint
         *            the taxServiceEndpoint to set
         */
        public void setTaxServiceEndpoint(URL taxServiceEndpoint)
        {
            this.taxServiceEndpoint = taxServiceEndpoint;
        }

        /**
         * @return the zoneServiceEndpoint
         */
        public URL getZoneServiceEndpoint()
        {
            return zoneServiceEndpoint;
        }

        /**
         * @param zoneServiceEndpoint
         *            the zoneServiceEndpoint to set
         */
        public void setZoneServiceEndpoint(URL zoneServiceEndpoint)
        {
            this.zoneServiceEndpoint = zoneServiceEndpoint;
        }

        /**
         * @return the shipFromStreet
         */
        public String getShipFromStreet()
        {
            return shipFromStreet;
        }

        /**
         * @param shipFromStreet
         *            the shipFromStreet to set
         */
        public void setShipFromStreet(String shipFromStreet)
        {
            this.shipFromStreet = shipFromStreet;
        }

        /**
         * @return the shipFromCounty
         */
        public String getShipFromCounty()
        {
            return shipFromCounty;
        }

        /**
         * @param shipFromCounty
         *            the shipFromCounty to set
         */
        public void setShipFromCounty(String shipFromCounty)
        {
            this.shipFromCounty = shipFromCounty;
        }

        /**
         * @return the shipFromState
         */
        public String getShipFromState()
        {
            return shipFromState;
        }

        /**
         * @param shipFromState
         *            the shipFromState to set
         */
        public void setShipFromState(String shipFromState)
        {
            this.shipFromState = shipFromState;
        }

        /**
         * @return the shipFromCity
         */
        public String getShipFromCity()
        {
            return shipFromCity;
        }

        /**
         * @param shipFromCity
         *            the shipFromCity to set
         */
        public void setShipFromCity(String shipFromCity)
        {
            this.shipFromCity = shipFromCity;
        }

        /**
         * @return the shipFromPostcode
         */
        public String getShipFromPostcode()
        {
            return shipFromPostcode;
        }

        /**
         * @param shipFromPostcode
         *            the shipFromPostcode to set
         */
        public void setShipFromPostcode(String shipFromPostcode)
        {
            this.shipFromPostcode = shipFromPostcode;
        }

        /**
         * @return the shipFromCountry
         */
        public String getShipFromCountry()
        {
            return shipFromCountry;
        }

        /**
         * @param shipFromCountry
         *            the shipFromCountry to set
         */
        public void setShipFromCountry(String shipFromCountry)
        {
            this.shipFromCountry = shipFromCountry;
        }

        private String callingSys;

        /**
         * @return the callingSys
         */
        public String getCallingSys()
        {
            return callingSys;
        }

        /**
         * @param callingSys
         *            the callingSys to set
         */
        public void setCallingSys(String callingSys)
        {
            this.callingSys = callingSys;
        }

        private String defaultCurrency;

        /**
         * @return the sortOrder
         */
        public int getSortOrder()
        {
            return sortOrder;
        }

        /**
         * @param sortOrder
         *            the sortOrder to set
         */
        public void setSortOrder(int sortOrder)
        {
            this.sortOrder = sortOrder;
        }

        /**
         * @return the companyRole
         */
        public String getCompanyRole()
        {
            return companyRole;
        }

        /**
         * @param companyRole
         *            the companyRole to set
         */
        public void setCompanyRole(String companyRole)
        {
            this.companyRole = companyRole;
        }

        /**
         * @return the externalCompanyId
         */
        public String getExternalCompanyId()
        {
            return externalCompanyId;
        }

        /**
         * @param externalCompanyId
         *            the externalCompanyId to set
         */
        public void setExternalCompanyId(String externalCompanyId)
        {
            this.externalCompanyId = externalCompanyId;
        }

        /**
         * @return the sellerRole
         */
        public String getSellerRole()
        {
            return sellerRole;
        }

        /**
         * @param sellerRole
         *            the sellerRole to set
         */
        public void setSellerRole(String sellerRole)
        {
            this.sellerRole = sellerRole;
        }

        /**
         * @return the commodityCodeCustomField
         */
        public int getCommodityCodeCustomField()
        {
            return commodityCodeCustomField;
        }

        /**
         * @param commodityCodeCustomField
         *            the commodityCodeCustomField to set
         */
        public void setCommodityCodeCustomField(int commodityCodeCustomField)
        {
            this.commodityCodeCustomField = commodityCodeCustomField;
        }

        /**
         * @return the postcode
         */
        public String getPostcode()
        {
            return postcode;
        }

        /**
         * @param postcode
         *            the postcode to set
         */
        public void setPostcode(String postcode)
        {
            this.postcode = postcode;
        }

        /**
         * @return the city
         */
        public String getCity()
        {
            return city;
        }

        /**
         * @param city
         *            the city to set
         */
        public void setCity(String city)
        {
            this.city = city;
        }

        /**
         * @return the country
         */
        public String getCountry()
        {
            return country;
        }

        /**
         * @param country
         *            the country to set
         */
        public void setCountry(String country)
        {
            this.country = country;
        }

        /**
         * @return the street
         */
        public String getStreet()
        {
            return street;
        }

        /**
         * @param street
         *            the street to set
         */
        public void setStreet(String street)
        {
            this.street = street;
        }

        /**
         * @return the county
         */
        public String getCounty()
        {
            return county;
        }

        /**
         * @param county
         *            the county to set
         */
        public void setCounty(String county)
        {
            this.county = county;
        }

        /**
         * @return the defaultCurrency
         */
        public String getDefaultCurrency()
        {
            return defaultCurrency;
        }

        /**
         * @param defaultCurrency
         *            the defaultCurrency to set
         */
        public void setDefaultCurrency(String defaultCurrency)
        {
            this.defaultCurrency = defaultCurrency;
        }

        /**
         * @return the username
         */
        public String getUsername()
        {
            return username;
        }

        /**
         * @param username
         *            the username to set
         */
        public void setUsername(String username)
        {
            this.username = username;
        }

        /**
         * @return the password
         */
        public String getPassword()
        {
            return password;
        }

        /**
         * @param password
         *            the password to set
         */
        public void setPassword(String password)
        {
            this.password = password;
        }

        /**
         * @return the secure
         */
        public boolean isSecure()
        {
            return secure;
        }

        /**
         * @param secure
         *            the secure to set
         */
        public void setSecure(boolean secure)
        {
            this.secure = secure;
        }

        /**
         * @return the state
         */
        public String getState()
        {
            return state;
        }

        /**
         * @param state
         *            the state to set
         */
        public void setState(String state)
        {
            this.state = state;
        }

        /**
         * @return the taxIdField
         */
        public int getTaxIdField()
        {
            return taxIdField;
        }

        /**
         * @param taxIdField
         *            the taxIdField to set
         */
        public void setTaxIdField(int taxIdField)
        {
            this.taxIdField = taxIdField;
        }

        /**
         * @return the usCommodityCodeCustomField
         */
        public int getUsCommodityCodeCustomField()
        {
            return usCommodityCodeCustomField;
        }

        /**
         * @param usCommodityCodeCustomField
         *            the usCommodityCodeCustomField to set
         */
        public void setUsCommodityCodeCustomField(int usCommodityCodeCustomField)
        {
            this.usCommodityCodeCustomField = usCommodityCodeCustomField;
        }

        /**
         * @return the mossCodeCustomField
         */
        public int getMossCodeCustomField()
        {
            return mossCodeCustomField;
        }

        /**
         * @param mossCodeCustomField
         *            the mossCodeCustomField to set
         */
        public void setMossCodeCustomField(int mossCodeCustomField)
        {
            this.mossCodeCustomField = mossCodeCustomField;
        }

        /**
         * @return the customCodeCustomField
         */
        public int getCustomCodeCustomField()
        {
            return customCodeCustomField;
        }

        /**
         * @param customCodeCustomField
         *            the customCodeCustomField to set
         */
        public void setCustomCodeCustomField(int customCodeCustomField)
        {
            this.customCodeCustomField = customCodeCustomField;
        }

    }
}
