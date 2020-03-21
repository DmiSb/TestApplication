package ru.dmisb.test_app.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_detail.*
import ru.dmisb.test_app.R
import ru.dmisb.test_app.utils.hideKeyboard

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = initViewModel()
    }

    protected abstract fun initViewModel() : VM

    protected abstract val layoutResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResId, container, false)
    }

    protected fun <VM : BaseViewModel> provideViewModel(modelClass: Class<VM>) =
        ViewModelProvider(this).get(modelClass)

    protected fun <VM : BaseViewModel> provideViewModel(
        factory: ViewModelProvider.NewInstanceFactory, modelClass: Class<VM>
    ) = ViewModelProvider(this, factory).get(modelClass)

    open val showBackButton: Boolean = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as? AppCompatActivity?)?.apply {
            setSupportActionBar(detailToolbar)
            if (showBackButton) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    override fun onPause() {
        super.onPause()

        val view = activity?.currentFocus
        view?.hideKeyboard()
    }
}