package controller;

import model.Product;
import model.ProductHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import services.ProductHistoryService;
import services.ProductService;
import services.UserAuthentication;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/ws")
public class WebServiceController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductHistoryService productHistoryService;

    @Autowired
    private UserAuthentication userAuthentication;

    @RequestMapping
    @ResponseBody
    public String home(HttpServletRequest request) {
        Product product = productService.getProductByWeightId(request.getParameter("id"));
        ProductHistory productHistory = new ProductHistory(product);

        if (product == null) {
            product = new Product(request.getParameter("id"), Double.parseDouble(request.getParameter("weight")));
        }
        else {
            productHistoryService.save(productHistory);
            product.setMaxWeight(Math.max(product.getWeight(),Double.parseDouble(request.getParameter("weight")) ));
            product.setLastWeight(product.getWeight());
            product.setLastWeightChange(new Date());
            product.setWeight(Double.parseDouble(request.getParameter("weight")));
        }
        productService.save(product);
        return "ok";
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> getProducts(@RequestParam("sessionId") String sessionId) {
        return userAuthentication.updateSessionId(sessionId) ? productService.getActiveProductList() : new ArrayList<Product>();
    }

    @RequestMapping(value = "/productsHistory", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> getProductsHistory(@RequestParam("sessionId") String sessionId) {
        return userAuthentication.updateSessionId(sessionId) ? productService.historyListToProductList(productHistoryService.findAll()) : new ArrayList<Product>();
    }

    @RequestMapping(value = "/productsfree", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> getProductsWithoutKey() {
        return productService.getActiveProductList();
    }

    @RequestMapping(value = "/productsfree", method = RequestMethod.POST)
    @ResponseBody
    public List<Product> getProductsWithoutKeyPOST() {
        return productService.getActiveProductList();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam("login") String login, @RequestParam("password") String password) {
        return userAuthentication.getSessionIDForUser(login, password);
    }
}
