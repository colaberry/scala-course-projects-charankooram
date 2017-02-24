
class TypeClass[T]

class ParentClass extends Ordered[ParentClass]{

  def compare(that:ParentClass)={
  0
  }
}

class ChildClass extends ParentClass

class TypeClass[T]

val p = new TypeClass[ParentClass]
val c = new TypeClass[ChildClass]


class TypeClass[T]


val anotherp : TypeClass[ParentClass] = p

class TypeClass[+T] //co-variance

val p = new TypeClass[ParentClass]
val c = new TypeClass[ChildClass]

val pr : TypeClass[ParentClass] = c

class TypeClass[-T] //contra-variance

val p = new TypeClass[ParentClass]
val c = new TypeClass[ChildClass]

val pr2 : TypeClass[ChildClass] = p

class SomeOtherClass
class GreatList[T]{

def add[S>:T](n:S)={
// LowerBound S must be SuperClass of T
}

def altAdd[S<:T](n:S)={
// UpperBound S must be a subclass of T
}

// WORKS WITH TRAITS
def altTraitAdd[T<:Ordered[T]](n:T)={
// T must be mixed with Ordered Trait
}


}




class SomeList[T <: Ordered[T]]

val c = new SomeList[ParentClass]

abstract class Shape
case class Square(s:Int) extends Shape
case class Rectangle(l:Int,b:Int) extends Shape

implicit class RectangularHelper(l:Int){
def x(b:Int) = Rectangle(l,b)
}

//val r = 5x4
//val r = RectangularHelper(5).x(4)


val m = Map("Mavericks" -> "Dallas","Lakers"->"LA")


m("Mavericks")

m("Warriors")

m get "Lakers"

m get "Lakers" match {
    case Some(s) => println(s)
    case None => println("Not found")
}

m get "Warriors" match { 
 case Some(s) => println(s)
 case None => println("Not found")
}

def addMethod(y:Option[String]) : Option[String] = {
  if(y!=None) y else None
}

addMethod(some(name)) match {
  case Some(s) => println(s)
  case None => println("not there")

}

/*
Null ( No Reference objects found)
Nothing ( abnormal termination) 
None (no value) 
Unit functions that do not return anything 
nil empty list
*/


