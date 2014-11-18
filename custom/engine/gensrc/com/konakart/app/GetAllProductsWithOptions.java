package com.konakart.app;

import com.konakart.appif.*;

/**
 *  The KonaKart Custom Engine - GetAllProductsWithOptions - Generated by CreateKKCustomEng
 */
@SuppressWarnings("all")
public class GetAllProductsWithOptions
{
    KKEng kkEng = null;

    /**
     * Constructor
     */
     public GetAllProductsWithOptions(KKEng _kkEng)
     {
         kkEng = _kkEng;
     }

     public ProductsIf getAllProductsWithOptions(String sessionId, DataDescriptorIf dataDesc, int languageId, FetchProductOptionsIf options) throws KKException
     {
         return kkEng.getAllProductsWithOptions(sessionId, dataDesc, languageId, options);
     }
}
