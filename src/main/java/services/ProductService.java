package services;

import DAO.ProductDao;
import model.Product;
import model.ProductHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public void setActiveById(String id, boolean active) {
        Product product = productDao.getProductById(id);
        product.setActive(active);
        productDao.save(product);
    }

    public void deleteProductById(String id) {
        productDao.deleteProductById(id);
    }

    public List<Product> getActiveProductList() {
        return productDao.getActiveProductList(new Query(Criteria.where("active").is(true)));
    }

    public Product getProductById(String id) {
        return productDao.getProductById(id);
    }

    public List<Product> getDeactivatedProducts() {
        return productDao.getProductByQuery(Query.query(Criteria.where("active").is(false)));
    }

    public Product getProductByWeightId(String id) {
        return productDao.getProductByWeightId(id);
    }

    public void  save(Product product){
        productDao.save(product);
    }

    public List<Product> getProductList(){
        return productDao.getProductList();
    }
    public void editProduct(Product newProduct, Product oldProduct) {
        oldProduct.setVisibleName(newProduct.getVisibleName());
        oldProduct.setPrice(newProduct.getPrice());
        oldProduct.setTolerance(newProduct.getTolerance());
        oldProduct.setDescription(newProduct.getDescription());
        productDao.save(oldProduct);
    }

    public List<Product> historyListToProductList(List<ProductHistory> productHistories){
        List<Product> products = new ArrayList<Product>();
        for (ProductHistory productHistory : productHistories){
            products.add(productHistory.getProduct());
        }
        return products;
    }

}
