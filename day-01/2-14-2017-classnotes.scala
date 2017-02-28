Scala and java
import java.util.{Date,Locale}
import java.text.DateFormat._

println(getDateInstance(LONG,Locale.UK) format new Date)

class Person (name:String, age:Int){
    def name():Int ={name} 
    def age():Int ={age}
}

val p = new Person("charan",26)

p.name()

class Person(name:String,age:Int)
val p = new Person("A",25)
p.name

println(p)

class Monkey(name:String,age:Int){

override def toString() = {
  "Person with name "+name + " and age "+ age;
}

}

val mon = new Monkey("random",14);
println(mon)

fdfdf

abstract class Shape
case class Square(s:Int,t:Int) extends Shape
case class Rectangle(l:Int,b:Int) extends Shape
    def printObj(x:Shape) = x match {
      case Square(a,b) => println("Square a "+a +" and b "+b)
      case Rectangle(x,y) => println(x +" "+y)

    }


printObj(Square(2,3))
printObj(Rectangle(4,5))



def modN(n:Int,x:Int) = ((x%n)==0)

modN(2,5)

def modN(n:Int = 2, x:Int) = ((x%n)==0)

modN(x=5)

modN(5)

modN(2)(5)

def modN(n:Int)(x:Int) = (x%n)==0

modN(2)(5)

modN(2)

List(4,5,6).map(modN())


