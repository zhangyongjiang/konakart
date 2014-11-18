package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - UpdateCountry - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class UpdateCountry
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public UpdateCountry(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public int updateCountry(String sessionId, AdminCountry country) throws KKAdminException
     {
         return kkAdminEng.updateCountry(sessionId, country);
     }
}
