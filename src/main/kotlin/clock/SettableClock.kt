package clock

import java.time.Instant

class SettableClock(var clock: Instant) : Clock {
    override fun now(): Instant = clock
}