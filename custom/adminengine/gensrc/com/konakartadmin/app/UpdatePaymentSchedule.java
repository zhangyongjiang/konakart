package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - UpdatePaymentSchedule - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class UpdatePaymentSchedule
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public UpdatePaymentSchedule(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public int updatePaymentSchedule(String sessionId, AdminPaymentSchedule ps) throws KKAdminException
     {
         return kkAdminEng.updatePaymentSchedule(sessionId, ps);
     }
}
