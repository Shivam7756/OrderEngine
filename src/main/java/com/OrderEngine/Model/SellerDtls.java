package com.OrderEngine.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection="seller_dtls")
public class SellerDtls {

    @Id
    private String id;

    private String instrumentId;

    private float sellPrice;

    private Integer quantity;

    private Integer sellerId;

    private Date sellDate;
}
