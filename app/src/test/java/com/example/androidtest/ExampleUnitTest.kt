package com.example.androidtest

import com.example.androidtest.data.ContactRepository
import com.example.androidtest.model.ApiContact
import com.example.androidtest.model.Address
import com.example.androidtest.viewmodel.DetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.example.androidtest.viewmodel.ContactViewModel
import com.example.androidtest.model.Contact
import com.example.androidtest.network.ContactApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: ContactRepository
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = DetailsViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadContactById devrait mettre a jour le contact en cas de succes`() = runTest {
        val fauxContact = ApiContact(
            id = 1,
            name = "John Doe",
            email = "john@example.com",
            address = Address(
                street = "123 Main St",
                suite = "Apt 1",
                city = "Cityville",
                zipcode = "12345"
            )
        )
        coEvery { repository.getContactById(1) } returns fauxContact

        viewModel.loadContactById(1)
        testDispatcher.scheduler.advanceUntilIdle()

        val contact = viewModel.contact.value
        assertNotNull(contact)
        assertEquals("John Doe", contact?.name)
        assertEquals("Cityville", contact?.address?.city)
    }

    @Test
    fun `loadContactById devrait mettre le contact a null en cas d'erreur`() = runTest {
        coEvery { repository.getContactById(any()) } throws Exception("Network error")

        viewModel.loadContactById(1)
        testDispatcher.scheduler.advanceUntilIdle()

        val contact = viewModel.contact.value
        assertNull(contact)
    }
}

@ExperimentalCoroutinesApi
class ContactViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: ContactRepository
    private lateinit var viewModel: ContactViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadContacts devrait mettre a jour la liste des contacts en cas de succes`() = runTest {
        val fakeContacts = listOf(
            Contact(1, "John Doe", "123 Main St"),
            Contact(2, "Jane Smith", "456 Maple Ave")
        )
        coEvery { repository.getContactList() } returns fakeContacts

        viewModel = ContactViewModel(repository)

        testDispatcher.scheduler.advanceUntilIdle()

        val contacts = viewModel.contacts.first()
        assertEquals(fakeContacts, contacts)
    }

    @Test
    fun `loadContacts met a jour avec une liste vide quand ca echoue`() = runTest {
        coEvery { repository.getContactList() } throws RuntimeException("Erreur réseau")

        viewModel = ContactViewModel(repository)

        testDispatcher.scheduler.advanceUntilIdle()

        val contacts = viewModel.contacts.first()
        assertEquals(emptyList<Contact>(), contacts)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ContactApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ContactApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContactApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getContacts returns list of contacts`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                [
                    {
                        "id": 1,
                        "name": "Leanne Graham",
                        "email": "Sincere@april.biz",
                        "address": {
                            "street": "Kulas Light",
                            "suite": "Apt. 556",
                            "city": "Gwenborough",
                            "zipcode": "92998-3874"
                        }
                    },
                    {
                        "id": 2,
                        "name": "Ervin Howell",
                        "email": "Shanna@melissa.tv",
                        "address": {
                            "street": "Victor Plains",
                            "suite": "Suite 879",
                            "city": "Wisokyburgh",
                            "zipcode": "90566-7771"
                        }
                    }
                ]
            """.trimIndent())

        mockWebServer.enqueue(mockResponse)

        val contacts = apiService.getContacts()

        assertEquals(2, contacts.size)
        assertEquals("Leanne Graham", contacts[0].name)
        assertEquals("Gwenborough", contacts[0].address.city)
        assertEquals("Ervin Howell", contacts[1].name)
    }

    @Test
    fun `getContactById renvoie un contact unique`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                    "id": 1,
                    "name": "Leanne Graham",
                    "email": "Sincere@april.biz",
                    "address": {
                        "street": "Kulas Light",
                        "suite": "Apt. 556",
                        "city": "Gwenborough",
                        "zipcode": "92998-3874"
                    }
                }
            """.trimIndent())

        mockWebServer.enqueue(mockResponse)

        val contact = apiService.getContactById(1)

        assertEquals(1, contact.id)
        assertEquals("Leanne Graham", contact.name)
        assertEquals("Gwenborough", contact.address.city)
        assertEquals("Kulas Light", contact.address.street)
    }
    @Test // pour voir ce qui se passe quand la ressource demande n'est pas sur le serveur
    fun `getContacts doit lancer une HttpException en cas de erreur 404`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(404)
            .setBody("Non Trouvé")
        mockWebServer.enqueue(mockResponse)

        try {
            apiService.getContacts()
            fail("Expected HttpException")
        } catch (e: Exception) {
            assertTrue(e is retrofit2.HttpException)
            assertEquals(404, (e as retrofit2.HttpException).code())
        }
    }

    @Test // pour voir quand le serveur a un probleme et ne peut pas répondre correctement
    fun `getContacts doit lancer une HttpException en cas de erreur 500`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody("Erreur Interne du Serveur")
        mockWebServer.enqueue(mockResponse)

        try {
            apiService.getContacts()
            fail("Expected HttpException")
        } catch (e: Exception) {
            assertTrue(e is retrofit2.HttpException)
            assertEquals(500, (e as retrofit2.HttpException).code())
        }
    }
}