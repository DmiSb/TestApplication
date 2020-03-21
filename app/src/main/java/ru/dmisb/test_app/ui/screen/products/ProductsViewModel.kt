package ru.dmisb.test_app.ui.screen.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.dmisb.test_app.domain.Repository
import ru.dmisb.test_app.ui.common.BaseViewModel
import ru.dmisb.test_app.ui.common.SingleLiveEvent

class ProductsViewModel : BaseViewModel() {

    private val _search = MutableLiveData<String>()

    val products = Transformations.switchMap(_search) {
        Repository.selectAllProduct(it.orEmpty())
    }

    private val _animButton = SingleLiveEvent<Boolean>()
    val animButton: LiveData<Boolean> = _animButton

    init {
        _search.value = ""
    }

    fun setSearch(search: String) {
        _search.value = search
    }

    fun startAnimation() = launch {
        while (products.value?.isEmpty() == true) {
            delay(3000)
            if (products.value?.isEmpty() == true)
                _animButton.value = true
        }
    }
}