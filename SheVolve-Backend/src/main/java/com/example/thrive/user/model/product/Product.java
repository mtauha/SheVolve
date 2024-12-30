package com.example.thrive.user.model.product;

import com.example.thrive.user.model.BaseEntity;
import com.example.thrive.user.model.EntrepreneurModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SuperBuilder
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @NotNull(message = "name can not be null")
    @Column(unique = true)
    private String productName;

    @NotNull(message = "description can not be null")
    @Column(unique = true)
    private String productDescription;

    @NotNull(message = "price can not be null")
    @Positive(message = "price must be greater than zero")
    private int productPrice;

    @NotNull(message = "you must enter product image")
    @Lob
    private byte[] productImage;

    @NotNull(message = "owner can not be null")
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private EntrepreneurModel productOwner;

}