(ns rpn.core
  (:require [clojure.string :as str]))

(def operations {'+ +
                 '- -
                 '* *
                 '/ /})

(defn- process-number [number stack]
  (cons number stack))

(defn- process-operation [operation stack]
  (if (>= (count stack) 2)
    (cons (operation (first (rest stack)) (first stack)) (drop 2 stack))
    (throw
      (ex-info "Unexpected operator"
               {:error-type :unexpected-operator :operator operation :stack-size (count stack)}))))

(defn- process-token [stack token]
  (let [object (read-string token)]
    (if (number? object)
      (process-number object stack)
      (if-let [operation (object operations)]
        (process-operation operation stack)
        (throw
          (ex-info "Unable to parse input"
                   {:error-type :parse-error :input token}))))))

(defn calculator [expression]
  (let [tokens (str/split (str/trim expression) #"\s+")]
    (str/join " " (reverse (reduce process-token nil tokens)))))
