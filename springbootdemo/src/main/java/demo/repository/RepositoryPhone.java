package demo.repository;

import org.springframework.data.repository.CrudRepository;

import demo.entity.Phone;

public interface RepositoryPhone extends CrudRepository<Phone, Long> {

	Phone findByPhoneNumber(String phoneNumber);
}
