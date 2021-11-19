package ru.sinitsyndev.rs_shcool_final_task.dagger

import android.app.Application
import android.content.Context
import dagger.*
import ru.sinitsyndev.rs_shcool_final_task.MainActivity
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.data.ICoinCapRepository
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.GetAssetsListUseCase
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui.MainFragment
import javax.inject.Singleton

@Component(modules = [AppModule::class])
interface AppComponent {

 //   fun application(): Application

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

   // fun inject(activity: MainActivity)
   fun inject(mainFragment: MainFragment)
}

@Module(includes = [UseCaseModule::class, AppBindModule::class, DataModule::class])
class AppModule

@Module
interface AppBindModule {

    @Suppress("FunctionName")
    @Binds
    fun bindCoinCapRepositoryImpl_to_ICoinCapRepository(
        coinCapRepositoryImpl: CoinCapRepositoryImpl
    ): ICoinCapRepository
}

@Module
class UseCaseModule {
    @Provides
    fun provideGetAssetsListUseCase() = GetAssetsListUseCase(CoinCapRepositoryImpl())
}

@Module
class DataModule {
    @Provides
    fun provideCoinCapRepositoryImpl() = CoinCapRepositoryImpl()
}