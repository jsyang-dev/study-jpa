package me.study.jpa.chap21;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue
    private Long id;
    private int orderAmount;

    @Embedded
    private me.study.jpa.chap21.Address address;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private me.study.jpa.chap21.Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public me.study.jpa.chap21.Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public me.study.jpa.chap21.Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
