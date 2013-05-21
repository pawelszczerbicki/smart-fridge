package controller;

import DAO.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;

@Controller
public class HomeController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/")
    public String home(Model model){
        model.addAttribute("products", productDao.getProductList());
        return "index";
    }
    @RequestMapping(value = "/data")
    @ResponseBody
    public Integer data(){
        Random random = new Random();
        return random.nextInt() * 10;
    }
}
