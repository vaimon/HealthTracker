package me.vaimon.healthtracker.data.datasource.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migration1to2 : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.beginTransaction()
        try {
            db.execSQL("DELETE FROM routePoints WHERE trainingId in (SELECT id FROM trainings WHERE startTime IS NULL)")
            db.delete("trainings", "startTime = NULL", null)

            db.execSQL("CREATE TABLE temp_trainings AS SELECT * FROM trainings")
            db.execSQL("DROP TABLE trainings")
            db.execSQL("CREATE TABLE trainings (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, startTime INTEGER NOT NULL DEFAULT 0, endTime INTEGER)")
            db.execSQL("INSERT INTO trainings (startTime, endTime) SELECT startTime, endTime FROM temp_trainings")
            db.execSQL("DROP TABLE temp_trainings")
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }
}