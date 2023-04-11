package com.maid.cafe.c.maidcafec.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

//todo change aliases
@NamedQuery(name = "Product.getAllProduct",
        query = "select new com.maid.cafe.c.maidcafec.Wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status, p.category.id, p.category.name) from Product p")
@NamedQuery(name = "Product.updateProductStatus", query = "update Product p set p.status = :status where p.id = :id")
@NamedQuery(name = "Product.getProductByCategory", query = "select new com.maid.cafe.c.maidcafec.Wrapper.ProductWrapper(p.id, p.name) from Product p where p.category.id = :id and p.status='true'")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "product")
public class Product implements Serializable {
    public static final long serialVersionUID = 177013L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Integer price; // todo maybe make price double

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;
}
