package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - EditProduct - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class EditProduct
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public EditProduct(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public void editProduct(String sessionId, AdminProduct product) throws KKAdminException
     {
         kkAdminEng.editProduct(sessionId, product);
     }
}