package com.redis_cache.redis_sentinel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Builder
@Entity
@Table(name="ice_cream")
public class IceCream {

    @Id
    @Column(name="topic_id")
    private String id;

    @Column(name="ice_cream_name")
    String iceCreamName;

    @Column(name="ice_cream_price")
    Double iceCreamPrice;

    @Column(name="ice_cream_flavor")
    String iceCreamFlavor;

    public IceCream(String id, String iceCreamName, Double iceCreamPrice, String iceCreamFlavor) {
        this.id = id;
        this.iceCreamName = iceCreamName;
        this.iceCreamPrice = iceCreamPrice;
        this.iceCreamFlavor = iceCreamFlavor;
    }

    public IceCream() {
    }
}
