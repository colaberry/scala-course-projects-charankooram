import scala.collection.mutable.ArrayBuffer

/**
  * Created by Colaberry2017 on 2/14/2017.
  */
case class Student(name : String , courses : ArrayBuffer[Course]){

  def takeCourse(course : Course) ={
    //println("in debugging "+course)
    courses. += (course)
    //println(courses)
  }

  def registeredCourses()=courses

  def removeCourse(course : Course)={
    courses. -= (course)
  }

  override def toString(): String = name
}
