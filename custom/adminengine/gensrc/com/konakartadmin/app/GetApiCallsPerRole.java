package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - GetApiCallsPerRole - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class GetApiCallsPerRole
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public GetApiCallsPerRole(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public AdminApiCall[] getApiCallsPerRole(String sessionId, int roleId) throws KKAdminException
     {
         return kkAdminEng.getApiCallsPerRole(sessionId, roleId);
     }
}