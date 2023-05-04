package com.maid.cafe.c.maidcafec.POJO;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "Product.getAllProduct",
        query = "select new com.maid.cafe.c.maidcafec.Wrapper.ProductWrapper(product.id, product.name, product.description, product.price, product.status, product.category.id, product.category.name) from Product product")
@NamedQuery(name = "Product.updateProductStatus",
        query = "update Product product set product.status = :status where product.id = :id")
@NamedQuery(name = "Product.getProductByCategory",
        query = "select new com.maid.cafe.c.maidcafec.Wrapper.ProductWrapper(product.id, product.name) from Product product where product.category.id = :id and product.status='true'")
@NamedQuery(name = "Product.getProductById",
        query = "select new com.maid.cafe.c.maidcafec.Wrapper.ProductWrapper(product.id, product.name, product.description, product.price) from Product product where product.id = :id")
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
