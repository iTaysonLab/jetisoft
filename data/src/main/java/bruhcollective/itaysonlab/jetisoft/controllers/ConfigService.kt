package bruhcollective.itaysonlab.jetisoft.controllers

import android.content.Context
import com.squareup.moshi.Moshi
import com.tencent.mmkv.MMKV
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KProperty

@Singleton
class ConfigService @Inject constructor(
    @ApplicationContext applicationContext: Context,
    private val moshi: Moshi
) {
    private val instance: MMKV

    init {
        MMKV.initialize(applicationContext)
        instance = MMKV.defaultMMKV()
    }

    fun has(what: String) = instance.containsKey(what)

    fun string(of: String, def: String) = instance.getString(of, def)!!
    fun boolean(of: String, def: Boolean) = instance.getBoolean(of, def)
    fun int(of: String, def: Int) = instance.getInt(of, def)
    fun long(of: String, def: Long) = instance.getLong(of, def)
    fun bytes(of: String, def: ByteArray) = instance.getBytes(of, def)!!

    fun put(to: String, what: Any) {
        when (what) {
            is String -> instance.putString(to, what)
            is Int -> instance.putInt(to, what)
            is Long -> instance.putLong(to, what)
            is Boolean -> instance.putBoolean(to, what)
            is ByteArray -> instance.putBytes(to, what)
            else -> error("Not supported type")
        }
    }

    // Abstracts

    private inner class StringCfg (private val key: String, private val default: String) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = instance.getString(key, default) ?: default
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) { instance.putString(key, value) }
    }

    private inner class LongCfg (private val key: String, private val default: Long) {
        operator fun getValue(thisRef: Any?, property: KProperty<*>) = instance.getLong(key, default)
        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) { instance.putLong(key, value) }
    }

    // experimental json delegating
    inner class JsonCfg <T> (private val key: String, type: Class<T>) {
        // JSON parsing isn't cheap, so we cache it
        private var _jsonValue: T? = null
        private val jsonAdapter = moshi.adapter(type)

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
            if (_jsonValue == null) {
                if (instance.containsKey(key)) {
                    val str = instance.getString(key, null)
                    if (str.isNullOrEmpty()) return null
                    _jsonValue = jsonAdapter.fromJson(str)
                } else {
                    return null
                }
            }

            return _jsonValue!!
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            _jsonValue = if (value != null) {
                instance.putString(key, jsonAdapter.toJson(value))
                value
            } else {
                instance.removeValueForKey(key)
                null
            }
        }
    }

    inline fun <reified T> jsonCfg(key: String) = JsonCfg(key, T::class.java)
}