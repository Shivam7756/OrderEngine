package com.OrderEngine.Repository;

import com.OrderEngine.Model.BuyerDtls;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BuyerRepository extends MongoRepository<BuyerDtls,String> {

    BuyerDtls findTopByOrderByBuyerIdDesc();
}
