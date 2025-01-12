package com.amver.cultura_ayacucho

import com.amver.cultura_ayacucho.data.api.ApiLogin
import com.amver.cultura_ayacucho.data.api.ApiState
import com.amver.cultura_ayacucho.data.model.login.LoginRequestUser
import com.amver.cultura_ayacucho.data.model.login.LoginResponseUser
import com.amver.cultura_ayacucho.data.repository.LoginRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import android.util.Log
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
class LoginTests {
    private lateinit var loginRepository: LoginRepository
    private lateinit var apiLogin: ApiLogin

    @Before
    fun setup() {
        // Mock Android Log
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0

        // Crear y configurar una instancia el mock de ApiLogin
        apiLogin = mockk()

        // Inyectar el mock en el repositorio
        loginRepository = LoginRepository(apiLogin)
    }


    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `login valido`() = runTest {
        // Preparacion
        val username = "testUser"
        val password = "testPass"
        val loginRequest = LoginRequestUser(username, password)

        //El expectedResponse es el objeto que esperamos que retorne el método loginUser
        val expectedResponse = LoginResponseUser(
            token = "test-token",
            username = username,
            success = true,
            message = "Login successful"
        )

        // Configurar el comportamiento del mock para lanzar una excepción específica (simulacion)
        coEvery {
            apiLogin.loginUserApi(loginRequest)//Llamamos al método suspendido que queremos simular
        } returns expectedResponse      //Simulamos que el método retorna el objeto expectedResponse

        // Act
        val result = loginRepository.loginUser(username, password)

        // Assert
        assertTrue(result is ApiState.Success)
        assertEquals(expectedResponse, (result as ApiState.Success).data)
        println("LoginRequest: $loginRequest")
        println("ExpectedResponse: $expectedResponse")
    }

    @Test
    fun `login error`() = runTest {
        // Preparación
        val username = "testUser"
        val password = "wrongPass"
        val loginRequest = LoginRequestUser(username, password)
        val errorMessage = "Invalido usuario o contraseña"

        // Configurar el comportamiento del mock para lanzar una excepción específica (simulacion)
        coEvery { //coEvery es una función de MockK que se utiliza para métodos suspendidos
            apiLogin.loginUserApi(loginRequest) //Llamamos al método suspendido que queremos simular
        } throws IOException(errorMessage) //Simulamos una excepción de tipo IOException con el mensaje de error

        // Ejecutar el método que queremos probar
        val result = loginRepository.loginUser(username, password)

        // Verificación
        assertTrue(result is ApiState.Error) //Verificamos que el resultado sea de tipo ApiState.Error
        assertEquals(errorMessage, (result as ApiState.Error).message) //Verificamos que el mensaje de error sea el esperado
    }
}