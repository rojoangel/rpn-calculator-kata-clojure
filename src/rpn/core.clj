(ns rpn.core
  (:require [clojure.string :as str]))

(def operations {(symbol "+") (resolve (symbol "+"))
                 (symbol "-") (resolve (symbol "-"))})

(defn- process-token [stack token]
  (let [object (read-string token)]
    (if (number? object)
      (cons object stack)
      (if-let [operation (object operations)]
        (if (>= (count stack) 2)
          (cons (operation (first (rest stack)) (first stack)) (drop 2 stack))
          (throw (Exception. "Unexpected operator")))
        (throw (Exception. "Unable to parse input"))))))

(defn calculator [expression]
  (let [tokens (str/split (str/trim expression) #"\s+")]
    (str/join " " (reverse (reduce process-token nil tokens)))))
