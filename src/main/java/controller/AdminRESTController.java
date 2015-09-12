package controller;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import entity.User;
import services.AdminService;

@Path("/admin")
public class AdminRESTController {
	
	@Inject
	AdminService adminService;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUserREST(User user){
		adminService.addUserREST(user);
	}

}
