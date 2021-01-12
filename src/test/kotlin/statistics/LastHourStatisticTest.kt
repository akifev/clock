package statistics

import clock.RealClock
import clock.SettableClock
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.time.Duration
import java.time.Instant

internal class LastHourStatisticTest {
    @Test
    fun testEventsInOneHour() {
        val clock = RealClock()
        val statistic = LastHourStatistic(clock)

        statistic.incEvent("event#1")
        assertEquals(1.0 / 60, statistic.getAllEventStatistic()["event#1"])

        statistic.incEvent("event#1")
        assertEquals(2.0 / 60, statistic.getAllEventStatistic()["event#1"])

        statistic.incEvent("event#2")
        assertEquals(2.0 / 60, statistic.getAllEventStatistic()["event#1"])
        assertEquals(1.0 / 60, statistic.getAllEventStatistic()["event#2"])
    }

    @Test
    fun testEventsInDifferentHours() {
        val clock = SettableClock(Instant.now())
        val statistic = LastHourStatistic(clock)

        statistic.incEvent("event#1")
        assertEquals(1.0 / 60, statistic.getAllEventStatistic()["event#1"])

        clock.clock += Duration.ofMinutes(59)
        statistic.incEvent("event#1")
        assertEquals(2.0 / 60, statistic.getAllEventStatistic()["event#1"])
        clock.clock += Duration.ofMinutes(1)
        assertEquals(1.0 / 60, statistic.getAllEventStatistic()["event#1"])
        clock.clock += Duration.ofMinutes(58)
        assertEquals(1.0 / 60, statistic.getAllEventStatistic()["event#1"])
        clock.clock += Duration.ofMinutes(1)
        assertEquals(0.0, statistic.getAllEventStatistic()["event#1"])
    }
}