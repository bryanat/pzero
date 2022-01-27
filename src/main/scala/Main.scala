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
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.bson._
import datasets.MongoInserts


object Main extends App {
  println("Main started")

  // Abstract to its own file then import
  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("pzero")
  val ucollection: MongoCollection[Document] = database.getCollection("covidcollection")

  // Generates 555 usernames and passwords into the collection ucollection
  // MongoInserts.insertUsernamesAndPasswords(555, ucollection)
  
  def init() = {
    println("""
      |
      |
      |
      |
      |== MENU == 
      |Select a number to continue
      |1. Sync  - C - synchronous create/.insert()
      |2. Sync  - R - synchronous read/.find()    
      |3. Sync  - U - synchronous update/.update()
      |4. Sync  - D - synchronous delete/.delete()
      |5. Async - C - asynchronous create/.insert() 
      |6. Async - R - asynchronous read/.find()     
      |7. Async - U - asynchronous update/.update() 
      |8. Async - D - asynchronous delete/.delete() 
      |""".stripMargin) // stripMargin 

    var selection = 0 // default value
    try {
      selection = readLine("Select a number from the menu: ").toInt
    }
    catch {
      case e => println("Can't you read, pick a number.")
    }
    finally {
      selection match {
        case 0 => println("You didn't input a number")
        case 1 => println("syncCreate()")
        case 2 => println("syncRead()")
        case 3 => println("syncUpdate()")
        case 4 => println("syncDelete()")
        case 5 => println("asyncCreate()")
        case 6 => println("asyncRead()")
        case 7 => println("asyncUpdate()")
        case 8 => println("asyncDelete()")
      }
    }
  }
  init()

  def syncCreate(): Unit = {
    println("syncCreate() called")
    //init Data object for start time
  }

  def syncRead(): Unit = {
    println("syncRead() called")
    //init Date object for start time
  }

/* REGEX */
/* FUTURE & PROMISE */
/* EXTENDS */
/* CRUD */
  //SYNCRONOUS
    //Read 
    //Create
    //Update
    //Delete
  //ASYNCRONOUS
    //Read 
    //Create
    //Update
    //Delete



  
  /* REGEX 
    val myRegex = "([\"'])(?:(?=(\\?))\2.)*?\1".r
    var yp = ucollection.find(Document("username" -> "user1")).results().head.get("username").get.getClass //BsonString{value='user1'}
    myRegex.findFirstMatchIn( yp ) match {
        case Some(_) => println("Password OK")
        case None => println("Password must contain a number")
    }
  */


  def testThis(): Unit = {

    // pcollection.insertOne(equal("username", "userX"), set("work_experience_req", Document("job" -> "engineer", "company" -> "nikon")))
    ucollection.updateOne(equal("username", "user1"), set("work_experience_req", Document("job" -> "engineer", "company" -> "nikon"))).printResults()



    // var inputName = readLine("INPUT USERNAME: ")
    // var l = inputName.toString()

    // var query = Document("username"-> inputName.toString())
    // var query = Document("username"-> "user2")
    // var update = Document(BsonString("password")-> "xxxxxx")
    // var update = Document(BsonString("password")-> "xxxxxx")
    //ucollection.updateOne(equal("username", "user2"), set("username", "USERNAMEUPDATED")).printResults()

    //ucollection.find(Document("username" -> "user1")).printResults()
    //ucollection.find(Document("username" -> "user1")).results().getClass
    // println(ucollection.find(Document("username" -> "user1")).results().getClass)
    // println(ucollection.find(Document("username" -> "user1")).results())
      // will need find() or filter() after .head
    
    // println(ucollection.find(Document("username" -> "user1")).results().head.get("username").get.getClass) //BsonString{value='user1'}
    
    // var yp = ucollection.find(Document("username" -> "user1")).results().head.get("username").get.toString.getClass //BsonString{value='user1'}
    //var yp = ucollection.find(Document("username" -> "user1")).results().head.get("username").get.asString() //BsonString{value='user1'}
    // var yp = ucollection.find(Document("username" -> "user1")).results().head.get("username").get.getClass //BsonString{value='user1'}
    //var yl = ucollection.find(Document("username" -> "user1")).results().head.get("key").get.asString().getValue() //TYPE: Bson VALUE: BsonString{value='user1'}
    
    // GOLDEN IT FINALLY WORKS, ALL FINDS NOW CAN WORK SYNCRONOUSLY WITH THE CODE BELOW WITHOUT ASYNC FUTURES AND PROMISES
    var yp = ucollection.find(Document("username" -> "user1")).results().head.get("username").get.asString().getValue() //TYPE: Bson VALUE: BsonString{value='user1'}
    println(yp)
    // val regexQuote = "/('[^']*')/".r //finds values between 'single quotations'
    // var test = "dfbashdfbas'thisrighthere'"
    // var testwhoa = for (yup <- regexQuote.findFirstMatchIn( yp )) yield yup.group(1)
    // println(testwhoa)
    
    //  match {
    //     case Some(_) => println("Password OK")
    //     case None => println("Password must contain a number")
    // }
    
    //println(ucollection.find(Document("username" -> "user1")).results().head.get("username").get)

    println("deadline.")
    // println(ucollection.find(Document("username" -> "user1")).results().head) // Document : 
    //println(ucollection.find(Document("username" -> "user1")).results().head.get("username")) // Some
    
    
  }
      //
    // println(ucollection.find(Document("username" -> "user1")).results().head.find(("username", "user1"))) //user1
      // ^^^^ AHH ITS A DOCUMENT
      // {"key" : value}
      // Document("key1" -> "value1", "")

 

  def logIn(): Unit= {

    ucollection.find().printResults()
    // T is Int, the type of x
    // //ucollection.find().printResults()
    // //var b2 = ucollection.find(in("name" -> "user1")).results()
    // /*
    //  * COMPARING SYNC AND ASYNC
    //  */
    // var b = ucollection.find(Document("username" -> "usersada1")).results()
    // //var bb = b.foreach() // returns an iterable
    // println(b) // returns an iterable

    // // println(ucollection.find().getResults())
    // //var r = ucollection.find().getResults() //"name" : "squiggles"
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
      ucollection.find(Document("username" -> inputName)).subscribe(new Observer[Document](){
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
              ucollection.insertOne(Document("name" -> inputName, "password" -> inputPassword))
              exists = false
              println("IM HEREEEEEEE XXX")
          }
        }
      }
    }
  }  
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
    // var b = ucollection.find(Document("username" -> "user1")).results() //results is an async function (implemented through Await scala.concurrent.Await (blocking) )
    // b.results() // returns a list of iterables (THIS IS GOING TOO FAR FOR SAKE OF VERBAL TIME)
    // foreach() // returns an iterable (THIS IS GOING TOO FAR)

    // // ASYNC (alt + right arrow key to split and compare side by side)
    // var b = ucollection.find(Document("username" -> "user1")).subscribe() //subscribe is an async function (implemented through Future and Promise scala.concurrent.{Future, Promise} (nonblocking))
    // b.subscribe() // returns an list of observer (THIS IS GOING TOO FAR FOR SAKE OF VERBAL TIME)
    