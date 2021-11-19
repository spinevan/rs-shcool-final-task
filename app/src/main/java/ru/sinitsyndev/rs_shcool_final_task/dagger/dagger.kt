package ru.sinitsyndev.rs_shcool_final_task.dagger

import android.app.Application
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.sinitsyndev.rs_shcool_final_task.MainActivity
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.MainFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(mainFragment: MainFragment)
}

@Module
class AppModule(private val application: Application) {
    @Provides
    @Singleton
    fun providesApplication(): Application = application
}