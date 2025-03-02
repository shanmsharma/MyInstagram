package com.sharma.authentication.di

import com.google.firebase.auth.FirebaseAuth
import com.sharma.authentication.data.repository.LoginRepositoryImpl
import com.sharma.authentication.data.repository.SignUpRepositoryImpl
import com.sharma.authentication.domain.LoginRepository
import com.sharma.authentication.domain.SignUpRepository
import com.sharma.authentication.domain.usecase.LoginUseCase
import com.sharma.authentication.domain.usecase.SignUpUseCase
import com.sharma.common.Screen
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideLoginRepository(firebaseAuth: FirebaseAuth): LoginRepository =
        LoginRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideSignUpRepository(firebaseAuth: FirebaseAuth): SignUpRepository =
        SignUpRepositoryImpl(firebaseAuth)
}

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSignUpUseCase(signUpRepository: SignUpRepository): SignUpUseCase {
        return SignUpUseCase(signUpRepository)
    }
}