package services;

import DAO.FridgeDao;
import model.Fridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FridgeService {

    @Autowired
    private FridgeDao fridgeDao;

    public boolean fridgeExist(String name){
        return fridgeDao.findByName(name) == null ? false : true;
    }

    public List<Fridge> findAll(){
        return fridgeDao.findAll();
    }

    public void save(Fridge fridge){
        fridgeDao.save(fridge);
    }
}
