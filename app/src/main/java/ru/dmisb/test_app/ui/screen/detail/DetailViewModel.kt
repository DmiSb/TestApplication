package ru.dmisb.test_app.ui.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import ru.dmisb.test_app.data.Product
import ru.dmisb.test_app.domain.Repository
import ru.dmisb.test_app.ui.common.BaseViewModel
import ru.dmisb.test_app.ui.common.singleArgViewModelFactory

class DetailViewModel(val product: Product?) : BaseViewModel() {

    companion object {
        val factory =
            singleArgViewModelFactory(::DetailViewModel)
    }

    var productName: String = ""
        set(value) {
            field = value
            validateModel()
        }

    var productPrice: String = ""
        set(value) {
            field = value
            validateModel()
        }

    private val _nameValid = MutableLiveData<Boolean>()
    val nameValid: LiveData<Boolean> = _nameValid

    private val _priceValid = MutableLiveData<Boolean>()
    val priceValid: LiveData<Boolean> = _priceValid

    private val _modelValid = MutableLiveData<Boolean>()
    val modelValid: LiveData<Boolean> = _modelValid

    private val _state = MutableLiveData<DetailState>()
    val state: LiveData<DetailState> = _state

    init {
        productName = product?.name.orEmpty()
        productPrice = product?.price?.toString().orEmpty()
    }

    fun save() = launch {
        try {
            val price =  productPrice.toDouble()
            Repository.insertProduct(Product(product?.id ?: 0, productName, price))
            _state.value = DetailState.OnSuccessSaved()
        } catch (e: Exception) {
            e.printStackTrace()
            _state.value = DetailState.OnError(e.message.orEmpty())
        }
    }

    fun delete() = launch {
        try {
            product?.let{
                Repository.deleteProduct(it)
            }
            _state.value = DetailState.OnSuccessDelete()
        } catch (e: Exception) {
            e.printStackTrace()
            _state.value = DetailState.OnError(e.message.orEmpty())
        }
    }

    private fun validateModel() {
        _nameValid.value = productName.length > 2
        _priceValid.value = try {
            val price = productPrice.toDouble()
            price > 0.0
        } catch (e: Exception) {
            false
        }
        _modelValid.value = nameValid.value == true && priceValid.value == true
    }
}