package ru.maksonic.motionpopup.data

import ru.maksonic.motionpopup.domain.Currency
import ru.maksonic.motionpopup.domain.Repository

/**
 * @Author: maksonic on 17.02.2022
 */
class CurrencyRepository : Repository {
    override fun fetchCurrencyList(): List<Currency> =
        listOf(
            Currency("RUB", "₽"), Currency("USD", "$"),
            Currency("EUR", "€"), Currency("GBP", "£"),
            Currency("GBP", "£"), Currency("CHF", "₣"),
            Currency("CNY", "¥"), Currency("KRW", "₩"),
            Currency("IDR", "Rp"), Currency("INR", "₹"),
            Currency("JPY", "¥"),
        )
}