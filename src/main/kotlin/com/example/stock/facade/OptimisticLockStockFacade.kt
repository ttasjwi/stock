package com.example.stock.facade

import com.example.stock.service.OptimisticLockStockService
import org.springframework.orm.ObjectOptimisticLockingFailureException
import org.springframework.stereotype.Component

@Component
class OptimisticLockStockFacade(
    private val optimisticLockStockService: OptimisticLockStockService
) {

    fun decrease(id: Long, quantity: Long) {
        while(true) {
            try {
                optimisticLockStockService.decrease(id, quantity)
                break
            } catch (e: ObjectOptimisticLockingFailureException) {
                Thread.sleep(50)
            }
        }
    }
}
