package group.one.sos.domain.usecases

import group.one.sos.domain.contracts.PreferencesManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Onboarding Use Case
 *
 * Provides business logic functions for onboarding, most of which are
 * wrappers around the preference manager to prevent direct access
 */
class OnboardingUseCases @Inject constructor(private val preferencesManager: PreferencesManager) {
    fun isOnboardingCompleted(): Flow<Boolean> {
        return preferencesManager.isOnboardingCompleted()
    }

    suspend fun completeOnboarding() {
        preferencesManager.completeOnboarding()
    }
}