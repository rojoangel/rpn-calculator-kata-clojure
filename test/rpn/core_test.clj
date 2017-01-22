(ns rpn.core-test
  (:use midje.sweet)
  (:use [rpn.core])
  (:import (clojure.lang ExceptionInfo)))

(facts "About reverse polish notation calculator"

       (facts "In absence of errors computes as expected"
              (fact "When a digit is sent it should display the same digit"
                    (calculator "1") => "1"
                    (calculator "99") => "99")
              (fact "When some digits are sent it should display the number formed by those digits"
                    (calculator "1 2") => "1 2"
                    (calculator " 1 2 ") => "1 2")
              (fact "When a + operation is sent after two numbers it should display the result"
                    (calculator "1 2 +") => "3"
                    (calculator "1 2 + 3 +") => "6"
                    (calculator "1 2 + 3 + 4 +") => "10")
              (fact "When a - operation is sent after two numbers it should display the result"
                    (calculator "2 1 -") => "1"
                    (calculator "9 2 - 1 -") => "6"
                    (calculator "8 1 - 4 - 1 -") => "2")
              (fact "When a * operation is sent after two numbers it should display the result"
                    (calculator "3 2 *") => "6"
                    (calculator "9 2 * 3 *") => "54"
                    (calculator "8 1 * 4 * 3 *") => "96")
              (fact "When a / operation is sent after two numbers it should display the result"
                    (calculator "8 2 /") => "4"
                    (calculator "8 2 / 2 /") => "2"
                    (calculator "8 2 / 2 / 2 /") => "1")
              (fact "Works for the examples"
                    (calculator "20 5 /") => "4"
                    (calculator "4 2 + 3 -") => "3"
                    (calculator "3 5 8 * 7 + *") => "141"
                    (calculator "7 2 - 3 4") => "5 3 4"))

       (facts "In the presence of an error reports the error adequately"
              (fact "When an alpha character is sent it throws an error"
                    (calculator "a") => (throws ExceptionInfo "Unable to parse input"
                                                #(and (= :parse-error (:error-type (ex-data %)))
                                                      (= "a" (:input (ex-data %)))))
                    (calculator "Z") => (throws ExceptionInfo "Unable to parse input"
                                                #(and (= :parse-error (:error-type (ex-data %)))
                                                      (= "Z" (:input (ex-data %)))))
                    (calculator "P") => (throws ExceptionInfo "Unable to parse input"
                                                #(and (= :parse-error (:error-type (ex-data %)))
                                                      (= "P" (:input (ex-data %))))))
              (fact "When the expression constains an alpha it throws an error"
                    (calculator "1 a") => (throws ExceptionInfo "Unable to parse input"
                                                  #(and (= :parse-error (:error-type (ex-data %)))
                                                        (= "a" (:input (ex-data %)))))
                    (calculator "a 1") => (throws ExceptionInfo "Unable to parse input"
                                                  #(and (= :parse-error (:error-type (ex-data %)))
                                                        (= "a" (:input (ex-data %)))))
                    (calculator " 1 a ") => (throws ExceptionInfo "Unable to parse input"
                                                    #(and (= :parse-error (:error-type (ex-data %)))
                                                          (= "a" (:input (ex-data %))))))
              (fact "When a + operation is not sent after two numbers it throws an error"
                    (calculator "+") => (throws ExceptionInfo "Unexpected operator"
                                                #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                      (= + (:operator (ex-data %)))
                                                      (= 0 (:stack-size (ex-data %)))))
                    (calculator "2 +") => (throws ExceptionInfo "Unexpected operator"
                                                  #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                        (= + (:operator (ex-data %)))
                                                        (= 1 (:stack-size (ex-data %)))))
                    (calculator "1 + 2") => (throws ExceptionInfo "Unexpected operator"
                                                    #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                          (= + (:operator (ex-data %)))
                                                          (= 1 (:stack-size (ex-data %))))))
              (fact "When a - operation is not sent after two numbers it throws an error"
                    (calculator "-") => (throws ExceptionInfo "Unexpected operator"
                                                #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                      (= - (:operator (ex-data %)))
                                                      (= 0 (:stack-size (ex-data %)))))
                    (calculator "2 -") => (throws ExceptionInfo "Unexpected operator"
                                                  #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                        (= - (:operator (ex-data %)))
                                                        (= 1 (:stack-size (ex-data %)))))
                    (calculator "2 - 1") => (throws ExceptionInfo "Unexpected operator"
                                                    #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                          (= - (:operator (ex-data %)))
                                                          (= 1 (:stack-size (ex-data %))))))
              (fact "When a * operation is not sent after two numbers it throws an error"
                    (calculator "*") => (throws ExceptionInfo "Unexpected operator"
                                                #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                      (= * (:operator (ex-data %)))
                                                      (= 0 (:stack-size (ex-data %)))))
                    (calculator "2 *") => (throws ExceptionInfo "Unexpected operator"
                                                  #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                        (= * (:operator (ex-data %)))
                                                        (= 1 (:stack-size (ex-data %)))))
                    (calculator "2 * 1") => (throws ExceptionInfo "Unexpected operator"
                                                    #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                          (= * (:operator (ex-data %)))
                                                          (= 1 (:stack-size (ex-data %))))))
              (fact "When a / operation is not sent after two numbers it throws an error"
                    (calculator "/") => (throws ExceptionInfo "Unexpected operator"
                                                #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                      (= / (:operator (ex-data %)))
                                                      (= 0 (:stack-size (ex-data %)))))
                    (calculator "2 /") => (throws ExceptionInfo "Unexpected operator"
                                                  #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                        (= / (:operator (ex-data %)))
                                                        (= 1 (:stack-size (ex-data %)))))
                    (calculator "2 / 1") => (throws ExceptionInfo "Unexpected operator"
                                                    #(and (= :unexpected-operator (:error-type (ex-data %)))
                                                          (= / (:operator (ex-data %)))
                                                          (= 1 (:stack-size (ex-data %))))))))
