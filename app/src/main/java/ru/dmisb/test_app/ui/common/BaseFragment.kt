package ru.dmisb.test_app.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    fun initToolbar(toolbar: Toolbar, title: String, showBackButton: Boolean = false) {
        (activity as? AppCompatActivity?)?.apply {
            toolbar.title = title
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                if (showBackButton) {
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.drawable.ic_arrow_back)
                } else {
                    setDisplayHomeAsUpEnabled(false)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        val view = activity?.currentFocus
        view?.hideKeyboard()
    }
}