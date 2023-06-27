package com.teachingaura

import com.teachingaura.filters.FirebaseProvider
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [Application::class])
@ActiveProfiles("test")
class BaseTest {

    @MockBean
    private lateinit var firebaseProvider: FirebaseProvider

}