package recfun

import scala.collection.mutable.ListBuffer
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
	  
 		// If c = 0 then row must be equal to 1
 		if (c == 0 || c == r) 1
	    
 		// Else -1 and add Pascal function again recursively
 		else pascal(c - 1, r - 1) + pascal(c, r - 1)
    }

  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
	  
	  	// Parentheses finder function
	    def find_chars(chars: List[Char], numberOfOpen: Int): Boolean = {
	      
	      // If supplied list is empty then number of open paranthesis equals 0
	      if (chars.isEmpty) {
	        numberOfOpen == 0
	      } else {
	      
	      	// Assign list head
	        val head = chars.head
	        
	        // Get n recursively
	        val n =
	          if (head == '(') numberOfOpen + 1
	          else if (head == ')') numberOfOpen - 1
	          else numberOfOpen
	        if (n >= 0) find_chars(chars.tail, n)
	        else false
	      }
	    }
	 
	    find_chars(chars, 0)
  }

  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
    def finder(lastMaxCoin_total_coll: List[(Int, Int)], count: Int): Int = {
      if (lastMaxCoin_total_coll.isEmpty) {
        count
      } else {
        val b = ListBuffer[(Int, Int)]()
        var newCount = count
        for ((lastMaxCoin, total) <- lastMaxCoin_total_coll) {
          if (total < money) {
            for (c <- coins) {
              if (c >= lastMaxCoin) {
                val e = (c, total + c)
                b += e
              }
            }
          } else if (total == money) {
            newCount += 1
          }
        }
 
        finder(b.toList, newCount)
      }
    }
 
    val b = coins.map { c => (c, c) }
    finder(b, 0)
  }
}
