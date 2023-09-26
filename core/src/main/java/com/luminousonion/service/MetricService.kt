package com.luminousonion.service

import com.luminousonion.model.Finding
import com.luminousonion.repository.FindingRepository
import org.jetbrains.kotlinx.dataframe.api.*
import org.springframework.stereotype.Service

@Service
class MetricService( private val findingRepository: FindingRepository) {

    fun getDashboardMetrics(id: Long): String {
        val x: List<Finding> = findingRepository.findBySource_Product_IdAndSource_ArchivedFalse(id)
        val df = x.toDataFrame()
        println(df.toString())


        return "test"
    }

}
