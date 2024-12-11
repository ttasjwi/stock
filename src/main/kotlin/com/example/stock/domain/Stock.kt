package com.example.stock.domain

import jakarta.persistence.*

@Entity
@Table(name = "stocks")
class Stock(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    var id: Long? = null,

    @Column(name = "product_id")
    var productId: Long,

    @Column(name = "quantity")
    var quantity: Long,

    @Version
    var version: Long,
) {

    companion object {
        fun create(productId: Long, quantity: Long): Stock {
            return Stock(null, productId, quantity, 0)
        }
    }

    fun decrease(quantity: Long) {
        if (this.quantity - quantity < 0) {
            throw RuntimeException("재고 부족")
        }
        this.quantity -= quantity
    }
}
