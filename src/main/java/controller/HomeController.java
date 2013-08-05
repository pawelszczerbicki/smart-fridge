package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ProductService;

import java.util.Random;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/")
    public String home(Model model) {
        model.addAttribute("products", productService.getActiveProductList());
        return "index";
    }

    @RequestMapping(value = "/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

    @RequestMapping(value = "/data")
    @ResponseBody
    public Integer data() {
        Random random = new Random();
        return random.nextInt() * 10;
    }
}
