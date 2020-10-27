package com.livehappyapps.githubviewer.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.livehappyapps.githubviewer.data.local.dao.IssueDao
import com.livehappyapps.githubviewer.data.local.dao.OrganizationDao
import com.livehappyapps.githubviewer.data.local.dao.RepoDao
import com.livehappyapps.githubviewer.data.local.util.Converters
import com.livehappyapps.githubviewer.model.Issue
import com.livehappyapps.githubviewer.model.Organization
import com.livehappyapps.githubviewer.model.Repo


private const val DATABASE_NAME = "github_database"

@Database(
    entities = [Repo::class, Organization::class, Issue::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GithubDatabase : RoomDatabase() {
    abstract val repoDao: RepoDao
    abstract val organizationDao: OrganizationDao
    abstract val issueDao: IssueDao

    companion object {
        private lateinit var INSTANCE: GithubDatabase

        fun getDatabase(context: Context): GithubDatabase {
            synchronized(GithubDatabase::class.java) {
                if (!Companion::INSTANCE.isInitialized) {
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
