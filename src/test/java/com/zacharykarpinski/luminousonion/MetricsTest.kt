package com.zacharykarpinski.luminousonion

import com.zacharykarpinski.luminousonion.repository.FindingRepository
import com.zacharykarpinski.luminousonion.repository.SourceRepository
import com.zacharykarpinski.luminousonion.service.MetricService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
open class MetricsTest {
    @Autowired
    var findingRepository: FindingRepository? = null

    @Test
    fun testCreateNewFinding() {
        val metricService = MetricService(findingRepository!!)
        val s = metricService.getDashboardMetrics(1)
    }
}
