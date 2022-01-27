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

/** OBJECTIVES WITH THIS APP **/
/* CRUD */
    //SYNCRONOUS
      //Read 
      //Create
      //Update
      //Delete
    //ASYNCRONOUS
      //Read 
  /* FUTURE & PROMISE */
  /* REGEX */

object Main extends App {
  println("Main started")

  // Abstract to its own file then import
  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("pzero")
  val ccollection: MongoCollection[Document] = database.getCollection("covidcollection")

  /* Generates 555 usernames and passwords into the collection ccollection */
  //MongoInserts.insertUsernamesAndPasswords(555, ccollection)

  def init() = {
    def menu(): Unit = {
      println("""
        |
        |
        |== MENU == 
        |Select a number to continue
        |0. Exit
        |1. Sync  - C - synchronous create/.insert()
        |2. Sync  - R - synchronous read/.find()    
        |3. Sync  - U - synchronous update/.update()
        |4. Sync  - D - synchronous delete/.delete()
        |5. Async - R - asynchronous read/.find()     
        |""".stripMargin) // stripMargin removes padding from the left side of the console
      var selection = 6 // default value (non 0-5)
      try {
        selection = readLine("Select a number from the menu: ").toInt
      }
      catch {
        case e : Throwable => println("Can't you read, pick a number.")
      }
      finally {
        selection match {
          case 0 => System.exit(0)           // 0 is exit
          case 1 => {syncCreate(); menu()}   // .insert()
          case 2 => {syncRead();   menu()}     // .find()
          case 3 => {syncUpdate(); menu()}   // .update()
          case 4 => {syncDelete(); menu()}   // .delete()
          case 5 => {asyncRead();  menu()}    // .find() but async
          case _ => {println("Invalid number, pick a number 0 through 5."); menu()} // catch all for non numbers 0-5, recursive menu() call to loop 
        }
      }
    }
    menu() 
  }
  init()

  def syncCreate(): Unit = {
    println("1. Sync  - C - synchronous create/.insert() selected - syncCreate() function called.")
    var userinputField1 = readLine("Type the first field's value: ").toString()  // does not handle special characters
    var userinputField2 = readLine("Type the second field's value: ").toString() // does not handle special characters
    ccollection.insertOne(Document("key1" -> userinputField1, "key2" -> userinputField2)).printResults()
    //init Date object for start time
  }

  def syncRead(): Unit = {
    println("2. Sync  - R - synchronous read/.find() selected - syncRead() function called.")
    // Reads all documents from collection
    ccollection.find().printResults()
    // Finds the documents with the value "user1" for the field "username"
    println(ccollection.find(in("username", "user1")).results().head.get("username").get.asString().getValue())
    /** API **
     * ccollection                                                                        //
     * ccollection.find(BSON)                                                             //
     * ccollection.find(BSON).results()                                                   //
     * ccollection.find(BSON).results().head                                              //
     * ccollection.find(BSON).results().head.get("fieldname")                             //
     * ccollection.find(BSON).results().head.get("fieldname").get                         //
     * ccollection.find(BSON).results().head.get("fieldname").get.asString()              //
     * ccollection.find(BSON).results().head.get("fieldname").get.asString().getValue()   // user1
     * 
     ** BSON **
     * import org.mongodb.scala.model._
     * in() , equal() , gt() , lt() , min() , max() , regex() , ...
     * in("fieldname", "value")
     */

    //init Date object for start time
  }

  def syncUpdate(): Unit = {
    println("3. Sync  - U - synchronous update/.update() selected - syncUpdate() function called.")
    
    var userinput = readLine("Type the username to update: ")
    var newUsername = readLine("Type what you want the new username to be: ")
    ccollection.updateOne(equal("username", userinput), set("username", newUsername)).printResults()
  }

  def syncDelete(): Unit = {
    println("4. Sync  - D - synchronous delete/.delete() selected - syncDelete() function called.")

    var userinput = readLine("Type the username to delete: ")
    // Delete the username
    ccollection.deleteOne(equal("username", userinput)).printResults()
  }


  
  /* REGEX 
    val myRegex = "([\"'])(?:(?=(\\?))\2.)*?\1".r
    var yp = ccollection.find(Document("username" -> "user1")).results().head.get("username").get //BsonString{value='user1'}
    myRegex.findFirstMatchIn( yp ) match {
        case Some(_) => println("Password OK")
        case None => println("Password must contain a number")
    }
  */









  def testThis(): Unit = {

    // pcollection.insertOne(equal("username", "userX"), set("work_experience_req", Document("job" -> "engineer", "company" -> "nikon")))
    ccollection.updateOne(equal("username", "user1"), set("work_experience_req", Document("job" -> "engineer", "company" -> "nikon"))).printResults()



    // var inputName = readLine("INPUT USERNAME: ")
    // var l = inputName.toString()

    // var query = Document("username"-> inputName.toString())
    // var query = Document("username"-> "user2")
    // var update = Document(BsonString("password")-> "xxxxxx")
    // var update = Document(BsonString("password")-> "xxxxxx")
    //ccollection.updateOne(equal("username", "user2"), set("username", "USERNAMEUPDATED")).printResults()

    //ccollection.find(Document("username" -> "user1")).printResults()
    //ccollection.find(Document("username" -> "user1")).results().getClass
    // println(ccollection.find(Document("username" -> "user1")).results().getClass)
    // println(ccollection.find(Document("username" -> "user1")).results())
      // will need find() or filter() after .head
    
    // println(ccollection.find(Document("username" -> "user1")).results().head.get("username").get.getClass) //BsonString{value='user1'}
    
    // var yp = ccollection.find(Document("username" -> "user1")).results().head.get("username").get.toString.getClass //BsonString{value='user1'}
    //var yp = ccollection.find(Document("username" -> "user1")).results().head.get("username").get.asString() //BsonString{value='user1'}
    // var yp = ccollection.find(Document("username" -> "user1")).results().head.get("username").get.getClass //BsonString{value='user1'}
    //var yl = ccollection.find(Document("username" -> "user1")).results().head.get("key").get.asString().getValue() //TYPE: Bson VALUE: BsonString{value='user1'}
    
    // GOLDEN IT FINALLY WORKS, ALL FINDS NOW CAN WORK SYNCRONOUSLY WITH THE CODE BELOW WITHOUT ASYNC FUTURES AND PROMISES
    var yp = ccollection.find(Document("username" -> "user1")).results().head.get("username").get.asString().getValue() //TYPE: Bson VALUE: BsonString{value='user1'}
    println(yp)
    // val regexQuote = "/('[^']*')/".r //finds values between 'single quotations'
    // var test = "dfbashdfbas'thisrighthere'"
    // var testwhoa = for (yup <- regexQuote.findFirstMatchIn( yp )) yield yup.group(1)
    // println(testwhoa)
    
    //  match {
    //     case Some(_) => println("Password OK")
    //     case None => println("Password must contain a number")
    // }
    
    //println(ccollection.find(Document("username" -> "user1")).results().head.get("username").get)

    println("deadline.")
    // println(ccollection.find(Document("username" -> "user1")).results().head) // Document : 
    //println(ccollection.find(Document("username" -> "user1")).results().head.get("username")) // Some
    
    
  }
      //
    // println(ccollection.find(Document("username" -> "user1")).results().head.find(("username", "user1"))) //user1
      // ^^^^ AHH ITS A DOCUMENT
      // {"key" : value}
      // Document("key1" -> "value1", "")
   
  def asyncRead(): Unit = {
    println("5. Async - R - asynchronous read/.find() selected - asyncRead() function called.")
    var aPromise = Promise[Boolean]()
    var aFuture = aPromise.future
    var aUsername = "" // scope outside the Future below
    var userinput = readLine("Type the username to find: ")
    ccollection.find(Document("username" -> userinput)).subscribe(new Observer[Document](){
          override def onNext(result: Document) = {
            var aWaiting = true
            aPromise success aWaiting
          }
          override def onError(e: Throwable): Unit = println(s"Error: $e")
          override def onComplete(): Unit = println("Completed")
        })
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
      ccollection.find(Document("username" -> inputName)).subscribe(new Observer[Document](){
        var whatever = "just remember this is capable here"
          override def onNext(result: Document) = {
            println("I AM RUNNING ")
            var w = "this is the value aPromise "
            // var w = () => {
            //   pass = result.pass
            //   user = result.user
            // }
            usernameExists = true
            // p success w
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
              ccollection.insertOne(Document("name" -> inputName, "password" -> inputPassword))
              exists = false
          }
        }
      }
    }
  }  

  /* WHATS NEXT??? Future features to add
   * Inserts a subset of the csv file 'us_counties_covid19_daily.csv' (35 MB) into mongo collection 
   * Small  : us_covid_555_subset.csv
   * Medium : us_covid_5555_subset.csv
   * Large  : us_covid_55555_subset.csv
   */
  def insertCovidData(): Unit = {
  }
}

/*****************************************************************************/
// ASYNC USES A SUBSCRIBER
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
    // var b = ccollection.find(Document("username" -> "user1")).results() //results is an sync function (implemented through Await scala.concurrent.Await (blocking) )
    // b.results() // returns a list of iterables (THIS IS GOING TOO FAR FOR SAKE OF VERBAL TIME)
    // foreach() // returns an iterable (THIS IS GOING TOO FAR)

    // // ASYNC (alt + right arrow key to split and compare side by side)
    // var b = ccollection.find(Document("username" -> "user1")).subscribe() //subscribe is an async function (implemented through Future and Promise scala.concurrent.{Future, Promise} (nonblocking))
    // b.subscribe() // returns an list of observer (THIS IS GOING TOO FAR FOR SAKE OF VERBAL TIME)
    