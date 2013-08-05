package services;

import DAO.ProductHistoryDao;
import model.ProductHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductHistoryService {

    @Autowired
    ProductHistoryDao productHistoryDao;

    public void save(ProductHistory productHistory){
        productHistoryDao.save(productHistory);
    }

    public List<ProductHistory> findAll() {
        return productHistoryDao.findAll();
    }

}
