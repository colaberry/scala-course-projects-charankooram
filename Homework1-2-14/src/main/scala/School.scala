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
    course.removeCandidate(student);
    student.removeCourse(course);
  }

   override def toString(): String = ""
}
