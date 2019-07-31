package ir.hossainco.utils

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AssetDatabaseHelper(private val context: Context) : SQLiteOpenHelper(
	context,
	DATABASE_NAME,
	null,
	DATABASE_VERSION
) {
	private val localDbFile = context.getDatabasePath(DATABASE_NAME)

	override fun onCreate(db: SQLiteDatabase?) = Unit

	override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit

	override fun getWritableDatabase(): SQLiteDatabase {
		installDatabaseIfNeeded()
		return super.getWritableDatabase()
	}

	override fun getReadableDatabase(): SQLiteDatabase {
		installDatabaseIfNeeded()
		return super.getReadableDatabase()
	}


	private fun installDatabaseIfNeeded() {
		if (!localDbFile.exists()) {
			installDatabaseFromAssets()
		}
	}

	private fun installDatabaseFromAssets() {
		try {
			context.assets.open("$ASSETS_PATH/$DATABASE_NAME").use { inputStream ->
				localDbFile.outputStream().use { outputStream ->
					inputStream.copyTo(outputStream)
					outputStream.flush()
				}
			}
		} catch (exception: Throwable) {
			throw RuntimeException("The $DATABASE_NAME database couldn't be installed.", exception)
		}
	}

	init {
		instance = this
	}

	companion object {
		const val ASSETS_PATH = "databases"
		const val DATABASE_NAME = "database.db"
		const val DATABASE_VERSION = 1

		@SuppressLint("StaticFieldLeak")
		private var instance: AssetDatabaseHelper? = null

		@Synchronized
		fun getInstance(context: Context) = instance
			?: AssetDatabaseHelper(context.applicationContext)
	}
}
