package ru.dmisb.test_app.ui.screen.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_detail.*
import ru.dmisb.test_app.R
import ru.dmisb.test_app.data.Product
import ru.dmisb.test_app.ui.common.BaseFragment
import ru.dmisb.test_app.ui.common.defaultArg
import ru.dmisb.test_app.utils.SimpleTextWatcher
import ru.dmisb.test_app.utils.alert
import ru.dmisb.test_app.utils.enabled
import ru.dmisb.test_app.utils.setIsValid

class DetailFragment : BaseFragment<DetailViewModel>() {

    override val layoutResId = R.layout.fragment_detail
    override val showBackButton = true

    override fun initViewModel(): DetailViewModel {
        val product = defaultArg<Product?>().value
        return provideViewModel(DetailViewModel.factory(product), DetailViewModel::class.java)
    }

    private lateinit var nameListener: SimpleTextWatcher
    private lateinit var priceListener: SimpleTextWatcher

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        detailToolbar.title = if (viewModel.product == null) getString(R.string.detail_title_add)
        else viewModel.product?.name.orEmpty()

        nameListener = SimpleTextWatcher { viewModel.productName = it }
        priceListener = SimpleTextWatcher { viewModel.productPrice = it }

        detailNameView.setText(viewModel.productName)
        detailPriceView.setText(viewModel.productPrice)

        detailSaveButton.text = getString(
            if (viewModel.product == null) R.string.detail_add else R.string.detail_save
        )
        detailSaveButton.setOnClickListener {
            viewModel.save()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.nameValid.observe(viewLifecycleOwner, Observer {
            detailNameLayout.setIsValid(it)
        })

        viewModel.priceValid.observe(viewLifecycleOwner, Observer {
            detailPriceLayout.setIsValid(it)
        })

        viewModel.modelValid.observe(viewLifecycleOwner, Observer {
            detailSaveButton.enabled(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DetailState.OnSuccessSaved -> findNavController().popBackStack()
                is DetailState.OnSuccessDelete -> findNavController().popBackStack()
                is DetailState.OnError -> context.alert(getString(R.string.error_title), it.error)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            R.id.menu_item_delete -> {
                context.alert(
                    title ="",
                    message = getString(R.string.detail_delete_confirm, viewModel.product?.name),
                    positive = { viewModel.delete() },
                    negative = {  }
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
        menu.findItem(R.id.menu_item_delete)?.isVisible = viewModel.product != null
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()

        detailNameView.addTextChangedListener(nameListener)
        detailPriceView.addTextChangedListener(priceListener)
    }

    override fun onPause() {
        super.onPause()

        detailNameView.removeTextChangedListener(nameListener)
        detailPriceView.removeTextChangedListener(priceListener)
    }
}