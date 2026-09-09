package com.iftikar.memonto.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.PreferencesFileSerializer

fun createDataStore(
    context: Context
): DataStore<Preferences> {
    return DataStoreFactory.create(
        storage = FileStorage<Preferences>(
            serializer = PreferencesFileSerializer,
            produceFile = {
                context.filesDir.resolve(
                    "memonto.preferences_pb"
                )
            }
        )
    )
}