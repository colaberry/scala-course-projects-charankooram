import scala.collection.mutable.ArrayBuffer
/**
  * Created by Colaberry2017 on 2/14/2017.
  */
case class Course(name : String, registeredStudents : ArrayBuffer[Student]){

  def registerCandidate(s:Student)={
    //println("in debugging check incoming "+s.name)
    registeredStudents.+= (s)
    //println("debugging in course register candidate "+ registeredStudents.isEmpty)
    //println(registeredStudents)
  }

  def removeCandidate(student : Student)={
    registeredStudents. -=(student)
  }

  def ListAllStudents(): ArrayBuffer[Student] = registeredStudents

  override def toString(): String = name


}
