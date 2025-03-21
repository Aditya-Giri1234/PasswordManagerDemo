package com.aditya.passwordmanagerdemo.common.encryption

import android.util.Base64
import com.aditya.passwordmanagerdemo.data.SessionManager
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

class AESManager @Inject constructor(private val sessionManager: SessionManager) {

    suspend fun encrypt(input: String): String {
        val key = sessionManager.getOrCreateAesKey()
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec)
        val encrypted = cipher.doFinal(input.toByteArray())
        return Base64.encodeToString(encrypted, Base64.DEFAULT)
    }

    suspend fun decrypt(encryptedInput: String): String {
        val key = sessionManager.getOrCreateAesKey()
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
        val keySpec = SecretKeySpec(key.toByteArray(), "AES")
        cipher.init(Cipher.DECRYPT_MODE, keySpec)
        val decoded = Base64.decode(encryptedInput, Base64.DEFAULT)
        return String(cipher.doFinal(decoded))
    }
}