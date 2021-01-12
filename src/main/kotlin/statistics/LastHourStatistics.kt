package statistics

import clock.Clock
import java.time.Duration
import java.time.Instant

class LastHourStatistic(private val clock: Clock) : EventsStatistic {
    private val startTime = clock.now()
    private val statistic = mutableMapOf<String, MutableList<Instant>>()

    override fun incEvent(eventName: String) {
        statistic.putIfAbsent(eventName, mutableListOf())
        statistic[eventName]!! += clock.now()
    }

    override fun getEventStatisticByName(eventName: String): Double =
        statistic[eventName]?.filter { inLastHour(it) }?.size?.toDouble()?.div(60) ?: 0.0

    override fun getAllEventStatistic(): Map<String, Double> =
        statistic.mapValues { it.value.filter { inLastHour(it) }.size.toDouble() / 60 }

    override fun printStatistic() {
        val hours = (clock.now().epochSecond - startTime.epochSecond).toDouble()

        println("-------------------------------------")
        System.out.printf("%20s %15s%n", "EVENT", "RPM")
        println("-------------------------------------")

        statistic.forEach { (eventName, eventTimes) ->
            val rpm = eventTimes.size.toDouble() / hours

            System.out.printf("%20s %15s %n", eventName, rpm)
        }
    }

    private fun inLastHour(time: Instant): Boolean {
        val now = clock.now()
        val hour = Duration.ofHours(1)

        return (now - hour < time && time <= now)
    }
}