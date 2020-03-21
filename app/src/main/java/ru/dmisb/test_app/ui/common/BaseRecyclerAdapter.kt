package ru.dmisb.test_app.ui.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseRecyclerAdapter <T>(
    val layout: Int = 0,
    var items: List<T>,
    private val holderFactory: ((view: View) -> BaseViewHolder<T>),
    private val onItemClick: ((item: T) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    open fun obtainLayout(viewType: Int): Int {
        return layout
    }

    override fun getItemCount() = items.size

    private fun inflateItemView(parent: ViewGroup?, viewType: Int): View {
        val inflater = LayoutInflater.from(parent?.context)
        return inflater.inflate(obtainLayout(viewType), parent, false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BaseViewHolder<T> {
        return holderFactory.invoke(inflateItemView(parent, viewType))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = items[position]
        holder.bindView(item, onItemClick)
    }
}