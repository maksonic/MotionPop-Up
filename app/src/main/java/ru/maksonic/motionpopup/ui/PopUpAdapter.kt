package ru.maksonic.motionpopup.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.maksonic.motionpopup.domain.Currency
import ru.maksonic.motionpopup.databinding.ItemCurrencyPopupBinding

/**
 * @Author: maksonic on 16.02.2022
 */
class PopUpAdapter(
    private val onClick: (Currency) -> Unit
) : ListAdapter<Currency, PopUpAdapter.CurrencyViewHolder>(CurrencyItemDiffUtil()) {

    inner class CurrencyViewHolder(
        private val binding: ItemCurrencyPopupBinding,
        private val onClick: (Currency) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.popUpItem.setOnClickListener {
                onClick.invoke(getItem(adapterPosition))
            }
        }

        fun bind(currency: Currency) {
            binding.currencyAbbreviation.text = currency.name
            binding.currencySymbol.text = currency.symbol
        }
    }

    class CurrencyItemDiffUtil : DiffUtil.ItemCallback<Currency>() {
        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(
            ItemCurrencyPopupBinding
                .inflate(LayoutInflater.from(parent.context), parent, false),
            onClick = onClick
        )

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}