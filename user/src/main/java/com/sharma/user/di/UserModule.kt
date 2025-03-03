package com.sharma.user.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sharma.user.data.repository.UserRepositoryImpl
import com.sharma.user.domain.UserRepository
import com.sharma.user.domain.usecase.GetUserProfileUseCase
import com.sharma.user.domain.usecase.SaveUserProfileUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserRepository(firebaseAuth: FirebaseAuth,firestore: FirebaseFirestore, storage: FirebaseStorage): UserRepository =
        UserRepositoryImpl(firebaseAuth, firestore,storage)
}
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetUserProfileUseCase(userRepository: UserRepository): GetUserProfileUseCase {
        return GetUserProfileUseCase(userRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSaveUserProfileUseCase(userRepository: UserRepository): SaveUserProfileUseCase {
        return SaveUserProfileUseCase(userRepository)
    }
}