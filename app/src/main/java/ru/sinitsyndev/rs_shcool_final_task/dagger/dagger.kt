package ru.sinitsyndev.rs_shcool_final_task.dagger

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.sinitsyndev.rs_shcool_final_task.MainActivity
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.GetAssetDetailsUseCase
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.domain.GetAssetPriceHistoryUseCase
import ru.sinitsyndev.rs_shcool_final_task.assetDetailScreen.ui.AssetDetailsFragment
import ru.sinitsyndev.rs_shcool_final_task.data.CoinCapRepositoryImpl
import ru.sinitsyndev.rs_shcool_final_task.data.ICoinCapRepository
import ru.sinitsyndev.rs_shcool_final_task.data.retrofit.CoinCapAPI
import ru.sinitsyndev.rs_shcool_final_task.data.retrofit.CoinCapAPIRetrofitClient
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.domain.GetAssetsListUseCase
import ru.sinitsyndev.rs_shcool_final_task.mainScreen.ui.MainFragment
import ru.sinitsyndev.rs_shcool_final_task.utils.COIN_CAP_API_BASE_URL

@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(assetDetailsFragment: AssetDetailsFragment)
}

@Module(includes = [UseCaseModule::class, AppBindModule::class, DataModule::class, NetworkModule::class])
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
    fun provideGetAssetsListUseCase(repository: CoinCapRepositoryImpl) = GetAssetsListUseCase(repository)

    @Provides
    fun provideGetAssetDetailsUseCase(repository: CoinCapRepositoryImpl) = GetAssetDetailsUseCase(repository)

    @Provides
    fun provideGetAssetPriceHistoryUseCase(repository: CoinCapRepositoryImpl, prefs: SharedPreferences) = GetAssetPriceHistoryUseCase(repository, prefs)
}

@Module
class DataModule {
    @Provides
    fun provideCoinCapRepositoryImpl(client: CoinCapAPIRetrofitClient) = CoinCapRepositoryImpl(client)

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
}

@Module
class NetworkModule {

    @Provides
    fun provideCoinCapAPI(): CoinCapAPI {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(COIN_CAP_API_BASE_URL)
            .build()
        return retrofit.create(CoinCapAPI::class.java)
    }
}
