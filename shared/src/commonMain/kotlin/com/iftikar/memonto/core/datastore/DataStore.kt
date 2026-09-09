package com.iftikar.memonto.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Storage
import androidx.datastore.preferences.core.Preferences

private const val DATASTORE_FILE_NAME = "memonto.preferences_pb"
fun createDataStore(
    storage: Storage<Preferences>
): DataStore<Preferences> {
    return DataStoreFactory.create(
        storage = storage
    )
}