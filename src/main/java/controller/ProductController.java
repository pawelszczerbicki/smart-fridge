package controller;

import DAO.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("id") String id, Model model) {
        productDao.deleteProductById(id);
        model.addAttribute("products", productDao.getProductList());
        return "index";
    }
    @RequestMapping(value = "/show/${id}", method = RequestMethod.GET)
    public String showProduct(@PathVariable String id, Model model) {
        model.addAttribute("product", productDao.getProductById(id));
        return "details";
    }

}
