import scala.collection.mutable.ArrayBuffer
/**
  * Created by Colaberry2017 on 2/14/2017.
  */
object Driver extends App {

    var list_students = new ArrayBuffer[Student]
    var list_courses = new ArrayBuffer[Course]

    var c1 = new Course("wizardry", new ArrayBuffer[Student]())
    var c2 = new Course("Physics", new ArrayBuffer[Student]())
    var c3 = new Course("Social", new ArrayBuffer[Student]())
    var c4 = new Course("Maths", new ArrayBuffer[Student]())
    var c5 = new Course("Science", new ArrayBuffer[Student]())

    list_courses.+=(c1)
    list_courses.+=(c2)
    list_courses.+=(c3)
    list_courses.+=(c4)
    list_courses.+=(c5)



   var s1 = new Student("student1", new ArrayBuffer[Course])
   var s2 = new Student("student2", new ArrayBuffer[Course])
   var s3 = new Student("student3", new ArrayBuffer[Course])
   var s4 = new Student("student4", new ArrayBuffer[Course])
   var s5 = new Student("student5", new ArrayBuffer[Course])

  list_students.+=(s1)
  list_students.+=(s2)
  list_students.+=(s3)
  list_students.+=(s4)
  list_students.+=(s5)

   var school = new School(list_students,list_courses)

   school.enroll(c1,s1)
   school.enroll(c4,s1)
   school.enroll(c3,s1)

  school.enroll(c1,s2)
  school.enroll(c4,s2)
  school.enroll(c3,s2)

  school.enroll(c4,s3)
  school.enroll(c5,s3)
  school.enroll(c3,s3)

  school.enroll(c1,s4)
  school.enroll(c2,s4)
  school.enroll(c3,s4)

  school.enroll(c1,s5)
  school.enroll(c2,s5)
  school.enroll(c4,s5)



   // var r
    println("Tests after enrollment ")
    println()


    println("students registered in course 1")
    //println(c1.registeredStudents)
    for( r <- c1.registeredStudents) print(r+" ")
    println()
    println()

    println("students registered in course 2")
    //println(c2.registeredStudents)
    for( r <- c2.registeredStudents) print(r+" ")
    println()
    println()

    println("students registered in course 3")
    //println(c3.registeredStudents)
    for( r <- c3.registeredStudents) print(r+" ")
    println()
    println()

    println("students registered in course 4")
    //println(c4.registeredStudents)
    for( r <- c4.registeredStudents) print(r+" ")
    println()
    println()

    println("students registered in course 5")
    //println(c5.registeredStudents)
    for( r <- c5.registeredStudents) print(r+" ")
    println()
    println()


    /*println(s1.registeredCourses)
    println(s2.registeredCourses)
    println(s3.registeredCourses)
    println(s4.registeredCourses)
    println(s5.registeredCourses)*/

  println("Courses enrolled by student 1")
  for(r <- s1.registeredCourses()) print(r+" ")
  println()
  println()

  println("Courses enrolled by student 2")
  for(r <- s2.registeredCourses()) print(r+" ")
  println()
  println()

  println("Courses enrolled by student 3")
  for(r <- s3.registeredCourses()) print(r+" ")
  println()
  println()

  println("Courses enrolled by student 4")
  for(r <- s4.registeredCourses()) print(r+" ")
  println()
  println()

  println("Courses enrolled by student 5")
  for(r <- s5.registeredCourses()) print(r+" ")
  println()
  println()

    school.deEnroll(c1,s1)
    school.deEnroll(c1,s1)

    println("Tests after derollment")
    println()


  /*println(c1.registeredStudents)
  println(c2.registeredStudents)
  println(c3.registeredStudents)
  println(c4.registeredStudents)
  println(c5.registeredStudents)


  println(s1.registeredCourses)
  println(s2.registeredCourses)
  println(s3.registeredCourses)
  println(s4.registeredCourses)
  println(s5.registeredCourses)*/




  println("students registered in course 1")
  //println(c1.registeredStudents)
  for( r <- c1.registeredStudents) print(r+" ")
  println()
  println()

  println("students registered in course 2")
  //println(c2.registeredStudents)
  for( r <- c2.registeredStudents) print(r+" ")
  println()
  println()

  println("students registered in course 3")
  //println(c3.registeredStudents)
  for( r <- c3.registeredStudents) print(r+" ")
  println()
  println()

  println("students registered in course 4")
  //println(c4.registeredStudents)
  for( r <- c4.registeredStudents) print(r+" ")
  println()
  println()

  println("students registered in course 5")
  //println(c5.registeredStudents)
  for( r <- c5.registeredStudents) print(r+" ")
  println()
  println()


  println("Courses enrolled by student 1")
  for(r <- s1.registeredCourses()) print(r+" ")
  println()
  println()

  println("Courses enrolled by student 2")
  for(r <- s2.registeredCourses()) print(r+" ")
  println()
  println()

  println("Courses enrolled by student 3")
  for(r <- s3.registeredCourses()) print(r+" ")
  println()
  println()

  println("Courses enrolled by student 4")
  for(r <- s4.registeredCourses()) print(r+" ")
  println()
  println()

  println("Courses enrolled by student 5")
  for(r <- s5.registeredCourses()) print(r+" ")
  println()
  println()






}
