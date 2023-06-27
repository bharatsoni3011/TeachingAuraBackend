package com.teachingaura.service.impl

import com.teachingaura.api.SubjectDetails
import com.teachingaura.db.StudyMaterialRepository
import com.teachingaura.db.SubjectRepository
import com.teachingaura.db.TopicRepository
import com.teachingaura.db.model.Course
import com.teachingaura.db.model.Subject
import com.teachingaura.db.model.Topic
import com.teachingaura.exception.SubjectDoesNotExistsException
import com.teachingaura.service.SubjectService
import org.springframework.stereotype.Service
import javax.inject.Inject

@Service
class SubjectServiceImpl @Inject
constructor(
    private val subjectRepository: SubjectRepository,
    private val topicRepository: TopicRepository,
    private val studyMaterialRepository: StudyMaterialRepository
) :
    SubjectService {

    override suspend fun getSubjectById(id: String): Subject {
        return subjectRepository.findByPid(id) ?: throw SubjectDoesNotExistsException()
    }

    override fun updateSubject(
        course: Course,
        subjectDetails: List<SubjectDetails>,
        subjects: MutableList<Subject>
    ) {
        // delete subjects which are not in use anymore
        subjects.forEach { subject ->
            if (subjectDetails.find { subject.pid == it.id } == null) {
                topicRepository.deleteBySubjectPid(subject.pid)
                studyMaterialRepository.deleteBySubjectPid(subject.pid)
                subjectRepository.deleteById(subject.pid)
            }
        }
        // save all the remaining subjects into database
        subjectDetails.forEach { subjectDetails ->
            val subject = Subject(subjectDetails.id, subjectDetails.name, course = course)
            val dbSubject = subjectRepository.save(subject)
            subjectDetails.topics.forEach { topicDetails ->
                val topic = Topic(topicDetails.id, topicDetails.name, topicDetails.description, topicDetails.ordering, dbSubject)
                topicRepository.save(topic)
            }
        }

    }

    override suspend fun searchSubject(searchKeyword: String): List<Subject> {
        return subjectRepository.findByNameContaining(searchKeyword)
    }
}