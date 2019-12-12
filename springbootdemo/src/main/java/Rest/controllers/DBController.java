package Rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Rest.entity.Phone;
import Rest.repository.RepositoryPhone;

@RestController
public class DBController {

	@Autowired
	private RepositoryPhone repoPhone;
	
	@GetMapping(path="/add")
	public @ResponseBody String addPhoneCode(@RequestParam String phoneNumber, @RequestParam Integer code) {
		
		Phone phone = new Phone();
		phone.setPhoneNumber(phoneNumber);
		phone.setCode(code);
		
		repoPhone.save(phone);
		
		return "success !";
	}
	
	@GetMapping(path="/find")
	public @ResponseBody Phone findPhone(@RequestParam String phoneNumber) {
		System.out.println(phoneNumber);
		return repoPhone.findByPhoneNumber(phoneNumber);
	}
	
	@GetMapping(path="/all")
	public @ResponseBody Iterable<Phone> getAllPhone() {
		return repoPhone.findAll();
	}
}
