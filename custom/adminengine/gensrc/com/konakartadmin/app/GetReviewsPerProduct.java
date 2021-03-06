package com.konakartadmin.app;

import com.konakartadmin.appif.*;
import com.konakart.app.*;
import com.konakartadmin.bl.KKAdmin;

/**
 *  The KonaKart Custom Engine - GetReviewsPerProduct - Generated by CreateKKAdminCustomEng
 */
@SuppressWarnings("all")
public class GetReviewsPerProduct
{
    KKAdmin kkAdminEng = null;

    /**
     * Constructor
     */
     public GetReviewsPerProduct(KKAdmin _kkAdminEng)
     {
         kkAdminEng = _kkAdminEng;
     }

     public AdminReviews getReviewsPerProduct(String sessionId, AdminDataDescriptor dataDesc, int productId) throws KKAdminException
     {
         return kkAdminEng.getReviewsPerProduct(sessionId, dataDesc, productId);
     }
}
