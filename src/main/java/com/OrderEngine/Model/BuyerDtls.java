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
@Document(collection = "buyer_dtls")
public class BuyerDtls {

    @Id
    private String id;

    private String instrumentId;

    private Integer buyerId;

    private float buyPrice;

    private Integer quantity;


    private Date buyDate;
}
