package com.OrderEngine.Repository;

import com.OrderEngine.Model.OrderRecords;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderRecords,String> {

    OrderRecords findTopByOrderByOrderIdDesc();
}
