package group.one.sos.domain.usecases

import group.one.sos.data.local.preferences.PreferenceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Onboarding Use Case
 *
 * Provides business logic functions for onboarding, most of which are
 * wrappers around the preference manager to prevent direct access
 */
class OnboardingUseCase @Inject constructor(private val preferenceManager: PreferenceManager) {
    fun isOnboardingCompleted(): Flow<Boolean> {
        return preferenceManager.isOnboardingCompleted()
    }

    suspend fun completeOnboarding() {
        preferenceManager.completeOnboarding()
    }
}