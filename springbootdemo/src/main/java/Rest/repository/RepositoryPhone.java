package Rest.repository;

import org.springframework.data.repository.CrudRepository;

import Rest.entity.Phone;

public interface RepositoryPhone extends CrudRepository<Phone, Long> {

	Phone findByPhoneNumber(String phoneNumber);
}
