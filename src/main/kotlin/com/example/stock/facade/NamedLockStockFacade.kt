package com.example.stock.facade

import com.example.stock.repository.LockRepository
import com.example.stock.service.PlainStockService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class NamedLockStockFacade(
    private val lockRepository: LockRepository,
    private val plainStockService: PlainStockService
) {

    @Transactional
    fun decrease(id: Long, quantity: Long) {
        try {
            lockRepository.getLock(id.toString())
            plainStockService.decrease(id, quantity)
        } finally {
            lockRepository.releaseLock(id.toString())
        }
    }
}
