package controller;

import javax.inject.Inject;
import javax.jws.WebService;

import services.AdminService;

@WebService
public class AdminSOAPController {
	
	@Inject
	AdminService adminService;
	
	public void addUser(String name, String password){
		adminService.addUser(name, password);
	}
	
	public void removeUser(long id){
		adminService.removeUser(id);
	}
	
	public void deleteComment(long id){
		adminService.deleteComment(id);
	}
	
	public void setAdmin(long id){
		adminService.setAdmin(id);
	}

}
