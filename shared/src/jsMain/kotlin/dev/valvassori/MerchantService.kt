package dev.valvassori

import dev.valvassori.domain.MerchantResponse
import dev.valvassori.repository.MerchantRepository
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.promise
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.js.Promise

@JsExport
object MerchantService : KoinComponent {
    private val dispatcher = CoroutineScope(window.asCoroutineDispatcher())
    private val repository: MerchantRepository by inject()

    fun getAllMerchants(): Promise<Array<MerchantResponse>> = dispatcher.promise {
        repository.getAllMerchants()
    }

    fun getMerchantById(id: String): Promise<MerchantResponse?> = dispatcher.promise {
        repository.getMerchantById(id)
    }
}
