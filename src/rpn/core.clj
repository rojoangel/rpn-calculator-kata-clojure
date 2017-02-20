(ns rpn.core
  (:require [clojure.string :as str]))

(def operations {'+ +
                 '- -
                 '* *
                 '/ /})

(defn- filter-out-unsupported-symbols [symbols]
  (filter #(or (number? %) (% operations)) symbols))

(defn- validate-symbols [symbols]
  {:pre [(= symbols (filter-out-unsupported-symbols symbols))]}
  symbols)

(defn- to-symbols [expression]
  (let [tokens (str/split (str/trim expression) #"\s+")
        symbols (map #(read-string %) tokens)]
    (validate-symbols symbols)))

(defn- process-number [number stack]
  (cons number stack))

(defn- process-operation [operation stack]
  {:pre [(>= (count stack) 2)]}
  (cons (operation (first (rest stack)) (first stack)) (drop 2 stack)))

(defn- process-token [stack token]
  (if (number? token)
    (process-number token stack)
    (when-let [operation (token operations)]
      (process-operation operation stack))))

(defn calculator [expression]
  (let [tokens (to-symbols expression)]
    (str/join " " (reverse (reduce process-token nil tokens)))))
