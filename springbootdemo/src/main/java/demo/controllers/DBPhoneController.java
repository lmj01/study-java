package demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.Phone;
import demo.repository.RepositoryPhone;

@RestController
public class DBPhoneController {

	@Autowired
	private RepositoryPhone repoPhone;
	
	// http://localhost:9090/phone/add?phoneNumber=1234512312&code=12
	@GetMapping(path="/phone/add")
	public @ResponseBody String addPhoneCode(@RequestParam String phoneNumber, @RequestParam Integer code) {
		
		Phone phone = new Phone();
		phone.setPhoneNumber(phoneNumber);
		phone.setCode(code);
		
		repoPhone.save(phone);
		
		return "success !";
	}
	
	// http://localhost:9090/phone/find?phoneNumber=1234512312
	@GetMapping(path="/phone/find")
	public @ResponseBody Phone findPhone(@RequestParam String phoneNumber) {
		System.out.println(phoneNumber);
		return repoPhone.findByPhoneNumber(phoneNumber);
	}
	
	// http://localhost:9090/phone/all
	@GetMapping(path="/phone/all")
	public @ResponseBody Iterable<Phone> getAllPhone() {
		return repoPhone.findAll();
	}
}
