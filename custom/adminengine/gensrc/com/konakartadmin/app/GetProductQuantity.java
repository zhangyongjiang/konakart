package com.konakartadmin.app;

import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - GetProductQuantity - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class GetProductQuantity
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public GetProductQuantity(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public int getProductQuantity(String sessionId, String sku, int id) throws KKAdminException
     {
         return kkAdminEng.getProductQuantity(sessionId, sku, id);
     }
}
