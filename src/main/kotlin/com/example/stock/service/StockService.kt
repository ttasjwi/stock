package com.example.stock.service

import com.example.stock.repository.StockRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockRepository: StockRepository,
) {

    // @Transactional
    // Transactional 과 @Synchronized 를 같이 쓰면?
    // 트랜잭션 시작 - 실제 decrease 메서드 호출(여기에 동기화 걸림) - 트랜잭션 종료
    // 트랜잭션 종료를 대기하는 사이에 다른 요청에 의해 decrease 실제 메서드가 호출되는 문제가 발생하게 됨
    @Synchronized
    fun decrease(id: Long, quantity: Long) {
        // stock 조회
        val stock = stockRepository.findByIdOrNull(id) ?: throw NoSuchElementException("Stock with id $id not found")

        // 재고 감소
        stock.decrease(quantity)

        // 갱신된 값을 저장
        stockRepository.saveAndFlush(stock)
    }
}
