package model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.awt.image.BufferedImage;
import java.util.Date;

@Document(collection="products")
public class Product {

    @Id
    private String id;

    private String weightId;

    private String name;

    private String visibleName;

    private Double weight;

    private Double lastWeight;

    private Double price;

    private Integer tolerance;

    private Date lastWeightChange;

    private Double maxWeight;

    private BufferedImage image;

    public Product() {
       super();
    }

    public Product(String id, Double weight) {
        this.id = id;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeightId() {
        return weightId;
    }

    public void setWeightId(String weightId) {
        this.weightId = weightId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLastWeight() {
        return lastWeight;
    }

    public void setLastWeight(Double lastWeight) {
        this.lastWeight = lastWeight;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTolerance() {
        return tolerance;
    }

    public void setTolerance(Integer tolerance) {
        this.tolerance = tolerance;
    }

    public Date getLastWeightChange() {
        return lastWeightChange;
    }

    public void setLastWeightChange(Date lastWeightChange) {
        this.lastWeightChange = lastWeightChange;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", lastWeight=" + lastWeight +
                ", price=" + price +
                ", tolerance=" + tolerance +
                ", lastWeightChange=" + lastWeightChange +
                '}';
    }
}
