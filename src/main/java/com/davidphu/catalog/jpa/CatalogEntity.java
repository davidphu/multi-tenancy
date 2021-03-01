package com.davidphu.catalog.jpa;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "catalog")
@Entity
public class CatalogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String itemName;

    private String itemDescription;

    private Float salePrice;

    private long availableQuantity;

    private Date listingDate;

    private Date listingEndDate;
}
