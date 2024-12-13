package com.example.stock.repository

import com.example.stock.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LockRepository : JpaRepository<Stock, Long> {

    @Query("SELECT get_lock(:key, 3000)", nativeQuery = true)
    fun getLock(@Param("key") key: String)

    @Query("SELECT release_lock(:key)", nativeQuery = true)
    fun releaseLock(@Param("key") key: String)
}
