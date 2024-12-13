package com.example.stock.facade

import com.example.stock.service.PlainStockService
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedissonLockStockFacade(
    private val redissonClient: RedissonClient,
    private val plainStockService: PlainStockService,
) {

    fun decrease(id: Long, quantity: Long) {
        val lock = redissonClient.getLock(id.toString())

        try {
            val available = lock.tryLock(
                15, //  몇 초동안 락 획득 시도?
                1, // 몇초동안 락 점유?
                TimeUnit.SECONDS
            )
            if (!available) {
                println("lock 획득 실패")
                return
            }
            plainStockService.decrease(id, quantity)
        } finally {
            lock.unlock()
        }
    }
}
