/*
 * (C) Copyright IBM Deutschland GmbH 2021
 * (C) Copyright IBM Corp. 2021
 */

package de.rki.covpass.sdk.httplib.util

public data class EtaggedData<T>(val data: T, val etag: String)
