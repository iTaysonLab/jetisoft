package bruhcollective.itaysonlab.jetisoft

import android.app.Application
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JetisoftApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(applicationContext)
    }
}