import clock.RealClock
import statistics.LastHourStatistic

fun main(args: Array<String>) {
    val clock = RealClock()
    val statistic = LastHourStatistic(clock)

    statistic.incEvent("event#1")
    statistic.incEvent("event#1")
    statistic.incEvent("event#2")

    Thread.sleep(1000)

    statistic.printStatistic()
}