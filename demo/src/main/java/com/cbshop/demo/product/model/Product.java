package com.cbshop.demo.product.model;

import com.cbshop.demo.enums.Category;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String description;
    private Integer price;
    private Category category;
    @CreatedDate
    private Date createDate;
    @LastModifiedDate
    private Date lastUpdateDate;
}
