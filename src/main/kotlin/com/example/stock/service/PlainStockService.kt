package com.example.stock.service

import com.example.stock.repository.StockRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class PlainStockService(
    private val stockRepository: StockRepository,
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun decrease(id: Long, quantity: Long) {
        // stock 조회
        val stock = stockRepository.findByIdOrNull(id) ?: throw NoSuchElementException("Stock with id $id not found")

        // 재고 감소
        stock.decrease(quantity)

        // 갱신된 값을 저장
        stockRepository.saveAndFlush(stock)
    }
}
