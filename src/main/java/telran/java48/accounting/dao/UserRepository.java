package telran.java48.accounting.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.java48.accounting.model.User;

public interface UserRepository extends MongoRepository<User, String> {

}
