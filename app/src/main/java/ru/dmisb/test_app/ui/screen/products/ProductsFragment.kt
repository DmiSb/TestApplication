package ru.dmisb.test_app.ui.screen.products

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_products.*
import ru.dmisb.test_app.R
import ru.dmisb.test_app.data.Product
import ru.dmisb.test_app.ui.common.BaseFragment
import ru.dmisb.test_app.ui.common.BaseRecyclerAdapter
import ru.dmisb.test_app.ui.common.bundle

class ProductsFragment : BaseFragment<ProductsViewModel>() {

    override val layoutResId = R.layout.fragment_products

    private lateinit var productAdapter: BaseRecyclerAdapter<Product>

    private var animShake: Animation? = null

    override fun initViewModel(): ProductsViewModel {
        return provideViewModel(ProductsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animShake = AnimationUtils.loadAnimation(context, R.anim.shake)

        productAdapter = BaseRecyclerAdapter(
            layout = R.layout.item_product,
            items = listOf(),
            holderFactory = { ProductsViewHolder(it) },
            onItemClick = {
                findNavController().navigate(R.id.listFragment_to_detailFragment, it.bundle)
            }
        )
        productsItemsView.adapter = productAdapter

        productsAddButton.setOnClickListener {
            findNavController().navigate(R.id.listFragment_to_detailFragment)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.products.observe(viewLifecycleOwner, Observer {
            productAdapter.items = it.orEmpty()
            productAdapter.notifyDataSetChanged()

            val isEmpty = it.isNullOrEmpty()
            productsEmptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE

            if (isEmpty) viewModel.startAnimation()
        })

        viewModel.animButton.observe(viewLifecycleOwner, Observer {
            if (it == true && animShake != null) {
                productsAddButton.startAnimation(animShake)
            }
        })
    }
}