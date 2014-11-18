package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - InsertCustomerTag - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class InsertCustomerTag
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public InsertCustomerTag(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public int insertCustomerTag(String sessionId, AdminCustomerTag tag) throws KKAdminException
     {
         return kkAdminEng.insertCustomerTag(sessionId, tag);
     }
}
