package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }
  


  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  
  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  
  //////////////////////////////////////
  
    test("singletonSet(2) contains 2") {
    new TestSets {
      assert(contains(s2, 2), "Singleton 2")
    }
  }

  test("singletonSet(3) contains 3") {
    new TestSets {
      assert(contains(s3, 3), "Singleton 3")
    }
  }


  test("intersect contains elements that are in both sets") {
    new TestSets {
      val s = intersect(union(s1, s2), s2)
      assert(!contains(s, 1), "Intersect 1")
      assert(contains(s, 2), "Intersect 2")
      assert(!contains(s, 3), "Intersect 3")
    }
  }

  test("difference between sets") {
    new TestSets {
      val s = diff(union(s1, s2), union(s2, s3))
      assert(contains(s, 1))
      assert(!contains(s, 2))
      assert(!contains(s, 3))
    }
  }

  test("filtering a set") {
    new TestSets {
      val test1 = filter(union(s1, s2), (x: Int) => x % 2 != 0)
      assert(contains(test1, 1))
      assert(!contains(test1, 2))

      val test2 = filter(union(s1, s3), (x: Int) => x % 2 != 0)
      assert(contains(test2, 1))
      assert(contains(test2, 3))
    }
  }

  test("forall across positive numbers") {
    new TestSets {
      val posInts = (x: Int) => x > 0

      assert(forall(posInts, (x: Int) => true))
      assert(!forall(posInts, (x: Int) => x % 2 == 0))
    }
  }

  test("exists shows that at least one object satisfies") {
    val posInts = (x: Int) => x > 0
    val negInts = (x: Int) => x < 0

    assert(exists(posInts, (x: Int) => x == 1))
    assert(exists(posInts, (x: Int) => true))

    assert(!exists(negInts, (x: Int) => x == 1))
    assert(exists(negInts, (x: Int) => true))
  }

  test("map applies each element to the function") {
    val s = singletonSet(1)
    val manyS = (x: Int) => x > 0 && x <= 10

    assert(contains(map(s, x => x + 1), 2))

    val manySMap = map(manyS, x => x + 1)
    for( x <- 2 to 11) {
      assert(contains(manySMap, x))
    }
    assert(!contains(manySMap, 1))
    assert(!contains(manySMap, 12))
  }
  
  
  
  
}
