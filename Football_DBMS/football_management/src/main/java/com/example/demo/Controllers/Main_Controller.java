package com.example.demo.Controllers;


//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;




@Controller
public class Main_Controller {
	

    
	
	
	@GetMapping("/Home")
    public String showHomePage(Model model) {
        return "Home"; 
    }
	
}
