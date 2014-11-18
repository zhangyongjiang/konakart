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

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Handler Resolver
 */
public class HeaderHandlerResolver implements HandlerResolver
{
    private String uName = "not-set-yet";

    private String pWord = "not-set-yet";

    private boolean secure = false;

    /**
     * The <code>Log</code> instance for this application.
     */
    protected Log log = LogFactory.getLog(HeaderHandlerResolver.class);

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
     * @param username
     */
    public void setUName(String username)
    {
        uName = username;
    }

    /**
     * @param password
     */
    public void setPWord(String password)
    {
        pWord = password;
    }

    /**
     * @return the uName
     */
    public String getUName()
    {
        return uName;
    }

    /**
     * @return the pWord
     */
    public String getPWord()
    {
        return pWord;
    }

    public List<Handler> getHandlerChain(PortInfo portInfo)
    {
        List<Handler> handlerChain = new ArrayList<Handler>();

        if (isSecure())
        {
            HeaderSecrityHandler hsh = new HeaderSecrityHandler();
            hsh.setUName(getUName());
            hsh.setPWord(getPWord());

            handlerChain.add(hsh);
        }

        HeaderLoggingHandler hlh = new HeaderLoggingHandler();

        handlerChain.add(hlh);

        return handlerChain;
    }
}