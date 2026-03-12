package com.OrderEngine.Service;


import com.OrderEngine.Model.BuyerDtls;
import com.OrderEngine.Model.OrderRecords;
import com.OrderEngine.Model.SellerDtls;

import java.util.List;

public interface TransactionService {

    public BuyerDtls saveBuyOrder(String instrId, Integer quantity , float price);
    public SellerDtls makeSellOrder(String instrId, Integer quantity, float sellPrice);
    public List<OrderRecords> executeBatchProcessedOrders();

    List<Object> showUnfulfilledOrders();
}
