(ns rpn.api-test
  (:use midje.sweet)
  (:use [rpn.core] [rpn.api]))

(facts "About reverse polish notation calculator error conditions"
       (fact "When an alpha character is sent it throws an error"
             (calculator "a") => (throws Exception "Unable to parse input")
             (calculator "Z") => (throws Exception "Unable to parse input")
             (calculator "P") => (throws Exception "Unable to parse input"))
       (fact "When the expression constains an alpha it throws an error"
             (calculator "1 a") => (throws Exception "Unable to parse input")
             (calculator "a 1") => (throws Exception "Unable to parse input")
             (calculator " 1 a ") => (throws Exception "Unable to parse input"))
       (fact "When a + operation is not sent after two numbers it throws an error"
             (calculator "+") => (throws Exception "Unexpected operator")
             (calculator "2 +") => (throws Exception "Unexpected operator")
             (calculator "1 + 2") => (throws Exception "Unexpected operator"))
       (fact "When a - operation is not sent after two numbers it throws an error"
             (calculator "-") => (throws Exception "Unexpected operator")
             (calculator "2 -") => (throws Exception "Unexpected operator")
             (calculator "2 - 1") => (throws Exception "Unexpected operator"))
       (fact "When a * operation is not sent after two numbers it throws an error"
             (calculator "*") => (throws Exception "Unexpected operator")
             (calculator "2 *") => (throws Exception "Unexpected operator")
             (calculator "2 * 1") => (throws Exception "Unexpected operator"))
       (fact "When a / operation is not sent after two numbers it throws an error"
             (calculator "/") => (throws Exception "Unexpected operator")
             (calculator "2 /") => (throws Exception "Unexpected operator")
             (calculator "2 / 1") => (throws Exception "Unexpected operator")))
