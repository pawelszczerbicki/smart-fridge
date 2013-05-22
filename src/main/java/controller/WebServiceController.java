package controller;

import DAO.ProductDao;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import services.UserAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ws")
public class WebServiceController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserAuthentication userAuthentication;

    @RequestMapping
    @ResponseBody
    public String home(HttpServletRequest request) {
        Product product = productDao.getProductByWeightId(request.getParameter("id"));

        if (product == null)
            product = new Product(request.getParameter("id"), Double.parseDouble(request.getParameter("weight")));
        else {
            product.setLastWeight(product.getWeight());
            product.setLastWeightChange(new Date());
            product.setWeight(Double.parseDouble(request.getParameter("weight")));
        }
        productDao.save(product);
        return "ok";
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> getProducts(@RequestParam("sessionId") String sessionId) {
        return userAuthentication.updateSessionId(sessionId) ? productDao.getProductList() : null;
    }

    @RequestMapping(value = "/productsfree", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getProductsWithoutKey() {
        return productDao.getProductList();
    }
    @RequestMapping(value = "/productsfree", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> getProductsWithoutKeyPOST() {
        return productDao.getProductList();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("login") String login, @RequestParam("password") String password) {
        return userAuthentication.getSessionIDForUser(login, password);
    }
}
