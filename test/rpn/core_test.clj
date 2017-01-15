(ns rpn.core-test
  (:use midje.sweet)
  (:use [rpn.core]))

(facts "About reverse polish notation calculator"
       (fact "When a digit is sent it should display the same digit"
             (calculator "1") => "1"
             (calculator "99") => "99")
       (fact "When an alpha character is sent it throws an error"
             (calculator "a") => (throws Exception)
             (calculator "Z") => (throws Exception)
             (calculator "+") => (throws Exception))
       (fact "When some digits are sent it should display the number formed by those digits"
             (calculator "1 2") => "1 2"
             (calculator " 1 2 ") => "1 2")
       (fact "When the expression constains an alpha it throws an error"
             (calculator "1 a") => (throws Exception)
             (calculator "a 1") => (throws Exception)
             (calculator " 1 a ") => (throws Exception))
       (fact "When a + operation is sent after two numbers it should display the result"
             (calculator "1 2 +") => "3"
             (calculator "1 2 + 3 +") => "6"
             (calculator "1 2 + 3 + 4 +") => "10")
       (fact "When a + operation is not sent after two numbers it throws an error"
             (calculator "+") => (throws Exception "Unexpected operator")
             (calculator "2 +") => (throws Exception "Unexpected operator")
             (calculator "1 + 2") => (throws Exception "Unexpected operator")))
