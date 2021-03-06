package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - UpdateConfigurationGroup - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class UpdateConfigurationGroup
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public UpdateConfigurationGroup(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public void updateConfigurationGroup(String sessionId, AdminConfigurationGroup newConf) throws KKAdminException
     {
         kkAdminEng.updateConfigurationGroup(sessionId, newConf);
     }
}
