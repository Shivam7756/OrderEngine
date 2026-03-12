package com.OrderEngine.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "order_records")
public class OrderRecords {

    @Id
    private String id;

    private Integer orderId;

    private String orderStrId;

    private float sellingPrice;

    private Integer buyerId;

    private Integer sellerId;

    private Integer quantity;

    private Date ordertxndate;


}
