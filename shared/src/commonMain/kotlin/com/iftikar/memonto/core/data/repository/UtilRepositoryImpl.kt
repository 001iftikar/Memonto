package com.iftikar.memonto.core.data.repository

import androidx.sqlite.SQLiteException
import com.iftikar.memonto.core.domain.repository.UtilRepository
import com.iftikar.memonto.core.local.dao.UserDao
import com.iftikar.memonto.core.local.model.UserEntity
import com.iftikar.memonto.core.local.model.asExternalModel
import com.iftikar.memonto.core.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UtilRepositoryImpl(
    private val userDao: UserDao
) : UtilRepository {
    override suspend fun saveUser(name: String) = withContext(Dispatchers.IO) {
        try {
            userDao.saveUser(
                UserEntity(0, name)
            )
        } catch (ex: SQLiteException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun getUser(): Flow<User?> {
        return userDao.getUser(0).map {
            it?.asExternalModel()
        }.catch {
            it.printStackTrace()
        }
            .flowOn(Dispatchers.IO)
    }
}

















