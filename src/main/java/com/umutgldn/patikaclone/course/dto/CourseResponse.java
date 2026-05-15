package com.umutgldn.patikaclone.course.dto;

public record CourseResponse(
    Long id,
    String name,
    String description,
    Long pathId,
    String pathName,
    Long instructorId,
    String instructorName
) {

}
