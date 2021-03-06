package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - EditOrder - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class EditOrder
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public EditOrder(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public void editOrder(String sessionId, AdminOrder order) throws KKAdminException
     {
         kkAdminEng.editOrder(sessionId, order);
     }
}
