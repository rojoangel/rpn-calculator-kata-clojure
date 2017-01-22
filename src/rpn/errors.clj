(ns rpn.errors
  (:require [rpn.core :refer :all]
            [dire.core :refer [with-postcondition! with-handler!]]))

(defn- valid? [symbols]
  (empty? (remove #(% operations) (remove number? symbols))))

(with-postcondition!
  #'to-symbols
  :valid-symbols
  (fn [symbols & _]
    (valid? symbols)))

(with-handler!
  #'to-symbols
  {:postcondition :valid-symbols}
  (fn [e & args]
    (throw (Exception. "Unable to parse input"))))
