package com.zacharykarpinski.luminousonion.service

import com.zacharykarpinski.luminousonion.model.Finding
import com.zacharykarpinski.luminousonion.repository.FindingRepository
import org.jetbrains.kotlinx.dataframe.DataFrame
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