package com.aditya.passwordmanagerdemo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.passwordmanagerdemo.common.encryption.AESManager
import com.aditya.passwordmanagerdemo.common.model.ApiResponse
import com.aditya.passwordmanagerdemo.data.SessionManager
import com.aditya.passwordmanagerdemo.domain.models.PasswordInfo
import com.aditya.passwordmanagerdemo.domain.repo.PasswordRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: PasswordRepo,
    private val aesManager: AESManager ,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _passwords = MutableStateFlow<List<PasswordInfo>>(emptyList())
    val passwords = _passwords.asStateFlow()

    private val _dbOperation = MutableStateFlow<ApiResponse<String>>(ApiResponse.Initial())
    val dbOperation = _dbOperation.asStateFlow()

    private var job: Job? = null

    // StateFlow to hold the session PIN (or other value)
    private val _sessionPin = MutableStateFlow<String?>(null)
    val sessionPin: StateFlow<String?> get() = _sessionPin

    init {
        // Launch a coroutine to get the session PIN
        viewModelScope.launch {
            _sessionPin.value = sessionManager.getSessionPin()
        }
    }

    fun setPin(pin :String) = viewModelScope.launch {
        sessionManager.setSessionPin(pin)
    }


    fun getAllPasswords() {
        job?.cancel(CancellationException("A new password request is begin."))
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getAllPasswords().onEach { values ->
                    _passwords.update {
                        values.map {
                            it.copy(
                                password = aesManager.decrypt(it.password)
                            )
                        }
                    }
                }.launchIn(this)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun insertPassword(passwordInfo: PasswordInfo) = viewModelScope.launch(Dispatchers.IO) {
        _dbOperation.update {
            ApiResponse.Loading()
        }
        try {
            val count = repo.insertPassword(
                passwordInfo.copy(
                    password = aesManager.encrypt(passwordInfo.password)
                )
            )

            if (count > 0) {
                _dbOperation.update {
                    ApiResponse.Success("Password added successfully")
                }
            } else {
                _dbOperation.update {
                    ApiResponse.Error("Failed to add password")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun updatePassword(passwordInfo: PasswordInfo) = viewModelScope.launch(Dispatchers.IO) {
        _dbOperation.update {
            ApiResponse.Loading()
        }
        try {
            val count = repo.updatePassword(passwordInfo.copy(
                password = aesManager.encrypt(passwordInfo.password)
            ))
            if (count > 0) {
                _dbOperation.update {
                    ApiResponse.Success("Password updated successfully")
                }
            } else {
                _dbOperation.update {
                    ApiResponse.Error("Failed to update password")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deletePassword(passwordInfo: PasswordInfo) = viewModelScope.launch(Dispatchers.IO) {
        _dbOperation.update {
            ApiResponse.Loading()
        }
        try {
            val count = repo.deletePassword(passwordInfo.copy(
                password = aesManager.encrypt(passwordInfo.password)
            ))

            if (count > 0) {
                _dbOperation.update {
                    ApiResponse.Success("Password deleted successfully")
                }
            } else {
                _dbOperation.update {
                    ApiResponse.Error("Failed to delete password")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}