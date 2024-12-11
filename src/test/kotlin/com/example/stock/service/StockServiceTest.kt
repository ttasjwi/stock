package com.example.stock.service

import com.example.stock.domain.Stock
import com.example.stock.repository.StockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class StockServiceTest {

    @Autowired
    private lateinit var stockService: StockService

    @Autowired
    private lateinit var stockRepository: StockRepository

    private var savedStockId: Long = 0L

    @BeforeEach
    fun setup() {
        savedStockId = stockRepository.saveAndFlush(Stock.create(1L, 100)).id!!
    }

    @AfterEach
    fun tearDown() {
        stockRepository.deleteById(savedStockId)
    }

    @Test
    @DisplayName("재고 수량을 1 감소시키고 조회하면 감소된 양의 Stock이 조회된다")
    fun decreaseTest() {
        // given
        // when
        stockService.decrease(savedStockId, 1L)

        // then
        val findStock = stockRepository.findByIdOrNull(savedStockId)!!
        assertThat(findStock.quantity).isEqualTo(99L)
    }


    @Test
    @DisplayName("동시 100개 요청(성공)")
    fun decreaseWithMultiThread() {
        // given
        val savedStockId = stockRepository.saveAndFlush(Stock.create(1L, 100)).id!!

        val threadCount = 100
        val executorsService = Executors.newFixedThreadPool(threadCount)
        val countDownLatch = CountDownLatch(threadCount)

        // when
        for (i in 1..threadCount) {
            executorsService.submit {
                try {
                    stockService.decrease(savedStockId, 1L)
                } finally {
                    countDownLatch.countDown()
                }
            }
        }
        countDownLatch.await()

        val findStock = stockRepository.findByIdOrNull(savedStockId)!!
        assertThat(findStock.quantity).isEqualTo(0L)
    }
}
