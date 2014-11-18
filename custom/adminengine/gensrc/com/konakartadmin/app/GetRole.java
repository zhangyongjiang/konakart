package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - GetRole - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class GetRole
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public GetRole(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public AdminRole getRole(String sessionId, int roleId) throws KKAdminException
     {
         return kkAdminEng.getRole(sessionId, roleId);
     }
}
