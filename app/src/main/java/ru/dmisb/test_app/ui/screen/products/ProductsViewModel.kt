package ru.dmisb.test_app.ui.screen.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.dmisb.test_app.data.Product
import ru.dmisb.test_app.domain.Repository
import ru.dmisb.test_app.ui.common.BaseViewModel
import ru.dmisb.test_app.ui.common.SingleLiveEvent

class ProductsViewModel : BaseViewModel() {

    val products : LiveData<List<Product>> = Repository.selectAllProduct()

    private val _animButton = SingleLiveEvent<Boolean>()
    val animButton: LiveData<Boolean> = _animButton

    fun startAnimation() = launch {
        while (products.value?.isEmpty() == true) {
            delay(3000)
            if (products.value?.isEmpty() == true)
                _animButton.value = true
        }
    }
}