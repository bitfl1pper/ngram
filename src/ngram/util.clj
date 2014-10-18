(ns ngram.util)

(defn drop-if
  "Drops values from a collection if they are equal to "
  [val col]
  (filter #(not= val %) col))
