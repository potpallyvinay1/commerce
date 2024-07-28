package com.commerce.fashion.Repository;

import com.commerce.fashion.entity.RegisterCreds;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationRepository extends MongoRepository<RegisterCreds, String> {

}
