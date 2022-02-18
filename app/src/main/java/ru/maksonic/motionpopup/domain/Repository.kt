package ru.maksonic.motionpopup.domain

/**
 * @Author: maksonic on 17.02.2022
 */
interface Repository {
    fun fetchCurrencyList(): List<Currency>
}