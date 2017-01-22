(ns rpn.core
  (:require [clojure.string :as str]))

(def operations {'+ +
                 '- -
                 '* *
                 '/ /})

(defn- to-symbols [expression]
  (let [tokens (str/split (str/trim expression) #"\s+")]
    (map #(read-string %) tokens)))

(defn- process-number [number stack]
  (cons number stack))

(defn- process-operation [operation stack]
  (if (>= (count stack) 2)
    (cons (operation (first (rest stack)) (first stack)) (drop 2 stack))
    (throw (Exception. "Unexpected operator"))))

(defn- process-token [stack token]
  (if (number? token)
    (process-number token stack)
    (if-let [operation (token operations)]
      (process-operation operation stack)
      (throw (Exception. "Unable to parse input")))))

(defn calculator [expression]
  (let [tokens (to-symbols expression)]
    (str/join " " (reverse (reduce process-token nil tokens)))))
