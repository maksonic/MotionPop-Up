package ru.maksonic.motionpopup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.maksonic.motionpopup.data.CurrencyRepository
import ru.maksonic.motionpopup.domain.Currency

/**
 * @Author: maksonic on 17.02.2022
 */
class MainScreenViewModel(repository: CurrencyRepository) : ViewModel() {

    private val _currencyList = MutableStateFlow<List<Currency>>(emptyList())
    val currencyList: StateFlow<List<Currency>>
        get() = _currencyList

    init {
        _currencyList.value = repository.fetchCurrencyList()
    }

    @Suppress("UNCHECKED_CAST")
    class MainScreenViewModelFactory(private val repository: CurrencyRepository) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainScreenViewModel(repository) as T
    }
}