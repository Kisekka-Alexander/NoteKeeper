package com.example.notekeeper

class DataManager {
    val courses = HashMap<String, CourseInfo>()
    val notes = ArrayList<NoteInfo>()

    init{
        initialisecourses()
    }

    private fun initialisecourses(){
        var course  = CourseInfo("android_intents", "Android Programming with Intents")
        courses.set(course.courseId, course)

        course  = CourseInfo(courseId = "android_async", title = "Android Async Programming and Services")
        courses.set(course.courseId, course)

        course = CourseInfo(title = "Java Fundamentals: The Java Language", courseId = "java_lang")
        courses.set(course.courseId, course)

        course = CourseInfo("java_core", "Java Fundamentals: The core Platform")
        courses.set(course.courseId, course)
    }
}