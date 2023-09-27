package com.luminousonion

import com.luminousonion.repository.FindingRepository
import com.luminousonion.service.MetricService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest(showSql = false)
open class MetricsTest {
    @Autowired
    var findingRepository: FindingRepository? = null

    @Test
    fun testCreateNewFinding() {
        val metricService = MetricService(findingRepository!!)
        val s = metricService.getDashboardMetrics(1)
    }
}
