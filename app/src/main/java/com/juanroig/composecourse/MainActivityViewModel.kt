package com.juanroig.composecourse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivityViewModel() : ViewModel() {

    var showSplashScreen by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            delay(2000)
            showSplashScreen = false
        }
    }

}


