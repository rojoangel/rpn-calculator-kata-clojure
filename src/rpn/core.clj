(ns rpn.core
  (:require [clojure.string :as str]))

(defn- parse-token [token]
  (let [object (read-string token)]
    (if (number? object)
      (str object)
      (throw (Exception. "Unable to parse input")))))

(defn calculator [expression]
  (let [tokens (str/split (str/trim expression) #"\s+")]
    (str/join " " (map parse-token tokens))))
