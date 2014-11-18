package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - GetRolesPerSessionId - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class GetRolesPerSessionId
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public GetRolesPerSessionId(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public AdminRole[] getRolesPerSessionId(String sessionId) throws KKAdminException
     {
         return kkAdminEng.getRolesPerSessionId(sessionId);
     }
}
