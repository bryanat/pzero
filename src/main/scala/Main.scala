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
import scala.io.Source

/** OBJECTIVES FOR THIS P0 CODE **/
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
  /* SHOW MONGO INSERTS */

object Main extends App {
  println("Main started")

  // Abstract to its own file then import
  val mongoClient: MongoClient = MongoClient()
  val database: MongoDatabase = mongoClient.getDatabase("pzero")
  val ccollection: MongoCollection[Document] = database.getCollection("covidcollection")

  /* Generates 555 usernames and passwords into the collection ccollection */
  // MongoInserts.insertUsernamesAndPasswords(555, ccollection)

  def init() = {
    def menu(): Unit = {
      println("""
        |
        |
        |== MENU == 
        |Select a number to manage user accounts
        |0. Exit
        |1. Sync  - C - synchronous create/.insert()
        |2. Sync  - R - synchronous read/.find() all    
        |3. Sync  - U - synchronous update/.update()
        |4. Sync  - D - synchronous delete/.delete()
        |5. Sync  - R - synchronous read/.find() key      
        |6. Async - R - asynchronous read/.find()
        |7. Login
        |""".stripMargin) // stripMargin removes padding from the left side of the console
      var selection = 9 // default value (non 0-5)
      try {
        selection = readLine("Select a number from the menu: ").toInt
      }
      catch {
        case e : Throwable => println("Pick again from the menu.")
      }
      finally {
        selection match {
          case 0 => System.exit(0)            // 0 is exit
          case 1 => {syncCreate(); menu()}    // .insert()
          case 2 => {syncRead();   menu()}    // .find()
          case 3 => {syncUpdate(); menu()}    // .update()
          case 4 => {syncDelete(); menu()}    // .delete()
          case 5 => {syncReadKey();  menu()}  // .find() key value
          case 6 => {asyncRead();  menu()}    // .find() but async
          case 7 => {login();}                //
          case _ => {println("Invalid number, pick a number 0 through 7."); menu()} // catch all for non numbers 0-7, recursive menu() call to loop 
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
    //var userinputmenu = readLine("Press any key to return to the menu")
  }

  def syncRead(): Unit = {
    println("2. Sync  - R - synchronous read/.find() selected - syncRead() function called.")
    // Reads all documents from collection
    ccollection.find().printResults()
    // Finds the documents with the value "user1" for the field "username"
    var talkaboutAPI = ccollection.find(in("username", "user1")).results().head.get("username").get.asString().getValue()
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

  def syncReadKey(): Unit = {
    println("5. Sync  - R - synchronous read/.find() key - syncReadKey() function called.")
    var userinput = readLine("Type the username whose password you want to search for: ").toString()
    // Finds the documents with the value "user1" for the field "username"
    println("The password for "+userinput+" is: " + ccollection.find(in("username", userinput)).results().head.get("password").get.asString().getValue())
  }

  def asyncRead(): Unit = {
    println("6. Async - R - asynchronous read/.find() selected - asyncRead() function called.")
    var aPromise = Promise[String]()
    var aFuture = aPromise.future
    var aPassword = "" // scope outside the Future below
    var userinput = readLine("Type the username whose password you want to find: ")
    ccollection.find(in("username", userinput)).subscribe(new Observer[Document](){
          override def onNext(result: Document) = {
            // aPassword = result.head.get("password").get.asString().getValue()
            //println(result.get("password").get.asString().getValue())
            aPassword = result.get("password").get.asString().getValue()
            aPromise success aPassword
          }
          override def onError(e: Throwable): Unit = println(s"Error: $e")
          override def onComplete(): Unit = println("Promise completed and password value has been returned. Passwords match.")
    })
    val consumerUsernameExists = Future {
        aFuture foreach { i =>
          // if already exists, ask again
          println("The users password is: " + i)
        }
      }
    }

  def login(): Unit = {
    println("7. Login - login() function called.")
    var userinputUsername = readLine("Enter username: ")
    var userinputPassword = readLine("Enter password: ")
    // if (userinputUsername == ccollection.find(in("username", userinputUsername)).results().head.get("username").get.asString().getValue() 
    // println(ccollection.find(in("username", userinputUsername)).results().head.get("password").get.asString().getValue())
    if (userinputUsername == ccollection.find(in("username", userinputUsername)).results().head.get("username").get.asString().getValue() 
        && userinputPassword == ccollection.find(in("username", userinputUsername)).results().head.get("password").get.asString().getValue()) {
          println("LOGGED IN")
          loggedinMenu();
        }
    else {
      println("Incorrect password for the entered username, try again.")
      login();
    }
  }
   
  def loggedinMenu(): Unit = {
    def subMenu(): Unit = {
      println("""
                |
                |
                |== MENUUUUUUUUUU == 
                |Select a number to continue
                |0. Exit
                |1. Stream Covid data (small)
                |2. Stream Covid data (medium)
                |3. Stream Covid data (large)
                |""".stripMargin) // stripMargin removes padding from the left side of the console
      var selection = 9 // default value (non 0-5)
      try {
        selection = readLine("Select a number from the menu: ").toInt
      }
      catch {
        case e : Throwable => println("Pick again from the menu.")
      }
      finally {
        selection match {
          case 0 => System.exit(0)            // 0 is exit
          case 1 => {streamCovid("small"); subMenu()}   
          case 2 => {streamCovid("medium");   subMenu()}  
          case 3 => {streamCovid("large"); subMenu()}  
          case 4 => {importCovid();}
          case _ => {println("Invalid number, pick a number 0 through 3."); subMenu()} // catch all for non numbers 0-3, recursive menu() call to loop 
        }
      }
    }
    subMenu() 
  }

  /*
   * IN THE FUTURE COULD REPLACE THE PRINTLN IN streamCovid WITH DATA STREAMS
   */
  def streamCovid(size: String): Unit = {
    var filename = ""
    size match 
      {
        case "small" => filename = "C:/revenant/pzero/src/resources/us_covid_555_subset.csv"
        case "medium" => filename = "C:/revenant/pzero/src/resources/us_covid_5555_subset.csv"
        case "large" => filename = "C:/revenant/pzero/src/resources/us_covid_55555_subset.csv"
      }
    for (line <- Source.fromFile(filename).getLines) { println(line)}
  }
}

  /* REGEX 
    val myRegex = "([\"'])(?:(?=(\\?))\2.)*?\1".r
    var yp = ccollection.find(Document("username" -> "user1")).results().head.get("username").get //BsonString{value='user1'}
    myRegex.findFirstMatchIn( yp ) match {
        case Some(_) => println("Password OK")
        case None => println("Password must contain a number")
    }
  */

  // def syncReadPretty(): Unit = {
  //   println("2. Sync  - R - synchronous read/.find() selected - syncRead() function called.")
  //   // Reads all documents from collection
  //   ccollection.find().printResults()
  //   // Finds the documents with the value "user1" for the field "username"
  //   var talkaboutAPI = ccollection.find(in("username", "user1")).results().head.get("username").get.asString().getValue()
  //   /** API **
  //    * ccollection                                                                        //
  //    * ccollection.find(BSON)                                                             //
  //    * ccollection.find(BSON).results()                                                   //
  //    * ccollection.find(BSON).results().head                                              //
  //    * ccollection.find(BSON).results().head.get("fieldname")                             //
  //    * ccollection.find(BSON).results().head.get("fieldname").get                         //
  //    * ccollection.find(BSON).results().head.get("fieldname").get.asString()              //
  //    * ccollection.find(BSON).results().head.get("fieldname").get.asString().getValue()   //
  //    * 
  //    ** BSON **
  //    * import org.mongodb.scala.model._
  //    * in() , equal() , gt() , lt() , min() , max() , regex() , ...
  //    * in("fieldname", "value")
  //    */

  //   //init Date object for start time
  // }

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
// foreach() // returns an iterable (THIS IS GOING TOO FAR
// // ASYNC (alt + right arrow key to split and compare side by side)
// var b = ccollection.find(Document("username" -> "user1")).subscribe() //subscribe is an async function (implemented through Future and Promise scala.concurrent.{Future, Promise} (nonblocking))
// b.subscribe() // returns an list of observer (THIS IS GOING TOO FAR FOR SAKE OF VERBAL TIME)
    