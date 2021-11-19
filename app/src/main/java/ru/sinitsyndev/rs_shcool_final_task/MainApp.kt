package ru.sinitsyndev.rs_shcool_final_task

import android.app.Application
import android.content.Context
import ru.sinitsyndev.rs_shcool_final_task.dagger.AppComponent
import ru.sinitsyndev.rs_shcool_final_task.dagger.AppModule
import ru.sinitsyndev.rs_shcool_final_task.dagger.DaggerAppComponent

//import ru.sinitsyndev.rs_shcool_final_task.dagger.DaggerAppComponent

class MainApp : Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(context = this)
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> applicationContext.appComponent
    }
