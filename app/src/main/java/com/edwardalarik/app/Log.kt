package com.edwardalarik.app

class Log {
    companion object {
        fun d(tag: String, data: String) {
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, "$tag $data")
            }
        }

        fun d(tag: String, data: String, e: Exception? = null) {
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, "$tag $data")
                e?.printStackTrace()
            }
        }

        fun d(tag: String, data: String, e: Throwable? = null) {
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, "$tag $data")
                e?.printStackTrace()
            }
        }

        fun i(tag: String, data: String, e: Exception? = null) {
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, "$tag $data")
                e?.printStackTrace()
            }
        }

        fun e(tag: String, data: String, e: Exception? = null) {
            if (BuildConfig.DEBUG) {
                android.util.Log.d(tag, "$tag $data")
                e?.printStackTrace()
            }
        }
    }
}