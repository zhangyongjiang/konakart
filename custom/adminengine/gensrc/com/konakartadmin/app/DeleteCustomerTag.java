package com.konakartadmin.app;

import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - DeleteCustomerTag - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class DeleteCustomerTag
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public DeleteCustomerTag(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public int deleteCustomerTag(String sessionId, int id) throws KKAdminException
     {
         return kkAdminEng.deleteCustomerTag(sessionId, id);
     }
}
