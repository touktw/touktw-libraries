package com.touktw.core.security

import android.security.keystore.KeyProperties
import com.google.common.truth.Truth.assertThat
import com.touktw.core.security.module.SignModule
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SecurityTest {
    companion object {
        private const val cipherKeyAlias = "cipherKey"
        private const val signKeyAlias = "signKey"

        @BeforeAll
        @JvmStatic
        internal fun setup() {
            TestUtil.timberInit()
            keyStore.deleteEntry(cipherKeyAlias)
            keyStore.deleteEntry(signKeyAlias)
        }
    }

    @Nested
    @DisplayName("데이터를 서명하고")
    inner class CipherTest {
        @Test
        @DisplayName("데이터를 암호화 하고 복호화 하면 원본 데이터가 나와야 한다.")
        fun 데이터를_암호화_하고_복호화_하면_원본_데이터가_나와야_한다() {
            val data = "test"
            val module = cipherModule(
                algorithm = KeyProperties.KEY_ALGORITHM_AES,
                blockMode = KeyProperties.BLOCK_MODE_CBC,
                padding = KeyProperties.ENCRYPTION_PADDING_PKCS7,
                aesKeySize = 256
            )

            val encryptData = module.encrypt(
                alias = cipherKeyAlias,
                value = data.toByteArray(defaultCharset)
            )

            val decrypt = module.decrypt(
                alias = cipherKeyAlias,
                encryptData = encryptData
            ).toString(defaultCharset)

            assertThat(decrypt)
                .isEqualTo(data)
        }
    }

    @Nested
    @DisplayName("데이터를 서명하고")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class SignTest {
        private val data = "this is sign"
        private lateinit var signature: String

        private val signModule: SignModule by lazy {
            signModule(
                padding = KeyProperties.SIGNATURE_PADDING_RSA_PSS,
                KeyProperties.DIGEST_SHA256
            )
        }

        @BeforeEach
        fun sign() {
            signature = base64EncodeString(
                signModule.sign(
                    alias = signKeyAlias,
                    value = data.toByteArray(defaultCharset)
                )
            )
        }

        @Test
        @DisplayName("다른 데이터를 서명 했을때 다른 값이 나와야 한다.")
        fun `다른_데이터를_서명_했을때_다른_값이_나와야_한다`() {
            val otherSignature = base64EncodeString(
                signModule.sign(
                    alias = signKeyAlias,
                    value = "other sign".toByteArray(defaultCharset)
                )
            )

            assertThat(otherSignature)
                .doesNotMatch(signature)
        }

        @Test
        @DisplayName("서명 확인이 성공해야 한다.")
        fun `서명_확인이_성공해야_한다`() {
            val result = signModule.verify(
                alias = signKeyAlias,
                origin = data.toByteArray(defaultCharset),
                signature = decodeBas64String(signature)
            )

            assertThat(result)
                .isTrue()
        }
    }
}