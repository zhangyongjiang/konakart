package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - RemoveImage - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class RemoveImage
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public RemoveImage(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public void removeImage(String sessionId, AdminImageOptions options) throws KKAdminException
     {
         kkAdminEng.removeImage(sessionId, options);
     }
}
