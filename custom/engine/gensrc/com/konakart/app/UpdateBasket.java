package com.konakart.app;

import com.konakart.appif.*;

/**
 *  The KonaKart Custom Engine - UpdateBasket - Generated by CreateKKCustomEng
 */
@SuppressWarnings("all")
public class UpdateBasket
{
    KKEng kkEng = null;

    /**
     * Constructor
     */
     public UpdateBasket(KKEng _kkEng)
     {
         kkEng = _kkEng;
     }

     public void updateBasket(String sessionId, int customerId, BasketIf item) throws KKException
     {
         kkEng.updateBasket(sessionId, customerId, item);
     }
}
