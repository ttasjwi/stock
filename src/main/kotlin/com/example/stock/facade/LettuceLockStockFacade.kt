package com.example.stock.facade

import com.example.stock.repository.RedisLockRepository
import com.example.stock.service.PlainStockService
import org.springframework.stereotype.Component

@Component
class LettuceLockStockFacade(
    private val redisLockRepository: RedisLockRepository,
    private val plainStockService: PlainStockService,
) {

    fun decrease(id: Long, quantity: Long) {
        while((redisLockRepository.lock(id)) != true) {
            Thread.sleep(100)
        }

        try {
            plainStockService.decrease(id, quantity)
        } finally {
            redisLockRepository.unlock(id)
        }
    }
}
