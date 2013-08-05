package controller;

import model.Product;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import services.ProductService;

import javax.validation.Valid;
import java.io.InputStream;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("id") String id, Model model) {
        productService.deleteProductById(id);
        model.addAttribute("deleted", true);
        model.addAttribute("products", productService.getActiveProductList());
        return "index";
    }

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public String deactivate(@RequestParam("id") String id, @RequestParam("active") boolean active, Model model) {
        productService.setActiveById(id, active);
        if (active)
            model.addAttribute("activated", true);
        else
            model.addAttribute("activated", false);
        model.addAttribute("product", productService.getProductById(id));
        return "details";
    }

    @RequestMapping(value = "/show/{id}", method = RequestMethod.GET)
    public String showProduct(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "details";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editProduct(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "edit_product";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editProduct(Model model, @ModelAttribute("product") @Valid Product product, BindingResult bindingResult) {
        Product oldProduct = productService.getProductById(product.getId());
        if (bindingResult.hasErrors()) {
            model.addAttribute("fail", true);
        } else {
            productService.editProduct(product, oldProduct);
            model.addAttribute("product_edited", true);
        }
        model.addAttribute("product", productService.getProductById(product.getId()));
        return "edit_product";
    }

    @RequestMapping(value = "/edit/addPhoto", method = RequestMethod.POST)
    public String addPhoto(@RequestParam("image") MultipartFile image, @RequestParam("id") String id, Model model) {
        Product product = productService.getProductById(id);
        try {
            InputStream inputStream = image.getInputStream();
            product.setImage(IOUtils.toByteArray(inputStream));
            productService.save(product);
        } catch (Exception ex) {

        }
        model.addAttribute("product", product);
        return "edit_product";
    }
    @RequestMapping(value = "/getImage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] getPhoto(@PathVariable("id") String id, Model model) {
        return productService.getProductById(id).getImage();
    }


    @RequestMapping(value = "/deactivated", method = RequestMethod.GET)
    public String showDeactivatedProducts(Model model) {
        model.addAttribute("deactivated", true);
        model.addAttribute("products", productService.getDeactivatedProducts());
        return "hidden";
    }

}
