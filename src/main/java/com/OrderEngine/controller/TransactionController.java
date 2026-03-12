package com.OrderEngine.controller;

import com.OrderEngine.Model.BuyerDtls;
import com.OrderEngine.Model.OrderRecords;
import com.OrderEngine.Model.SellerDtls;
import com.OrderEngine.Service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/OrderEngine")
@Controller
public class TransactionController {

   @Autowired
    TransactionService transactionService;

    @ResponseBody
    @RequestMapping(value="/makeBuyOrder",method = {RequestMethod.GET,RequestMethod.POST})
    public BuyerDtls makeBuyOrder(HttpServletRequest request)
    {
        String instrId = request.getParameter("instrumentId");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        float buyPrice = Float.parseFloat(request.getParameter("price"));

      return  transactionService.saveBuyOrder(instrId,quantity,buyPrice);
    }


    @ResponseBody
    @RequestMapping(value="/makeSellOrder" , method = {RequestMethod.GET , RequestMethod.POST})
    public SellerDtls makeSellOrder(HttpServletRequest request)
    {
        String instrId = request.getParameter("instrId");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));
        float sellPrice = Float.parseFloat(request.getParameter("price"));

        return transactionService.makeSellOrder(instrId,quantity,sellPrice);
    }

    @ResponseBody
    @RequestMapping(value="/executeOrder",method={RequestMethod.GET,RequestMethod.POST})
    public List<OrderRecords> executeOrder()
    {
        return transactionService.executeBatchProcessedOrders();
    }

    @ResponseBody
    @RequestMapping(value="/unfulfilledOrders" , method = {RequestMethod.GET,RequestMethod.POST})
    public ArrayList<ArrayList<Objects>> showUnfulfilledOrders()
    {
        return transactionService.showUnfulfilledOrders();
    }
}
