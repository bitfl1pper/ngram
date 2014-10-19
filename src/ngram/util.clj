(ns ngram.util)

(defn drop-if
  "Drops values from a collection if they are equal to "
  [val col]
  (filter #(not= val %) col))

(defn seefreq
  "Takes the frequencies of items in a collection, sorts
   the resulting collection by #, and reverses the list."
  [col]
  (reverse (sort-by val (frequencies col))))
