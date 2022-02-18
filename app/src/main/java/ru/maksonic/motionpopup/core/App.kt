package ru.maksonic.motionpopup.core

import android.app.Application
import ru.maksonic.motionpopup.MainScreenViewModel
import ru.maksonic.motionpopup.data.CurrencyRepository

/**
 * @Author: maksonic on 17.02.2022
 */
class App : Application() {

    private var _repository: CurrencyRepository? = null
    private val repository: CurrencyRepository
        get() = requireNotNull(_repository)
    private var _mainScreenVMFactory: MainScreenViewModel.MainScreenViewModelFactory? = null
    val mainScreenVMFactory
        get() = requireNotNull(_mainScreenVMFactory)

    override fun onCreate() {
        super.onCreate()
        _repository = CurrencyRepository()
        _mainScreenVMFactory = MainScreenViewModel.MainScreenViewModelFactory(repository)
    }
}