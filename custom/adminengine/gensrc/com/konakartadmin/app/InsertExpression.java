package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - InsertExpression - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class InsertExpression
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public InsertExpression(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public int insertExpression(String sessionId, AdminExpression exp) throws KKAdminException
     {
         return kkAdminEng.insertExpression(sessionId, exp);
     }
}
