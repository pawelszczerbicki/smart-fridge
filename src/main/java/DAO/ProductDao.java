package DAO;

import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> getProductList() {
        return mongoTemplate.findAll(Product.class);
    }

    public List<Product> getActiveProductList(Query query) {
        return mongoTemplate.find(query,Product.class);
    }

    public Product getProductById(String id) {
        return mongoTemplate.findById(id, Product.class);
    }

    public Product getProductByName(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), Product.class);
    }

    public Product getProductByWeightId(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("weightId").is(id)), Product.class);
    }

    public void deleteProductById(String id){
        mongoTemplate.findAndRemove(new Query(Criteria.where("id").is(id)),Product.class);
    }
    public void save(Product product) {
        mongoTemplate.save(product);
    }
    public List<Product> getProductByQuery(Query q){
        return mongoTemplate.find(q,Product.class);
    }
}
