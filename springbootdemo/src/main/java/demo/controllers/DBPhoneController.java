package demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.Phone;
import demo.repository.RepositoryPhone;

@RestController
@RequestMapping(value="/phone", produces= {MediaType.APPLICATION_JSON_VALUE})
public class DBPhoneController {

	@Autowired
	private RepositoryPhone repoPhone;
	
	// http://localhost:9090/phone/add?phoneNumber=1234512312&code=12
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public @ResponseBody String addPhoneCode(@RequestParam String phoneNumber, @RequestParam Integer code) {
		
		Phone phone = new Phone();
		phone.setPhoneNumber(phoneNumber);
		phone.setCode(code);
		
		repoPhone.save(phone);
		
		return "success !";
	}
	
	// http://localhost:9090/phone/find?phoneNumber=1234512312
	//@GetMapping(path="/find")
	@RequestMapping(value="/find", method=RequestMethod.GET)
	public @ResponseBody Phone findPhone(@RequestParam String phoneNumber) {
		System.out.println(phoneNumber);
		return repoPhone.findByPhoneNumber(phoneNumber);
	}
	
	// http://localhost:9090/phone/all
	//@GetMapping(path="/all")
	@RequestMapping(value="/all", method=RequestMethod.GET)
	public @ResponseBody Iterable<Phone> getAllPhone() {
		return repoPhone.findAll();
	}
}
