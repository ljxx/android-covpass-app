/*
 * (C) Copyright IBM Deutschland GmbH 2021
 * (C) Copyright IBM Corp. 2021
 */

package de.rki.covpass.sdk.utils

import de.rki.covpass.sdk.dependencies.sdkDeps
import java.time.Duration
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * @return True, if the [ZonedDateTime] is older than given [hours], else false.
 */
public fun ZonedDateTime.isOlderThan(hours: Long): Boolean {
    return this.plusHours(hours).isBefore(ZonedDateTime.now(sdkDeps.clock))
}

/**
 * @return The duration in hours between this [ZonedDateTime] and now.
 */
public fun ZonedDateTime.hoursTillNow(): Int {
    return Duration.between(this, ZonedDateTime.now(sdkDeps.clock)).toHours().toInt()
}

/**
 * Returns "+00:00" in case of "Z", else just returns [toString].
 */
public fun ZoneOffset.getDisplayString(): String {
    return when (this.toString()) {
        "Z" -> "+00:00"
        else -> this.toString()
    }
}

/**
 * Formats a [ZonedDateTime] to e.g. "1989-03-28, 14:52".
 */
public fun ZonedDateTime.formatDateTimeInternational(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm")
    return format(formatter)
}

/**
 * Formats a [ZonedDateTime] to e.g. "12.03.1989, 14:52".
 */
public fun ZonedDateTime.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT)
    return format(formatter)
}

/**
 * Converts a [ZonedDateTime] to the default system timezone.
 */
public fun ZonedDateTime.toDeviceTimeZone(): ZonedDateTime {
    return withZoneSameInstant(ZoneId.systemDefault())
}
