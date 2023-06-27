package com.teachingaura.db

import com.teachingaura.db.model.Announcement
import com.teachingaura.db.model.Batch
import com.teachingaura.db.model.Institute
import com.teachingaura.db.model.Schedule
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ScheduleRepository : CrudRepository<Schedule, String> {

    fun findAllByBatchInAndStartTimeIsBetween(
        batch: List<Batch>,
        startTime: Long,
        endTime: Long
    ): List<Schedule>



    fun findSchedulesByInstitute(institute: Institute): List<Schedule>

    fun findSchedulesByInstituteAndStartTimeIsAfter(institute: Institute,startTime: Long):List<Schedule>
    fun findSchedulesByInstituteAndEndTimeIsBefore(institute: Institute,endTime: Long):List<Schedule>
    fun findSchedulesByInstituteAndStartTimeIsLessThanEqualAndEndTimeIsGreaterThanEqual(institute: Institute,startTime: Long,endTime: Long):List<Schedule>

    fun findByPid(id: String): Schedule?

    fun findByInstitutePid(instituteId: String): List<Schedule>

    fun findByTeacherPid(teacherId: String): List<Schedule>

    fun findAllByBatchIn(batch: List<Batch>): List<Schedule>

    fun findByZoomMeetingId(meetingId: String): Schedule
}