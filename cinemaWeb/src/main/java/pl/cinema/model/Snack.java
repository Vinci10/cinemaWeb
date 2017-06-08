package pl.cinema.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;

@Entity
@Table(name="snack")
public class Snack {
    @Id
    @Column(name="snack_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="name")
    @NotEmpty(message="Proszę podać nazwę")
    private String name;
    @Column(name="price")
    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
