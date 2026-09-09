package com.iftikar.memonto.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer
import java.io.File

fun createDataStore(): DataStore<Preferences> {
    return DataStoreFactory.create(
        storage = FileStorage<Preferences>(
            serializer = PreferencesFileSerializer,
            produceFile = {
                File(
                    System.getProperty("java.io.tmpdir"),
                    "memonto.preferences_pb"
                )
            }
        )
    )
}