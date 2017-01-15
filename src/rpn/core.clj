(ns rpn.core)

(defn calculator [expression]
  (let [n (read-string expression)]
    (if (number? n)
      (str n)
      (throw (Exception. "Unable to parse input")))))
