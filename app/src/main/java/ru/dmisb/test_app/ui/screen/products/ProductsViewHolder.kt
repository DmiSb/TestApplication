package ru.dmisb.test_app.ui.screen.products

import android.view.View
import kotlinx.android.synthetic.main.item_product.view.*
import ru.dmisb.test_app.data.Product
import ru.dmisb.test_app.ui.common.BaseViewHolder
import ru.dmisb.test_app.utils.toCurrencyText

class ProductsViewHolder(itemView: View) : BaseViewHolder<Product>(itemView) {

    override fun bindView(data: Product, onItemClick: ((item: Product) -> Unit)?) = with(itemView) {
        productNameView.text = data.name
        productPriceView.text = data.price.toCurrencyText()
        setOnClickListener { onItemClick?.invoke(data) }
    }
}