import scala.collection.mutable.ArrayBuffer

/**
  * Created by Colaberry2017 on 2/14/2017.
  */
case class School(students : ArrayBuffer[Student], courses : ArrayBuffer[Course]){



  def printCoursesByStudent(student : Student) = {
    if(students.contains(student)) {
      println(student.registeredCourses())
    }
  }


  def printStudentsInCourse(course : Course) = {
    if(courses.contains(course)){
      println(course.ListAllStudents())
    }
  }


  def enroll(course : Course , student : Student) ={
    course.registerCandidate(student)
    student.takeCourse(course)
  }


  def deEnroll(course : Course , student : Student)={

    if(students.contains(student)) {
      course.removeCandidate(student)
      student.removeCourse(course)
    }
  }

  def ListAllStudentsInSchool() : ArrayBuffer[Student] =students

  def ListAllCoursesOfferedinSchool() : ArrayBuffer[Course] = courses

  def AddStudentToSchool(student : Student)={
    students.+=(student)
    // Manually enroll a student to his required courses
  }



   override def toString(): String = ""
}
