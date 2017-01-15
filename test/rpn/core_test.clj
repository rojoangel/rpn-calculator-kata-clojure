(ns rpn.core-test
  (:use midje.sweet)
  (:use [rpn.core]))

(facts "About reverse polish notation calculator"
  (fact "When a digit is sent it should display the same digit"
    (calculator "1") => "1"
    (calculator "99") => "99"))
