package com.livehappyapps.githubviewer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.livehappyapps.githubviewer.model.Organization
import com.livehappyapps.githubviewer.model.Repo


private const val DATABASE_NAME = "github_database"

@Database(entities = [Repo::class, Organization::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GithubDatabase : RoomDatabase() {
    abstract val repoDao: RepoDao
    abstract val organizationDao: OrganizationDao

    companion object {
        private lateinit var INSTANCE: GithubDatabase

        fun getDatabase(context: Context): GithubDatabase {
            synchronized(GithubDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
