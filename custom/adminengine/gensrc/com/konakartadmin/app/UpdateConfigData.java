package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - UpdateConfigData - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class UpdateConfigData
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public UpdateConfigData(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public void updateConfigData(String sessionId, AdminConfigData[] configs) throws KKAdminException
     {
         kkAdminEng.updateConfigData(sessionId, configs);
     }
}