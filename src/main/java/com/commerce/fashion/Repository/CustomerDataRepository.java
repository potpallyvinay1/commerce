package com.commerce.fashion.Repository;

import com.commerce.fashion.entity.CustomerData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerDataRepository extends MongoRepository<CustomerData,Integer> {
    CustomerData findByEmailId(String emailId);
}
