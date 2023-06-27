package com.teachingaura

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserRecord
import com.teachingaura.api.*
import com.teachingaura.api.model.TokenRequest
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.random.Random

//var studentService = TeachingAuraClient.getStudentService()
//list of student id= 5050-5060
//list of teacher id= 1010-1010
//list of institute id= 2010-2020
//course id= 3010
//batch id=6010-6013
//schedule id= 7010-7031


lateinit var studentService: StudentService
lateinit var teacherService: TeacherService
lateinit var instituteService: InstituteService
fun main() {
    runBlocking {
        init()
        studentService = TeachingAuraClient.getStudentService()
        teacherService = TeachingAuraClient.getTeacherService()
        instituteService = TeachingAuraClient.getInstituteService()


/*
        generateStudents()
        generateTeachers()
        generateInstitutes()
        generateCourseById("2010", "3010")
        (5050..5055).forEach {
            println(instituteService.addStudentToInstitute("2010", it.toString()).name)
        }
        (1010..1015).forEach {
            println(instituteService.addTeacherToInstitute("2010", it.toString()).name)
        }

        (6010..6013).forEach {
            println(generateBatchById("2010", it.toString(), "3010").id)
        }

        println(instituteService.addStudentToBatch("2010", "5050", "6010"))
        println()


        //past
        (7010..7013).forEach {
            val schedule = generateScheduleById(
                it.toString(),
                "2010",
                "6010",
                "1010",
                LocalDateTime.now().minusDays(1).minusHours((it - 7010).toLong())
            )
            println(
                "${schedule.id} -> ${schedule.startTime} ${schedule.endTime} ${
                    LocalDateTime.now().toEpochSecond(
                        ZoneOffset.UTC
                    ) * 1000
                }"
            )
        }

        //live
        (7014..7020).forEach {
            val schedule = generateScheduleById(
                it.toString(),
                "2010",
                "6010",
                "1010",
                LocalDateTime.now().plusHours((it - 7014).toLong())
            )
            println(
                "${schedule.id} -> ${schedule.startTime} ${schedule.endTime} ${
                    LocalDateTime.now().toEpochSecond(
                        ZoneOffset.UTC
                    ) * 1000
                }"
            )
        }

        //upcoming
        (7021..7025).forEach {
            val schedule = generateScheduleById(
                it.toString(),
                "2010",
                "6010",
                "1010",
                LocalDateTime.now().plusDays(1).minusHours((it - 7021).toLong())
            )
            println(
                "${schedule.id} -> ${schedule.startTime} ${schedule.endTime} ${
                    LocalDateTime.now().toEpochSecond(
                        ZoneOffset.UTC
                    ) * 1000
                }"
            )
        }
        (7026..7030).forEach {
            val schedule = generateScheduleById(
                it.toString(),
                "2010",
                "6010",
                "1010",
                LocalDateTime.now().plusHours((it - 7026).toLong())
            )
            println(
                "${schedule.id} -> ${schedule.startTime} ${schedule.endTime} ${
                    LocalDateTime.now().toEpochSecond(
                        ZoneOffset.UTC
                    ) * 1000
                }"
            )
        }


        println(instituteService.removeTeacherFromInstitute("2010", "1010"))

        println(instituteService.removeStudentFromInstitute("2010", "5052"))

        println(instituteService.getInstituteOverview("2010"))
        println(instituteService.getInstituteOverview("2011"))
*/
/*
        val tempStudentId = "djauFe56PebZnElSCalkS1KSAA53"
        val instituteId = "2010"
        val batchId = "6011"
        val teacherId = "1010"

        exceptionHandling(generateBatchById(instituteId, batchId, "3010"))
        println(generateScheduleById("sch-1",instituteId,batchId,teacherId))
*/
    /*
        exceptionHandling(generateStudentById(tempStudentId))
        exceptionHandling(generateInstituteById(instituteId))
        exceptionHandling(generateTeacherById(teacherId))
        println("sdfsdfsd")

        exceptionHandling(generateCourseById(instituteId, "3010"))
        exceptionHandling(generateCourseById(instituteId, "3011"))
        exceptionHandling(generateCourseById(instituteId, "3012"))

        println("sdfsdfsd")


        exceptionHandling(generateBatchById(instituteId, batchId, "3010"))
        println("sdfsdfsd")

        exceptionHandling(instituteService.addTeacherToInstitute(instituteId, teacherId))
        println("sdfsdfsd")
        exceptionHandling(instituteService.addTeacherToBatch(instituteId,batchId, teacherId))
        println("sdfsdfsd")

        exceptionHandling(instituteService.addStudentToInstitute(instituteId, tempStudentId))
        println("sdfsdfsd")
        exceptionHandling(instituteService.addStudentToBatch(instituteId, tempStudentId, batchId))
        println("sdfsdfsd")

        println(instituteService.getCourseById(instituteId,"3010"))
        println(generateStudyMaterialById("stud-123",instituteId,batchId))
        println(instituteService.getCourseStudyMaterial(instituteId, "3010"))
*/
/*
        exceptionHandling(generateScheduleById("7031", instituteId, "6010", "1010", LocalDateTime.now()))
//        exceptionHandling(generateScheduleById("7032", "2010", "6010", "1010", LocalDateTime.now().plusHours(5)))
//        exceptionHandling(generateScheduleById("7033", "2010", "6010", "1010", LocalDateTime.now().plusDays(1)))
//        exceptionHandling(generateScheduleById("7034", "2010", "6010", "1010", LocalDateTime.now().plusHours(5).plusDays(1)))

        exceptionHandling(generateAttachmentById("9000", "2010"))
        exceptionHandling(instituteService.getAttachmentById(instituteId, "9000"))
        exceptionHandling(generateAttachmentById("attach1-2010", instituteId))

        exceptionHandling(generateStudyMaterialById("9010", instituteId, "6010"))
        println(instituteService.getCourseStudyMaterial(instituteId, "3010"))
        exceptionHandling(generateNoticeById("9090", instituteId, listOf("6010")))
*/
/*
        val testId = "test-1-$instituteId"
        instituteService.updateTest(
            instituteId,
            batchId,
            testId,
            generateTestById(testId, instituteId, batchId, LocalDateTime.now())
        )


        println(instituteService.getAllTests(instituteId, batchId))
        println(instituteService.getTeacherTests(teacherId))
        println(instituteService.getStudentTests(tempStudentId))
        println(instituteService.getTestById(instituteId, batchId, "test-1-$instituteId"))
*/
    }
}
/*
suspend fun generateNoticeById(id: String, instituteId: String, listOfBatches: List<String>): NoticeDetails {
    return instituteService.createAnnouncement(
        instituteId, NoticeDetails(
            id,
            "this is one of the notices",
            listOf(
                generateImageAttachmentById("9090", instituteId),
                generatePDFAttachmentById("9091", instituteId),
                generateVideoAttachmentById("9092", instituteId)
            ),
            listOfBatches
        )
    )
}

suspend fun generateTestById(
    testId: String,
    instituteId: String,
    batchId: String,
    startTime: LocalDateTime
): TestDetails {
    return instituteService.createTest(
        instituteId, batchId,
        TestDetails(
            testId,
            "test-$batchId-$testId",
            "description of test-$testId",
            startTime.toEpochSecond(ZoneOffset.UTC) * 1000,
            startTime.plusDays(1).toEpochSecond(
                ZoneOffset.UTC
            ) * 1000,
            batchId,
            listOf(generateTestAttachmentsById("test-$testId-attachments", instituteId))
        )
    )
}

suspend fun generateTestAttachmentsById(id: String, instituteId: String): TestAttachmentDetails {
    return TestAttachmentDetails(
        id,
        listOf(
            generateImageAttachmentById("test-attach-$id", instituteId),
            generatePDFAttachmentById("test-attach1-$id", instituteId),
            generateVideoAttachmentById("test-attach2-$id", instituteId)
        ),
        TestAttachmentType.QUESTIONPAPER
    )
}

suspend fun generateStudyMaterialById(id: String, instituteId: String, batchId: String): StudyMaterialDetails {
    return instituteService.createCourseStudyMaterial(
        instituteId, batchId, StudyMaterialDetails(
            id,
            "studymaterial $id",
            listOf(
                generateImageAttachmentById("attach1-$id", instituteId),
                generatePDFAttachmentById("attach2-$id", instituteId),
                generateVideoAttachmentById("attach2-$id", instituteId),
                ), "subject1-${instituteService.getBatchById(instituteId, batchId).courseId}"
        )
    )
}

suspend fun generateAttachmentById(
    id: String,
    instituteId: String,
    url: String,
    type: AttachmentType
): AttachmentDetails {
    return instituteService.generateSignedUrlToUpload(
        instituteId, AttachmentDetails(
            id,
            "attachment $id",
            url,
            type
        )
    )
}

suspend fun generateImageAttachmentById(id: String, instituteId: String): AttachmentDetails {
    return generateAttachmentById(
        id,
        instituteId,
        "https://images.ctfassets.net/hrltx12pl8hq/61DiwECVps74bWazF88Cy9/2cc9411d050b8ca50530cf97b3e51c96/Image_Cover.jpg?fit=fill&w=480&h=270",
        AttachmentType.IMAGE
    )
}
suspend fun generatePDFAttachmentById(id: String, instituteId: String): AttachmentDetails {
    return generateAttachmentById(
        id,
        instituteId,
        "https://iiitd.ac.in/sites/default/files/docs/education/2021/Time%20Table-Add-Drop%20Monsoon%202021V5.pdf",
        AttachmentType.PDF
    )
}

suspend fun generateVideoAttachmentById(id: String, instituteId: String): AttachmentDetails {
    return generateAttachmentById(
        id,
        instituteId,
        "https://youtu.be/fIzgiGODuC0",
        //"https://drive.google.com/file/d/1nDvifCfNy66u8R1225CcRNXXiIcGql8c/view?usp=sharing",
        AttachmentType.VIDEO
    )
}
fun exceptionHandling(f: Any) {
    try {
        println(f)
    } catch (e: Exception) {
        throw Exception("error : $e")
    }
}

suspend fun generateScheduleById(
    scheduleId: String,
    instituteId: String,
    batchId: String,
    teacherId: String,
): ScheduleDetails {
    return generateScheduleById(scheduleId, instituteId, batchId, teacherId, LocalDateTime.now())
}

suspend fun generateScheduleById(
    scheduleId: String,
    instituteId: String,
    batchId: String,
    teacherId: String,
    startTime: LocalDateTime,
): ScheduleDetails {
    return instituteService.createSchedule(
        instituteId,
        ScheduleDetails(
            scheduleId,
            instituteId,
            batchId,
            instituteService.getBatchById(instituteId, batchId).courseId,
            teacherId,
            "topic-sch$scheduleId",
            startTime.toEpochSecond(ZoneOffset.UTC) * 1000,

            startTime.plusHours(1).toEpochSecond(ZoneOffset.UTC) * 1000,
            "http://host.com$scheduleId",
            "join$scheduleId"
        )
    )
}

suspend fun generateBatchById(instituteId: String, batchId: String, courseId: String): BatchDetails {
    return instituteService.createBatch(
        "2010", BatchDetails(
            batchId,
            "batch-$courseId-$batchId",
            Random.nextLong(),
            courseId,
            instituteService.getCourseById(instituteId, courseId).name,
            instituteId,
            Random.nextBoolean()
        )
    )
}

suspend fun generateCourseById(instituteId: String, courseId: String): CourseDetails {
    return instituteService.createCourse(
        instituteId, CourseDetails(
            courseId,
            "course $courseId",
            "",
            listOf(
                SubjectDetails(
                    "subject1-$courseId",
                    "subject1",
                    listOf(
                        TopicDetails(
                            "topic1-sub1-$courseId",
                            "topic1",
                            1
                        ),
                        TopicDetails(
                            "topic2-sub1-$courseId",
                            "topic2",
                            2
                        )
                    )
                ),
                SubjectDetails(
                    "subject2-$courseId",
                    "subject2",
                    listOf(
                        TopicDetails(
                            "topic1-sub2-$courseId",
                            "topic1",
                            1
                        ),
                        TopicDetails(
                            "topic2-sub2-$courseId",
                            "topic2",
                            2
                        )
                    )
                )
            )
        )
    )
}

suspend fun generateInstitutes() {
    (2010..2020).forEach {
        println(generateInstituteById(it.toString()).id)
    }
}

suspend fun generateInstituteById(id: String): InstituteDetails {
    return instituteService.createInstitute(
        InstituteDetails(
            id,
            "institute$id",
            "logo$id",
            "institute$id@gmail.com",
            "9865489$id",
            "owner$id",
            "aboutus$id"
        )
    )
}

suspend fun generateTeachers() {
    (1010..1020).forEach {
        println(generateTeacherById(it.toString()).id)
    }
}

suspend fun generateTeacherById(id: String): TeacherDetails {
    return teacherService.createTeacher(
        TeacherDetails(
            id,
            "teacher$id",
            "tearcher$id@gmail.com",
            "8645321${id}65",
            "subject$id"
        )
    )
}

fun getGeneratedUsers() {
    for (i in 5050..5060) {
        println(FirebaseAuth.getInstance().getUser(i.toString()).email)
    }
}

fun generateUsers() {
    for (i in 5050..5060) {
        val user = createNewUser(i.toString())

        if (user != null) {
            println(FirebaseAuth.getInstance().getUser(user.uid ?: "kuch nhi mila"))
        }
    }
}
*/
suspend fun init() {
    setupFirebase()
    val firebaseApi = TeachingAuraClient.getFirebaseService()
    val tokenResponse = firebaseApi.getToken(TokenRequest("test2@springbackend.com", "test121233", true))
    TeachingAuraClient.setToken(tokenResponse.idToken)
}

suspend fun init(email: String, password: String) {
    //setupFirebase()
    val firebaseApi = TeachingAuraClient.getFirebaseService()
    //val tokenResponse = firebaseApi.getToken(TokenRequest("test2@springbackend.com", "test121233", true))
    val tokenResponse = firebaseApi.getToken(TokenRequest(email, password, true))
    TeachingAuraClient.setToken(tokenResponse.idToken)
}

suspend fun generateStudents() {
    (5050..5060).forEach {
        println(generateStudentById(it.toString()).name)
    }
}

suspend fun generateStudentById(StudentId: String): StudentDetails {
    return TeachingAuraClient.getStudentService().createStudent(
        StudentDetails(
            StudentId,
            "Student $StudentId",
            "Student$StudentId@gmail.com",
            "4566" + Random.nextLong() + "4545",
            MaxEducation.BELOW_MATRICULATE,
            Random.nextLong(54665) + 10000
        )
    )
}

fun createNewUser(id: String): UserRecord? {
    val request: UserRecord.CreateRequest = UserRecord.CreateRequest()
        .setUid(id)
        .setEmail("user" + id + "@example.com")
        .setEmailVerified(false)
        .setPassword("secretPassword")
        .setPhoneNumber("+91112233" + id)
        .setDisplayName("John Doe" + id)
        .setPhotoUrl("http://www.example.com/12345678" + id + "/photo.png")
        .setDisabled(false)

    //return FirebaseAuth.getInstance().getUser(userRecord.uid)
    return FirebaseAuth.getInstance().createUser(request)
}

fun setupFirebase() {
    val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.getApplicationDefault())
        .build()

    FirebaseApp.initializeApp(options)
}