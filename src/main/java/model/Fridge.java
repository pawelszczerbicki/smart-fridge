package model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Fridge {

    @Id
    private String id;

    private String name;

    private List<Product> products;

    private List<String> notes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", notes=" + notes +
                '}';
    }
}
