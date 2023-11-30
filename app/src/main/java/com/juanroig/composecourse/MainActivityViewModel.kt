package com.juanroig.composecourse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanroig.composecourse.di.qualifiers.AppID
import com.juanroig.composecourse.di.qualifiers.AppVersionName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    @AppID private val appId: String,
    @AppVersionName private val appVersionName: String
) : ViewModel() {

    var showSplashScreen by mutableStateOf(true)
        private set

    init {
        viewModelScope.launch {
            delay(200)
            showSplashScreen = false

            println("App ID: $appId")
            println("App version name: $appVersionName")
        }
    }
}
