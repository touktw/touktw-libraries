package com.touktw.core.security.module

import android.security.keystore.KeyProperties
import androidx.annotation.StringDef
import com.touktw.core.security.SecurityModule

@StringDef(value = [
    KeyProperties.SIGNATURE_PADDING_RSA_PKCS1,
    KeyProperties.SIGNATURE_PADDING_RSA_PSS // AndroidKeyStore 에서 PSSParamterSpec 을 사용할수 없음. 서명 할 때마다 다른 값이 나옴.
])
annotation class SignaturePadding

@StringDef(value = [
    KeyProperties.DIGEST_SHA1,
    KeyProperties.DIGEST_SHA256,
    KeyProperties.DIGEST_SHA384,
    KeyProperties.DIGEST_SHA512,
])
annotation class SignatureDigest

sealed class SignModule : SecurityModule {
    abstract fun sign(
        alias: String,
        value: ByteArray,
    ): ByteArray

    abstract fun verify(
        alias: String,
        origin: ByteArray,
        signature: ByteArray,
    ): Boolean
}

