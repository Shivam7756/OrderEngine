package com.OrderEngine.Repository;


import com.OrderEngine.Model.SellerDtls;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SellerRepository extends MongoRepository<SellerDtls,String> {

    SellerDtls findTopByOrderBySellerIdDesc();

    List<SellerDtls> findByInstrumentId(String instId);
}
