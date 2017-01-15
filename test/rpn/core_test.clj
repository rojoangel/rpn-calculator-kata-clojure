(ns rpn.core-test
  (:use midje.sweet)
  (:use [rpn.core]))

(facts "About reverse polish notation calculator"
       (fact "When a digit is sent it should display the same digit"
             (calculator "1") => "1"
             (calculator "99") => "99")
       (fact "When an alpha character is sent it throws an error"
             (calculator "a") => (throws Exception "Unable to parse input")
             (calculator "Z") => (throws Exception "Unable to parse input")
             (calculator "P") => (throws Exception "Unable to parse input"))
       (fact "When some digits are sent it should display the number formed by those digits"
             (calculator "1 2") => "1 2"
             (calculator " 1 2 ") => "1 2")
       (fact "When the expression constains an alpha it throws an error"
             (calculator "1 a") => (throws Exception "Unable to parse input")
             (calculator "a 1") => (throws Exception "Unable to parse input")
             (calculator " 1 a ") => (throws Exception "Unable to parse input"))
       (fact "When a + operation is sent after two numbers it should display the result"
             (calculator "1 2 +") => "3"
             (calculator "1 2 + 3 +") => "6"
             (calculator "1 2 + 3 + 4 +") => "10")
       (fact "When a + operation is not sent after two numbers it throws an error"
             (calculator "+") => (throws Exception "Unexpected operator")
             (calculator "2 +") => (throws Exception "Unexpected operator")
             (calculator "1 + 2") => (throws Exception "Unexpected operator"))
       (fact "When a - operation is sent after two numbers it should display the result"
             (calculator "2 1 -") => "1"
             (calculator "9 2 - 1 -") => "6"
             (calculator "8 1 - 4 - 1 -") => "2")
       (fact "When a - operation is not sent after two numbers it throws an error"
             (calculator "-") => (throws Exception "Unexpected operator")
             (calculator "2 -") => (throws Exception "Unexpected operator")
             (calculator "2 - 1") => (throws Exception "Unexpected operator"))
       (fact "When a * operation is sent after two numbers it should display the result"
             (calculator "3 2 *") => "6"
             (calculator "9 2 * 3 *") => "54"
             (calculator "8 1 * 4 * 3 *") => "96")
       (fact "When a - operation is not sent after two numbers it throws an error"
             (calculator "*") => (throws Exception "Unexpected operator")
             (calculator "2 *") => (throws Exception "Unexpected operator")
             (calculator "2 * 1") => (throws Exception "Unexpected operator"))
       (fact "When a / operation is sent after two numbers it should display the result"
             (calculator "8 2 /") => "4"
             (calculator "8 2 / 2 /") => "2"
             (calculator "8 2 / 2 / 2 /") => "1")
       (fact "When a / operation is not sent after two numbers it throws an error"
             (calculator "/") => (throws Exception "Unexpected operator")
             (calculator "2 /") => (throws Exception "Unexpected operator")
             (calculator "2 / 1") => (throws Exception "Unexpected operator"))
       (fact "Works for the examples"
             (calculator "20 5 /") => "4"
             (calculator "4 2 + 3 -") => "3"
             (calculator "3 5 8 * 7 + *") => "141"
             (calculator "7 2 - 3 4") => "5 3 4"))
