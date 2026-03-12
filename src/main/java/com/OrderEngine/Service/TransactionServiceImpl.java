package com.OrderEngine.Service;


import com.OrderEngine.Model.BuyerDtls;
import com.OrderEngine.Model.OrderRecords;
import com.OrderEngine.Model.SellerDtls;
import com.OrderEngine.Repository.BuyerRepository;
import com.OrderEngine.Repository.OrderRepository;
import com.OrderEngine.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import org.springframework.data.mongodb.core.query.Query;
import java.util.Date;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    BuyerRepository buyerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SellerRepository sellerRepository;

    private final MongoTemplate mongoTemplate;

    public TransactionServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public BuyerDtls saveBuyOrder(String instrId, Integer quantity , float price)
    {
        BuyerDtls bdtls = buyerRepository.findTopByOrderByBuyerIdDesc();
        Integer buyerId = 1;
        Integer flg = 1;
        BuyerDtls buyRecord = new BuyerDtls();

        if(bdtls!=null)
            buyerId = bdtls.getBuyerId()+1;

        try
        {

            buyRecord.setInstrumentId(instrId);
            buyRecord.setBuyerId(buyerId);
            buyRecord.setBuyPrice(price);
            buyRecord.setQuantity(quantity);
            buyRecord.setBuyDate(new Date());
            buyerRepository.save(buyRecord);
        }

        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return buyRecord;
    }

    public SellerDtls makeSellOrder(String instrId,Integer quantity,float sellPrice)
    {
        SellerDtls sellerDtls = sellerRepository.findTopByOrderBySellerIdDesc();
        Integer sellerId  = 1;

        if(sellerDtls!=null)
            sellerId = sellerDtls.getSellerId()+1;

        Integer flg = 0;
        SellerDtls sellerRecords = new SellerDtls();

        try{
            sellerRecords.setInstrumentId(instrId);
            sellerRecords.setSellerId(sellerId);
            sellerRecords.setSellPrice(sellPrice);
            sellerRecords.setQuantity(quantity);
            sellerRecords.setSellDate(new Date());
            sellerRepository.save(sellerRecords);
            flg = 1;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            throw  new RuntimeException();
        }

        return sellerRecords;
    }


    public List<OrderRecords> executeBatchProcessedOrders() {
          List<BuyerDtls> bdtls = buyerRepository.findAll();

         bdtls.sort(Comparator.comparing(BuyerDtls::getInstrumentId)
                 .thenComparing(BuyerDtls::getBuyPrice,Comparator.reverseOrder())
                 .thenComparing(BuyerDtls::getBuyDate));


         for(int i = 0;i<bdtls.size();i++)
         {
             BuyerDtls buyerDtls = bdtls.get(i);
             calculateMatchStock(buyerDtls);
         }

         List<OrderRecords> executedOrderList = orderRepository.findAll();
         return executedOrderList;

    }

    public void calculateMatchStock(BuyerDtls buyerDtls)
    {
        Integer quantity = 0;
        List<SellerDtls> sdtls = sellerRepository.findByInstrumentId(buyerDtls.getInstrumentId());

        sdtls.sort(Comparator.comparing(SellerDtls::getInstrumentId).
                thenComparing(SellerDtls::getSellPrice).thenComparing(SellerDtls::getSellDate));

        for(int i = 0;i<sdtls.size();i++)
        {
           SellerDtls  curSellerDtls = sdtls.get(i);

           if(buyerDtls.getQuantity()==0||buyerDtls.getBuyPrice()<curSellerDtls.getSellPrice())
               break;
           else if(curSellerDtls.getQuantity()==0)
               continue;
           else
           {
               int buyQty = buyerDtls.getQuantity();
               int sellQty = curSellerDtls.getQuantity();

               int soldQty = Math.min(buyQty,sellQty);

               buyerDtls.setQuantity(buyerDtls.getQuantity()-soldQty);
               curSellerDtls.setQuantity(curSellerDtls.getQuantity()-soldQty);

               buyerRepository.save(buyerDtls);
               sellerRepository.save(curSellerDtls);

               OrderRecords maxOrderRecords = orderRepository.findTopByOrderByOrderIdDesc();
               int maxOrderId = 1;
               if(maxOrderRecords!=null)
                   maxOrderId = maxOrderRecords.getOrderId()+1;

               OrderRecords orderRecords = new OrderRecords();
               orderRecords.setOrderId(maxOrderId);
               orderRecords.setOrderStrId("O"+maxOrderId);
               orderRecords.setSellingPrice(curSellerDtls.getSellPrice());
               orderRecords.setQuantity(soldQty);
               orderRecords.setBuyerId(buyerDtls.getBuyerId());
               orderRecords.setSellerId(curSellerDtls.getSellerId());
               orderRecords.setOrdertxndate(new Date());

               orderRepository.save(orderRecords);

           }
        }


    }

    public List<Object> showUnfulfilledOrders() {

        Query query = new Query();

        query.addCriteria(Criteria.where("quantity").gt(0));
        List<BuyerDtls> bdtls =  mongoTemplate.find(query, BuyerDtls.class);
        List<SellerDtls> sdtls =  mongoTemplate.find(query, SellerDtls.class);
        List<Object> list = new ArrayList<Object>();
        list.add(bdtls);
        list.add(sdtls);

        return list;

    }
}
