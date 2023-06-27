package com.teachingaura.service.impl

import com.google.firebase.auth.UserRecord
import com.teachingaura.api.*
import com.teachingaura.db.*
import com.teachingaura.db.model.*
import com.teachingaura.exception.*
import com.teachingaura.filters.FirebaseProvider
import com.teachingaura.roles.Role
import com.teachingaura.service.*
import com.teachingaura.util.toMillis
import com.teachingaura.zoom.ZoomService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashSet

private val logger = KotlinLogging.logger {}

@Service
class InstituteServiceImpl @Inject
constructor(
    val instituteRepository: InstituteRepository,
    val courseService: CourseService,
    val studentService: StudentService,
    val subjectRepository: SubjectRepository,
    val topicRepository: TopicRepository,
    val batchService: BatchService,
    val teacherService: TeacherService,
    val scheduleRepository: ScheduleRepository,
    val studentInvitesRepository: StudentInvitesRepository,
    val teacherInvitesRepository: TeacherInvitesRepository,
    val studyMaterialRepository: StudyMaterialRepository,
    val announcementsRepository: AnnouncementsRepository,
    val zoomService: ZoomService,
    val firebaseProvider: FirebaseProvider,
    val attachmentRepository: AttachmentRepository,
    val subjectService: SubjectService
) : InstituteService {

    override suspend fun getAllInstitutes(): List<Institute> {
        return instituteRepository.findAll().toList()
    }

    override suspend fun getInstituteById(id: String): Institute {
        return instituteRepository.findByPid(id) ?: throw InstituteDoesNotExistsException()
    }

    override suspend fun createInstitute(instituteRequest: InstituteDetails): Institute {
        if (instituteRepository.existsById(instituteRequest.id)) {
            throw InstituteAlreadyExistsException("User Already exists!")
        }
        val newInstitute = Institute(
            instituteRequest.id, instituteRequest.instituteName, instituteRequest.logo,
            instituteRequest.email, instituteRequest.ownerContactNumber, instituteRequest.aboutUs,
            instituteRequest.ownerName, instituteRequest.ownerEmail
        )
        return instituteRepository.save(newInstitute)
    }

    override suspend fun createCourse(instituteId: String, courseRequest: CourseDetails): Course {
        val institute = getInstituteById(instituteId)
        val course = Course(courseRequest.id, courseRequest.name, courseRequest.description, institute,
        durationInMonths = courseRequest.durationInMonths, url = courseRequest.url)
        val dbCourse = courseService.create(course)
        courseRequest.subjects.forEach { subjectDetails ->
            val subject = Subject(subjectDetails.id, subjectDetails.name, course = dbCourse)
            val dbSubject = subjectRepository.save(subject)
            subjectDetails.topics.forEach { topicDetails ->
                val topic = Topic(
                    topicDetails.id, topicDetails.name, topicDetails.description,
                    topicDetails.ordering, dbSubject
                )
                topicRepository.save(topic)
            }
        }
        return dbCourse
    }

    override suspend fun deleteCourse(instituteId: String, courseId: String) {
        courseService.deleteCourse(instituteId, courseId)
    }

    override suspend fun createBatch(instituteId: String, batchDetails: BatchDetails): Batch {
        val institute = getInstituteById(instituteId)
        val course = courseService.getCourseById(batchDetails.courseId)
        val dbBatch =
            Batch(
                batchDetails.id,
                batchDetails.name,
                batchDetails.startDate,
                true,
                batchDetails.imageUrl!!,
                BatchType.ONLINE,
                course,
                institute
            )
        return batchService.insert(dbBatch)
    }

    override suspend fun getSchedules(instituteId: String): List<Schedule> {
        return scheduleRepository.findSchedulesByInstitute(getInstituteById(instituteId))
    }

    override suspend fun getTotalHours(instituteId: String): Long {
        return getPastSchedules(instituteId).sumOf {
            (it.endTime - it.startTime)
        }
    }

    override suspend fun sendInviteToStudent(instituteId: String, studentId: String): Student {
        //TODO: handle case when student doesnt alreay exist also
        val student = studentService.getStudent(studentId)
        val institute = getInstituteById(instituteId)
        val invite = StudentInvites(StudentInviteKey(studentId, instituteId), student, institute, InviteType.INVITED)
        studentInvitesRepository.save(invite)
        return student
    }

    override suspend fun sendInviteToStudent(instituteId: String, studentDetails: StudentDetails): Student {
        var student: Student
        try {
            student = studentService.getStudentByPhoneNumber(studentDetails.contactNumber)
        } catch (e: UserDoesNotExistException) {
            val studentId = createFirebaseUser(studentDetails.contactNumber, studentDetails.name)
            student = studentService.createStudent(
                StudentDetails(
                    studentId,
                    studentDetails.name,
                    contactNumber = studentDetails.contactNumber
                )
            )
        }
        val institute = getInstituteById(instituteId)
        if (institute.students.contains(student)) {
            throw UserAlreadyExistsException("Student is already present in the institute")
        }
        val invite = StudentInvites(StudentInviteKey(student.pid, instituteId), student, institute, InviteType.INVITED)
        studentInvitesRepository.save(invite)
        return student
    }

    override suspend fun sendInviteToTeacher(instituteId: String, teacherId: String): Teacher {
        //TODO: handle case when teacher doesnt alreay exist also
        val teacher = teacherService.getTeacherById(teacherId)
        val institute = getInstituteById(instituteId)
        val invite = TeacherInvites(TeacherInviteKey(teacherId, instituteId), teacher, institute, InviteType.INVITED)
        teacherInvitesRepository.save(invite)
        return teacher
    }

    override suspend fun sendInviteToTeacher(instituteId: String, teacherDetails: TeacherDetails): Teacher {
        var teacher: Teacher
        try {
            teacher = teacherService.getTeacherByPhoneNumber(teacherDetails.contactNumber)
        } catch (e: TeacherDoesNotExistException) {
            val teacherId = createFirebaseUser(teacherDetails.contactNumber, teacherDetails.name)
            teacher = teacherService.createTeacher(
                TeacherDetails(
                    teacherId,
                    teacherDetails.name,
                    contactNumber = teacherDetails.contactNumber
                )
            )
        }
        val institute = getInstituteById(instituteId)
        if (institute.teachers.contains(teacher)) {
            throw UserAlreadyExistsException("Student is already present in the institute")
        }
        val invite = TeacherInvites(TeacherInviteKey(teacher.pid, instituteId), teacher, institute, InviteType.INVITED)
        teacherInvitesRepository.save(invite)
        return teacher
    }

    private fun createFirebaseUser(contactNumber: String, name: String): String {
        try {
            val existingUser = firebaseProvider.getFirebase().getUserByPhoneNumber(contactNumber)
            return existingUser.uid
        } catch (ex: Exception) {
            val studentId = UUID.randomUUID().toString()
            try {
                val request: UserRecord.CreateRequest = UserRecord.CreateRequest()
                    .setUid(studentId)
                    .setPhoneNumber(contactNumber)
                    .setDisplayName(name)
                    .setDisabled(false)
                firebaseProvider.getFirebase().createUser(request)
                return studentId
            } catch (e: Exception) {
                logger.error("Failed to create user for number : $contactNumber", e)
                throw e
            }
        }
    }

    override suspend fun getStudentInvites(instituteId: String): List<StudentInvites> {
        return studentInvitesRepository.findAllById_InstituteId(instituteId)
    }

    override suspend fun getTeacherInvites(instituteId: String): List<TeacherInvites> {
        return teacherInvitesRepository.findAllById_InstituteId(instituteId)
    }

    override suspend fun updateInstitute(instituteId: String, instituteRequest: InstituteDetails): Institute {
        val institute = getInstituteById(instituteId)
        institute.name = instituteRequest.instituteName
        institute.logo = instituteRequest.logo
        institute.email = instituteRequest.email
        institute.ownerEmail = instituteRequest.ownerEmail
        institute.aboutUs = instituteRequest.aboutUs
        institute.ownerName = instituteRequest.ownerName
        return instituteRepository.save(institute)
    }

    override suspend fun getInstituteTeachers(instituteId: String): List<Teacher> {
        return getInstituteById(instituteId).teachers.toList()
    }

    override suspend fun getInstituteStudents(instituteId: String): List<Student> {
        return getInstituteById(instituteId).students.toList()
    }

    override suspend fun getInstituteCourses(instituteId: String): List<Course> {
        return getInstituteById(instituteId).courses.filterNot { it.isDeleted }.toList()
    }

    override suspend fun getInstituteBatches(instituteId: String): List<Batch> {
        return getInstituteById(instituteId).batches.toList()
    }

    override suspend fun getBatchById(instituteId: String, batchId: String): Batch {
        return batchService.getBatchById(batchId)
    }

    override suspend fun getCourseById(instituteId: String, courseId: String): Course {
        return courseService.getCourseById(courseId)
    }

    override suspend fun updateCourse(instituteId: String, courseId: String, courseRequest: CourseDetails): Course {
        return courseService.updateCourse(courseId, courseRequest)
    }

    override suspend fun updateSubjects(instituteId: String, courseId: String, courseRequest: CourseDetails): Course {
        return courseService.updateSubjects(courseId, courseRequest)
    }

    override suspend fun getCourseStudyMaterial(instituteId: String, courseId: String): List<StudyMaterial> {
        val course = courseService.getCourseById(courseId)
        return studyMaterialRepository.findBySubjectIn(course.subjects)
    }

    override suspend fun getInstituteAnnouncements(instituteId: String): List<Announcement> {
        return announcementsRepository.findByInstitutePid(instituteId)
    }

    override suspend fun createAnnouncement(instituteId: String, request: AnnouncementDetails): Announcement {
        if (announcementsRepository.existsById(request.id)) {
            throw AnnouncementAlreadyExistsException("Announcement Already exists!")
        }
        val institutes = getInstituteById(instituteId)
        val attachments = request.attachmentIds.mapNotNull { attachmentRepository.findByPid(it) }.toList()
        val batches = request.batches.mapNotNull { batchService.getBatchById(it) }
        val announcement =
            Announcement(
                request.id,
                request.content,
                AnnouncementType.INSTITUTE_LEVEL,
                attachments,
                institutes,
                batches
            )
        return announcementsRepository.save(announcement)
    }

    override suspend fun getAnnouncementById(instituteId: String, noticeId: String): Announcement {
        return announcementsRepository.findByPid(noticeId) ?: throw AnnouncementDoesNotExistsException()
    }

    override suspend fun createCourseStudyMaterial(
        instituteId: String,
        batchId: String,
        request: StudyMaterialDetails
    ): StudyMaterial {
        val attachments = request.attachmentDetailsList.mapNotNull { attachmentRepository.findByPid(it.id) }.toList()
        val subject = subjectService.getSubjectById(request.subjectId)
        val batch = batchService.getBatchById(batchId)
        val studyMaterial = StudyMaterial(request.id, request.name, attachments, subject, batch)
        return studyMaterialRepository.save(studyMaterial)
    }

    override suspend fun getScheduleById(scheduleId: String): Schedule {
        return scheduleRepository.findByPid(scheduleId) ?: throw ScheduleDoesNotExistsException()
    }

    override suspend fun getInstituteSchedules(instituteId: String): List<Schedule> {
        return scheduleRepository.findByInstitutePid(instituteId)
    }

    override suspend fun getStudentBatches(instituteId: String, studentId: String): List<Batch> {
        return studentService.getStudentBatches(studentId).filter { it.institute.pid == instituteId }.toList()
    }

    override suspend fun getTeacherBatches(instituteId: String, teacherId: String): List<Batch> {
        return teacherService.getTeacherBatches(teacherId).filter { it.institute.pid == instituteId }.toList()
    }

    override suspend fun addTopicDetails(
        instituteId: String,
        courseId: String,
        topicId: String,
        topicAddDetails: TopicAddDetails
    ): TopicAddDetails {
        return courseService.addTopicDetails(instituteId, courseId, topicId, topicAddDetails)
    }

    override suspend fun getTopicDetails(instituteId: String, courseId: String, topicId: String): TopicAddDetails {
        return courseService.getTopicDetails(instituteId, courseId, topicId)
    }

    override suspend fun createCourseV2(instituteId: String, courseV2Request: CourseDetails): Course {
        val institute = getInstituteById(instituteId)
        val course = Course(
            courseV2Request.id, courseV2Request.name, courseV2Request.description, institute,
            subTitle = courseV2Request.subTitle, url = courseV2Request.url, type = courseV2Request.type,
            fee = courseV2Request.fee, durationInMonths = courseV2Request.durationInMonths
        )
        val dbCourse = courseService.create(course)

        courseV2Request.subjects.forEach { subjectDetails ->
            val subject = Subject(subjectDetails.id, subjectDetails.name, course = dbCourse)
            val dbSubject = subjectRepository.save(subject)
            subjectDetails.topics.forEach { topicDetails ->
                val topic = Topic(
                    topicDetails.id, topicDetails.name, topicDetails.description,
                    topicDetails.ordering, dbSubject
                )
                topicRepository.save(topic)
            }
        }

        return dbCourse
    }

    override suspend fun getAllSchedules(): List<Schedule> {
        return scheduleRepository.findAll().toList()
    }

    override suspend fun addTeacherToBatch(instituteId: String, batchId: String, teacherId: String): Teacher {
        val institute = getInstituteById(instituteId)
        val batch = batchService.getBatchById(batchId)
        return teacherService.addTeacherToBatch(teacherId, institute, batch)
    }

    override suspend fun removeTeacherFromBatch(
        instituteId: String,
        batchId: String,
        teacherId: String
    ): Teacher {
        val institute = getInstituteById(instituteId)
        val batch = batchService.getBatchById(batchId)
        return teacherService.removeTeacherFromBatch(teacherId, institute, batch)
    }

    override suspend fun deleteAllInstituteAttachments(instituteId: String): Institute {
        val institute = getInstituteById(instituteId)
        institute.batches.map {
            it.studyMaterials.map {
                it.attachments.map {
                    attachmentRepository.deleteById(it.pid)
                }
            }
        }
        return institute
    }

    @Transactional
    override suspend fun deleteAllInstituteAnnouncements(instituteId: String): Institute {
        val institute = getInstituteById(instituteId)
        announcementsRepository.deleteByInstitutePid(institute.pid)
        return institute
    }

    override suspend fun getAllBatchStudents(instituteId: String, batchId: String): Set<Student> {
        val institute = getInstituteById(instituteId)
        val batch = institute.batches.find { it.pid == batchId } ?: throw BatchDoesNotExistException()
        return batch.students
    }

    override suspend fun getAllBatchTeachers(instituteId: String, batchId: String): Set<Teacher> {
        val institute = getInstituteById(instituteId)
        val batch = institute.batches.find { it.pid == batchId } ?: throw BatchDoesNotExistException()
        return batch.teachers
    }

    override suspend fun getBatchStudyMaterials(instituteId: String, batchId: String): List<StudyMaterial> {
        val institute = getInstituteById(instituteId)
        val batch = institute.batches.find { it.pid == batchId } ?: throw BatchDoesNotExistException()
        return batch.studyMaterials
    }

    override suspend fun removeStudentFromBatch(instituteId: String, batchId: String, studentId: String): Student {
        val institute = getInstituteById(instituteId)
        val batch = batchService.getBatchById(batchId)
        return studentService.removeStudentFromBatch(studentId, institute, batch)
    }

    override suspend fun getPastSchedules(instituteId: String): List<Schedule> {
        return scheduleRepository.findSchedulesByInstituteAndEndTimeIsBefore(
            getInstituteById(instituteId), getCurrentTime()
        )
    }

    override suspend fun getLiveSchedules(instituteId: String): List<Schedule> {
        val time = getCurrentTime()
        return scheduleRepository.findSchedulesByInstituteAndStartTimeIsLessThanEqualAndEndTimeIsGreaterThanEqual(
            getInstituteById(instituteId),
            time,
            time
        )
    }

    override suspend fun getUpcomingSchedules(instituteId: String): List<Schedule> {
        return scheduleRepository.findSchedulesByInstituteAndStartTimeIsAfter(
            getInstituteById(instituteId), getCurrentTime()
        )
    }

    private fun getCurrentTime() = LocalDateTime.now().toMillis()


    override suspend fun createSchedule(scheduleDetails: ScheduleDetails): Schedule {
        val meetingDetails = zoomService.createMeeting(scheduleDetails.startTime, scheduleDetails.endTime)

        val institute = getInstituteById(scheduleDetails.instituteId)
        val teacher = scheduleDetails.teacherId?.let { id ->
            teacherService.getTeacherById(id)
        }
        val subject = scheduleDetails.subjectId?.let { id ->
            subjectService.getSubjectById(id)
        }
        val batch = batchService.getBatchById(scheduleDetails.batchId)
        val schedule = Schedule(
            id = scheduleDetails.id,
            teacher,
            institute,
            batch,
            scheduleDetails.topicName,
            scheduleDetails.startTime,
            scheduleDetails.endTime,
//            "join-url",
//            "",
//            ""
            subject,
            meetingDetails.joinUrl,
            zoomMeetingId = meetingDetails.id,
            meetingDetails.hostId

        )
        scheduleRepository.save(schedule)
        return schedule
    }

    override suspend fun addStudentToInstitute(instituteId: String, studentId: String): Student {
        val institute = getInstituteById(instituteId)
        return studentService.addStudentToInstitute(studentId, institute)
    }

    override suspend fun removeStudentFromInstitute(instituteId: String, studentId: String): Student {
        val institute = getInstituteById(instituteId)
        return studentService.removeStudentFromInstitute(studentId, institute)
    }

    override suspend fun addTeacherToInstitute(instituteId: String, teacherId: String): Teacher {
        val institute = getInstituteById(instituteId)
        return teacherService.addTeacherToInstitute(teacherId, institute)
    }

    @Transactional
    override suspend fun removeTeacherFromInstitute(instituteId: String, teacherId: String): Teacher {
        val institute = getInstituteById(instituteId)
        return teacherService.removeTeacherFromInstitute(
            teacherId,
            getTeacherBatches(instituteId, teacherId),
            institute
        )
    }

    override suspend fun addStudentToBatch(instituteId: String, batchId: String, studentID: String): Student {
        val institute = getInstituteById(instituteId)
        val batch = batchService.getBatchById(batchId)
        return studentService.addStudentToBatch(studentID, institute, batch)
    }

    override suspend fun getZoomUrlById(meetingId: Long): String {
        return zoomService.retriveMeeting(meetingId).hostUrl
    }

    override suspend fun searchCourse(searchKeyword: String): Set<Course> {
        val courses: MutableSet<Course> = mutableSetOf()
        val searchKeywords: List<String> = searchKeyword.split(" ").map { it.trim() }
        for (searchWord in searchKeywords) {
            courses.addAll(courseService.searchCourse(searchWord))
            val subject = subjectService.searchSubject(searchWord)
            subject.map { courses.add(it.course) }
            val topic = topicRepository.findByNameContaining(searchWord)
            topic.map { courses.add(it.subject.course) }
        }
        return courses
    }

    override suspend fun getUserInstituteAndRole(id: String): Set<UserRoleDetails> {
        val userRoleDetails: HashSet<UserRoleDetails> = HashSet()
        try {
            val teacher = teacherService.getTeacherById(id)
            for (institute in teacher.institutes) {
                userRoleDetails.add(UserRoleDetails(institute.name, Role.ROLE_TEACHER.toString()))
            }
        } catch (_: TeacherDoesNotExistException) {
        }
        try {
            val student = studentService.getStudent(id)
            for (institute in student.institutes) {
                userRoleDetails.add(UserRoleDetails(institute.name, Role.ROLE_STUDENT.toString()))
            }
        } catch (_: UserDoesNotExistException) {
        }
        return userRoleDetails
    }

    override suspend fun searchInstitute(searchKeyword: String): Set<Institute> {
        return instituteRepository.findByNameContaining(searchKeyword).toSet()
    }
}
