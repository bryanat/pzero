// Worked with Yueqi

import org.mongodb.scala._
import org.mongodb.scala.model._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Projections._
import org.mongodb.scala.model.Sorts._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.model.UpdateOptions
import org.mongodb.scala.bson.BsonObjectId
import tour.Helpers._
import scala.io.StdIn.readLine
import scala.concurrent._
import ExecutionContext.Implicits.global
//import org.mongodb.scala.bson.collection.mutable.Document
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.bson._

//RENAME pcollection

object Main extends App {
  println("Main started")

  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("pzero")
  // rename pcollection
  val pcollection: MongoCollection[Document] = database.getCollection("passwordcollection")
  // database.createCollection("collection1")
  // database.createCollection("collection2")


  // pcollection.insertOne(Document("username" -> "user1", "password" -> "password1")).printResults()
  // pcollection.insertOne(Document("username" -> "user2", "password" -> "password2")).printResults()
  // pcollection.insertOne(Document("username" -> "user3", "password" -> "password3")).printResults()
  // pcollection.insertOne(Document("username" -> "user6", "password" -> "password6")).printResults()
  // pcollection.insertMany(Seq(Document("username" -> "user4", "password" -> "password4"), Document("username" -> "user5", "password" -> "password5"))).printResults()


  def start(): Unit={
      val stdIN = System.console()
      println("Welcome to Jobs.com. please log in or create a new account")
      println("enter L to log in")
      println("enter C to create a new account")
      val ans = readLine().toLowerCase()
      if (ans =="l") {logIn()}
      else if (ans=="t") {testThis()}
      else if (ans=="c") {createAccount()}
  }

  start()

  /* REGEX */
  /* FUTURE & PROMISE */
  /* EXTENDS */
  
  /* REGEX 
    val myRegex = "([\"'])(?:(?=(\\?))\2.)*?\1".r
    var yp = pcollection.find(Document("username" -> "user1")).results().head.get("username").get.getClass //BsonString{value='user1'}
    myRegex.findFirstMatchIn( yp ) match {
        case Some(_) => println("Password OK")
        case None => println("Password must contain a number")
    }
  */


  // def isInt(userinput): Boolean = {
  //   if (userinput.toInt) {
  //     true
  //   }
  //   else {
  //     false
  //     //redo input
  //   }
  // }

  // val x = isInt(readLine("username: ")) 


  // def toInt(s: String): Option[Int] = {
  // try {
  //   Some(s.toInt)
  // } catch {
  //   case e: Exception => None
  // }
  // }


  // def isCorrectType(): Boolean = {
  //   x.getClass
  //     case int >
  //       isInt()
  // }

  // def syntaxedComments(): Unit = {
  //   println(pcollection.find(Document("username" -> "user1")).results().head.toJson()) 
  //   println(pcollection.find(Document("username" -> "user1")).results().head.get("username")) //user1 
  // }

  def testThis(): Unit = {
    println("I AM WORKING")


    //pcollection.find(Document("username" -> "user1")).printResults()
    //pcollection.find(Document("username" -> "user1")).results().getClass
    // println(pcollection.find(Document("username" -> "user1")).results().getClass)
    // println(pcollection.find(Document("username" -> "user1")).results())
      // will need find() or filter() after .head
    
    // println(pcollection.find(Document("username" -> "user1")).results().head.get("username").get.getClass) //BsonString{value='user1'}
    
    // var yp = pcollection.find(Document("username" -> "user1")).results().head.get("username").get.toString.getClass //BsonString{value='user1'}
    //var yp = pcollection.find(Document("username" -> "user1")).results().head.get("username").get.asString() //BsonString{value='user1'}
    // var yp = pcollection.find(Document("username" -> "user1")).results().head.get("username").get.getClass //BsonString{value='user1'}
    //var yl = pcollection.find(Document("username" -> "user1")).results().head.get("key").get.asString().getValue() //TYPE: Bson VALUE: BsonString{value='user1'}
    
    // GOLDEN IT FINALLY WORKS, ALL FINDS NOW CAN WORK SYNCRONOUSLY WITH THE CODE BELOW WITHOUT ASYNC FUTURES AND PROMISES
    var yp = pcollection.find(Document("username" -> "user1")).results().head.get("username").get.asString().getValue() //TYPE: Bson VALUE: BsonString{value='user1'}
    println(yp)
    // val regexQuote = "/('[^']*')/".r //finds values between 'single quotations'
    // var test = "dfbashdfbas'thisrighthere'"
    // var testwhoa = for (yup <- regexQuote.findFirstMatchIn( yp )) yield yup.group(1)
    // println(testwhoa)
    
    //  match {
    //     case Some(_) => println("Password OK")
    //     case None => println("Password must contain a number")
    // }
    
    //println(pcollection.find(Document("username" -> "user1")).results().head.get("username").get)

    println("deadline.")
    // println(pcollection.find(Document("username" -> "user1")).results().head) // Document : 
    //println(pcollection.find(Document("username" -> "user1")).results().head.get("username")) // Some
    
    
  }
      //
    // println(pcollection.find(Document("username" -> "user1")).results().head.find(("username", "user1"))) //user1
      // ^^^^ AHH ITS A DOCUMENT
      // {"key" : value}
      // Document("key1" -> "value1", "")

 

  def logIn(): Unit= {

    val v1 = "hey"
    val v2 = "678"

    // println(v1.toInt) // try catch if error then while loop
    println(v2.toInt)

    // val v11 = readLine("enter word: ")
    val v12 = readLine("enter number: ")

    // println(v11.toInt)
    println(v12.toInt)

    val x = 5
    def f[T](v: T) = v
    println(f(x.getClass)) // T is Int, the type of x
    // //pcollection.find().printResults()
    // //var b2 = pcollection.find(in("name" -> "user1")).results()
    // /*
    //  * COMPARING SYNC AND ASYNC
    //  */
    // var b = pcollection.find(Document("username" -> "usersada1")).results()
    // //var bb = b.foreach() // returns an iterable
    // println(b) // returns an iterable

    // // println(pcollection.find().getResults())
    // //var r = pcollection.find().getResults() //"name" : "squiggles"
   }


   


  def createAccount(): Unit= {
    println("createAccount called")
    var exists = true
    var inputName = ""
    var inputPassword = ""
    //create username and password
    while (exists == true) {
    var p = Promise[Boolean]()
    var f = p.future
    var usernameExists = false
    var user = ""
    var pass = ""
    // var w = () => {
    //   pass = result.pass
    //   user = result.user
    // }
      println("please create new username and password")
      inputName = readLine("username: ") 
      inputPassword = readLine( "password: ") 
      //if username already exists then set usernameExists = true
      val producerUsernameExists = Future {
      pcollection.find(Document("username" -> inputName)).subscribe(new Observer[Document](){
        var whatever = "just remember this is capable here"
          override def onNext(result: Document) = {
            println("I AM RUNNING ")
            
            // var w = () => {
            //   pass = result.pass
            //   user = result.user
            // }
            usernameExists = true
            // p success w
            // println("isndieOnnext usernameexists:" + usernameExists)
          }
          override def onError(e: Throwable): Unit = println(s"Error: $e")
          override def onComplete(): Unit = println("Completed")
        })
      }
      println("outside foreach:" + usernameExists)
      val consumerUsernameExists = Future {
        println("jsadbfkasbd")
        f foreach { ifUsernameExistsFutureVar =>
          // if already exists, ask again
          println("inside foreach:" + usernameExists)
          if (ifUsernameExistsFutureVar == true) {
              println("user name already exists, please create another one")
          }
          else if (inputPassword.length<6) {
              println("password must be at least 6 characters. please enter again")
          }
          else {
              // db.password_file.insert({"name": inputName})
              // db.password_file.insert({"password: " inputPassword})
              pcollection.insertOne(Document("name" -> inputName, "password" -> inputPassword))
              exists = false
              println("IM HEREEEEEEE XXX")
          }
        }
      }
    }
  }  

  // val mongoClient2: MongoClient = MongoClient()
  // val database2: MongoDatabase = mongoClient2.getDatabase("pzero")
  // val c1collection: MongoCollection[Document] = database2.getCollection("collection1")
  // c1collection.insertOne(Document("whateverkey" -> "whateverrrrrvalue"))
  // c1collection.find().printResults()
  
  // val c2collection: MongoCollection[Document] = database.getCollection("collection2")
  // c2collection.insertOne(Document("somekey" -> "someeeeeeeevalue"))
  // c2collection.find().printResults()

}

/*****************************************************************************/
// .subscribe(new Observer[Document](){
//         var whatever = "just remember this is capable here"

//         override def onNext(result: Document): Unit = {
//           println("YOURE INSIDE AN OBSERVABLE")
//           }
//         override def onError(e: Throwable): Unit = println(s"Error: $e")
        
//         override def onComplete(): Unit = println("Completed")
//       } })





    // /*
    //  * COMPARING SYNC AND ASYNC
    //  */
    // // SYNC
    // var b = pcollection.find(Document("username" -> "user1")).results() //results is an async function (implemented through Await scala.concurrent.Await (blocking) )
    // b.results() // returns a list of iterables (THIS IS GOING TOO FAR FOR SAKE OF VERBAL TIME)
    // foreach() // returns an iterable (THIS IS GOING TOO FAR)

    // // ASYNC (alt + right arrow key to split and compare side by side)
    // var b = pcollection.find(Document("username" -> "user1")).subscribe() //subscribe is an async function (implemented through Future and Promise scala.concurrent.{Future, Promise} (nonblocking))
    // b.subscribe() // returns an list of observer (THIS IS GOING TOO FAR FOR SAKE OF VERBAL TIME)
    