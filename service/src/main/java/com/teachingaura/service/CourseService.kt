package com.teachingaura.service

import com.google.gson.Gson
import com.teachingaura.api.CourseDetails
import com.teachingaura.api.TopicAddDetails
import com.teachingaura.db.*
import com.teachingaura.db.model.*
import com.teachingaura.exception.CourseDoesNotExistException
import com.teachingaura.exception.TopicDoesNotExistException
import com.teachingaura.toApi
import org.springframework.stereotype.Service
import javax.inject.Inject
import javax.transaction.Transactional

@Service
class CourseService @Inject constructor(
    val courseRepository: CourseRepository,
    private val subjectRepository: SubjectRepository,
    private val topicRepository: TopicRepository,
    private val subjectService: SubjectService,
    private val topicQuizRepository: TopicQuizRepository,
    private val topicFileRepository: TopicFileRepository,
    private val topicTextRepository: TopicTextRepository
) {

    fun getCourseById(id: String): Course {
        return courseRepository.findByPid(id) ?: throw CourseDoesNotExistException()
    }

    fun create(course: Course): Course {
        return courseRepository.save(course)
    }

    @Transactional
    fun updateCourse(courseId: String, courseDetails: CourseDetails): Course {
        val course = getCourseById(courseId)
        course.updateCourse(courseDetails)
        val dbCourse = courseRepository.save(course)
        subjectService.updateSubject(dbCourse, courseDetails.subjects, course.subjects)
        return dbCourse
    }

    @Transactional
    fun updateSubjects(courseId: String, courseDetails: CourseDetails): Course {
        val course = getCourseById(courseId)
        subjectService.updateSubject(course, courseDetails.subjects, course.subjects)
        return course
    }

    fun addTopicDetails(instituteId: String,
                        courseId: String,
                        topicId: String,
                        topicAddDetails: TopicAddDetails): TopicAddDetails {

        val topic = topicRepository.findByPid(topicId)?: throw TopicDoesNotExistException()

        topicAddDetails.topicFileDetails.forEach { topicFile ->
            val topicFile = TopicFile(topicFile.id,topic, topicFile.orderIndex ,topicFile.url)
            topicFileRepository.save(topicFile)
        }

        topicAddDetails.topicTextDetails.forEach { topicText ->
            val topicText = TopicText(topicText.id, topic, topicText.orderIndex, topicText.text)
            topicTextRepository.save(topicText)
        }

        topicAddDetails.topicQuizDetails.forEach { topicQuiz ->
            val gson = Gson()
            val json = gson.toJson(topicQuiz.questions)
            val topicQuiz = TopicQuiz(topicQuiz.id, topic, topicQuiz.orderIndex,json)
            topicQuizRepository.save(topicQuiz)
        }
        return topicAddDetails
    }

    fun getTopicDetails(instituteId: String,
                        courseId: String,
                        topicId: String): TopicAddDetails {

        val topicFiles = topicFileRepository.findByTopicPid(topicId)
        val topicTexts = topicTextRepository.findByTopicPid(topicId)
        val topicQuizs = topicQuizRepository.findByTopicPid(topicId)

        return TopicAddDetails(topicQuizs.map { it.toApi() }, topicTexts.map { it.toApi() }, topicFiles.map { it.toApi() })
    }

    fun searchCourse(searchKeyword:String,):List<Course> {
        return courseRepository.findByNameContaining(searchKeyword);
    }

    fun deleteCourse(instituteId: String, courseId: String) {
        val course = getCourseById(courseId)
        course.isDeleted = true
        courseRepository.save(course)
    }
}