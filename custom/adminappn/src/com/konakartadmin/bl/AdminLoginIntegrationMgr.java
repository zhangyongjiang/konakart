//
// (c) 2006 DS Data Systems UK Ltd, All rights reserved.
//
// DS Data Systems and KonaKart and their respective logos, are 
// trademarks of DS Data Systems UK Ltd. All rights reserved.
//
// The information in this document is the proprietary property of
// DS Data Systems UK Ltd. and is protected by English copyright law,
// the laws of foreign jurisdictions, and international treaties,
// as applicable. No part of this document may be reproduced,
// transmitted, transcribed, transferred, modified, published, or
// translated into any language, in any form or by any means, for
// any purpose other than expressly permitted by DS Data Systems UK Ltd.
// in writing.
//
package com.konakartadmin.bl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.konakartadmin.app.KKAdminException;
import com.konakartadmin.appif.KKAdminIf;

/**
 * Used to provide an integration point when a customer attempts a login so that custom security
 * logic can be implemented.
 */
public class AdminLoginIntegrationMgr extends AdminBaseMgr implements
        AdminLoginIntegrationMgrInterface
{
    /** the log */
    protected static Log log = LogFactory.getLog(AdminLoginIntegrationMgr.class);

    /**
     * Constructor
     * 
     * @param eng
     *            KKAdmin engine
     * @throws Exception
     */
    public AdminLoginIntegrationMgr(KKAdminIf eng) throws Exception
    {
        super.init(eng);

        if (log.isDebugEnabled())
        {
            if (eng != null && eng.getEngConf() != null && eng.getEngConf().getStoreId() != null)
            {
                log.debug("AdminLoginIntegrationMgr instantiated for store id = "
                        + eng.getEngConf().getStoreId());
            }
        }

    }

    /**
     * Called whenever a login attempt is made. This method should return:
     * <ul>
     * <li>A negative number in order for the login attempt to fail. The KonaKart login() method
     * will return a null sessionId</li>
     * <li>Zero to signal that this method is not implemented. The KonaKart login() method will
     * perform the credentials check.</li>
     * <li>A positive number for the login attempt to pass. The KonaKart login() will not check
     * credentials, and will log in the customer, returning a valid session id.</li>
     * </ul>
     * 
     * @param emailAddr
     *            The user name required to log in
     * @param password
     *            The log in password
     * @return Returns an integer
     * @throws KKAdminException
     */
    public int checkCredentials(String emailAddr, String password) throws KKAdminException
    {
        if (log.isDebugEnabled())
        {
            log.debug("returning 0 => KonaKart to check login credentials");
        }
        return 0;
    }
}
