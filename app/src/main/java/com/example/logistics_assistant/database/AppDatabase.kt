package com.example.logistics_assistant.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [
        TasksModel::class,
        StatisticsEntity::class,
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): TasksDao
    abstract fun statisticsDao(): StatisticsDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.tasksDao(), database.statisticsDao())
                }
            }
        }

        suspend fun populateDatabase(tasksDao: TasksDao, statisticsDao: StatisticsDao) {
            // Delete all content here.
            tasksDao.deleteAll()

            // Add sample tasks.
            var task = TasksModel(
                taskNum = "Задание № 004",
                price = "30 000,00 ₽",
                dateTask = "11.08.2023",
                timeTask = "12:00",
                status = "Новое",
                placeA = "Машиностроительная улица, 91",
                placeB = "Магистральная улица, 52",
                datePlaceA = "12.08.2023",
                timePlaceA = "12:00",
                datePlaceB = "13.08.2023",
                timePlaceB = "13:00"
            )
            tasksDao.insertTask(task)

            task = TasksModel(
                taskNum = "Задание № 003",
                price = "32 000,00 ₽",
                dateTask = "11.08.2023",
                timeTask = "10:00",
                status = "Новое",
                placeA = "Въезд Космонавтов, 96",
                placeB = "Спуск Косиора, 32",
                datePlaceA = "12.08.2023",
                timePlaceA = "07:00",
                datePlaceB = "13.08.2023",
                timePlaceB = "14:00",
            )
            tasksDao.insertTask(task)

            task = TasksModel(
                taskNum = "Задание № 002",
                price = "20 000,00 ₽",
                dateTask = "11.08.2023",
                timeTask = "09:00",
                status = "Новое",
                placeA = "Проезд Ладыгина, 44",
                placeB = "Проезд Балканская, 20",
                datePlaceA = "12.08.2023",
                timePlaceA = "07:00",
                datePlaceB = "13.08.2023",
                timePlaceB = "14:00",
            )
            tasksDao.insertTask(task)

            task = TasksModel(
                taskNum = "Задание № 001",
                price = "64 000,00 ₽",
                dateTask = "11.08.2023",
                timeTask = "09:00",
                status = "Новое",
                placeA = "Проезд Ладыгина, 44",
                placeB = "Проезд Балканская, 20",
                datePlaceA = "12.08.2023",
                timePlaceA = "07:00",
                datePlaceB = "13.08.2023",
                timePlaceB = "14:00",
            )
            tasksDao.insertTask(task)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, AppDatabase::class.java, "app_database"
                ).addCallback(AppDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

@Entity(tableName = "statistics")
data class StatisticsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val month: String,
    val completedTasks: Int,
)

@Entity(tableName = "tasks")
data class TasksModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskNum: String,
    val price: String,
    val dateTask: String,
    val timeTask: String,
    var status: String,
    val placeA: String,
    val placeB: String,
    val datePlaceA: String,
    val timePlaceA: String,
    val datePlaceB: String,
    val timePlaceB: String,
    var isCompleted: Boolean = false,
)

@Dao
interface TasksDao {
    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<TasksModel>>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: Int): LiveData<TasksModel>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskByIdSync(taskId: Int): TasksModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TasksModel)

    @Update
    suspend fun updateTask(task: TasksModel)

    @Query("DELETE FROM tasks")
    suspend fun deleteAll()
}

@Dao
interface StatisticsDao {
    @Query("SELECT * FROM statistics WHERE month = :month")
    fun getStatisticsForMonth(month: String): LiveData<StatisticsEntity>

    @Query("SELECT * FROM statistics WHERE month = :month")
    suspend fun getStatisticsForMonthSync(month: String): StatisticsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStatistics(statistics: StatisticsEntity)
}