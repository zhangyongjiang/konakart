package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - EditReview - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class EditReview
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public EditReview(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public void editReview(String sessionId, AdminReview review) throws KKAdminException
     {
         kkAdminEng.editReview(sessionId, review);
     }
}
