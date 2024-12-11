package com.example.stock.service

import com.example.stock.domain.Stock
import com.example.stock.repository.StockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
class StockServiceTest {

    @Autowired
    private lateinit var stockService: StockService

    @Autowired
    private lateinit var stockRepository: StockRepository

    @BeforeEach
    fun setup() {
        stockRepository.saveAndFlush(Stock.create(1L, 100))
    }


    @Test
    @DisplayName("재고 수량을 1 감소시키고 조회하면 감소된 양의 Stock이 조회된다")
    fun decreaseTest() {
        // given
        // when
        stockService.decrease(1L, 1L)

        // then
        val findStock = stockRepository.findByIdOrNull(1L)!!
        assertThat(findStock.quantity).isEqualTo(99L)
    }
}
