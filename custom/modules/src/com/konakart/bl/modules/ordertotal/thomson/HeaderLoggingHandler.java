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

import java.io.ByteArrayOutputStream;
import java.util.Set;

import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A handler to log messages in a friendly format
 */
public class HeaderLoggingHandler implements SOAPHandler<SOAPMessageContext>
{
    /**
     * The <code>Log</code> instance for this application.
     */
    protected Log log = LogFactory.getLog(HeaderLoggingHandler.class);

    public boolean handleMessage(SOAPMessageContext smc)
    {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        logSoapMsg(smc);
        return outboundProperty;
    }

    @SuppressWarnings(
    { "unchecked", "rawtypes" })
    public Set getHeaders()
    {
        // Not implemented
        return null;
    }

    public boolean handleFault(SOAPMessageContext context)
    {
        logSoapMsg(context);
        return true;
    }

    /**
     * Outputs the soap msg to the logger
     * 
     * @param context
     */
    public void logSoapMsg(SOAPMessageContext context)
    {
        if (!log.isDebugEnabled())
        {
            return;
        }
        
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        String msgType = null;
        if (outboundProperty.booleanValue())
        {
            msgType = "Request:";
        } else
        {
            msgType = "Response:";
        }

        SOAPMessage message = context.getMessage();
        try
        {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();

            // Set formatting

            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            Source sc = message.getSOAPPart().getContent();

            ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(streamOut);
            tf.transform(sc, result);

            if (log.isDebugEnabled())
            {
                log.debug(msgType
                        + "\n"
                        + streamOut.toString()
                        + "\n------------------------------------------------------------------------");
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void close(MessageContext context)
    {
        // Not implemented
    }
}
