package com.konakartadmin.app;

import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - GetHtml - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class GetHtml
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public GetHtml(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public String getHtml(String sessionId, int htmlType, int id, int languageId, String arg1) throws KKAdminException
     {
         return kkAdminEng.getHtml(sessionId, htmlType, id, languageId, arg1);
     }
}
