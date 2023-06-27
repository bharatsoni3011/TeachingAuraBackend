package com.teachingaura;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DatastoreEmulatorContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@SpringBootTest
@Testcontainers
@ActiveProfiles("integration")
public class DatastoreTestcontainersIntegrationTests {

    @Container
    private static final DatastoreEmulatorContainer datastoreEmulator =
            new DatastoreEmulatorContainer(
                    DockerImageName.parse("gcr.io/google.com/cloudsdktool/cloud-sdk:317.0.0-emulators"))
                    .withStartupTimeout(Duration.of(5, ChronoUnit.MINUTES))
                    .withReuse(true);

    @DynamicPropertySource
    static void emulatorProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.gcp.datastore.host", () -> "http://" + datastoreEmulator.getEmulatorEndpoint());
    }
//
//    @Autowired OTPRepository otpRepository;
//
//    @Test
//    void testGenerateOtpApiWorksProperly() {
//        OTPStore otp = new OTPStore("test@xyz.com", "12345");
//        OTPStore saved = otpRepository.save(otp);
//        OTPStore retrieved = otpRepository.findByEmail(saved.getEmail());
//        Assert.assertEquals("test@xyz.com", retrieved.getEmail());
//        Assert.assertEquals("12345", retrieved.getOtp());
//    }
}