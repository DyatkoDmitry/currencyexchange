package com.example.currency_exchange.modules

import android.content.Context
import com.example.currency_exchange.model.LocalState
import com.example.currency_exchange.model.LocalStateService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityComponent::class)
class ModuleStates {

    @Provides
    fun getLocalStateService(): LocalStateService {
        return LocalStateService()
    }

    @Provides
    fun getLocalStates(localStateService: LocalStateService): List<LocalState> {
        return localStateService.getLocalStates()
    }
}