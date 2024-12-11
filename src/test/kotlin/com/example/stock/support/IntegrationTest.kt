package com.example.stock.support

import com.example.stock.facade.OptimisticLockStockFacade
import com.example.stock.repository.StockRepository
import com.example.stock.service.OptimisticLockStockService
import com.example.stock.service.PessimisticLockStockService
import com.example.stock.service.StockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IntegrationTest {

    @Autowired
    protected lateinit var stockService: StockService

    @Autowired
    protected lateinit var pessimisticLockStockService: PessimisticLockStockService

    @Autowired
    protected lateinit var optimisticLockStockFacade: OptimisticLockStockFacade

    @Autowired
    protected lateinit var stockRepository: StockRepository

    protected var savedStockId: Long = 0L
}
