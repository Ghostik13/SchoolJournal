package com.example.rest.presentation

import android.util.Log
import android.widget.EditText
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rest.R
import com.example.rest.data.model.GifRemote
import com.example.rest.domain.GifRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class GifViewModel(private val repository: GifRepository) : ViewModel() {

    private val _gifResponse: MutableLiveData<GifRemote> = MutableLiveData()
    private val _gifTrendResponse: MutableLiveData<GifRemote> = MutableLiveData()

    val gifResponse: LiveData<GifRemote> = _gifResponse
    val gifTrendResponse: LiveData<GifRemote> = _gifTrendResponse

    private val _errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: LiveData<Int> = _errorMessage

    fun getTrendGifs() {
        viewModelScope.launch {
            try {
                val main = repository.loadTrendGifs()
                _gifTrendResponse.value = main
            } catch (e: Exception) {
                _errorMessage.value = R.string.connection
            }

        }
    }

    fun getGifs(query: String) {
        viewModelScope.launch {
            val main = repository.loadGifs(query)
            _gifResponse.value = main
        }
    }
}