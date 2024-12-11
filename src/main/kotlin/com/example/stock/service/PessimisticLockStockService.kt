package com.example.stock.service

import com.example.stock.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PessimisticLockStockService(
    private val stockRepository: StockRepository
) {

    @Transactional
    fun decrease(id: Long, quantity: Long) {
        // stock 조회
        val stock = stockRepository.findByIdWithPessimisticLock(id)
            ?: throw NoSuchElementException("Stock with id $id not found")

        // 재고 감소
        stock.decrease(quantity)

        // 갱신된 값을 저장
        stockRepository.saveAndFlush(stock)
    }
}
