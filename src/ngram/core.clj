(ns ngram.core
  (:require [ngram.util :as util]))

(defn read-file-by-line
  [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (doall (line-seq rdr))))

(defn tokenize
  [s]
  (clojure.string/split s #"\s+"))

(defn- drop-empty ;; needed?, see metadocs.
  [col]
  (util/drop-if "" col))

(defn tokens-in-col
  [col]
  (flatten (map (partial tokenize) col)))

(defn tokens-in-file
  [file]
  (drop-empty (tokens-in-col (read-file-by-line file))))

(defn ngram
  [n col]
  (partition n 1 col))

(defn ngrams-in-file
  [n file]
  (partition n 1 (tokens-in-file file)))

(defn see-ngrams
  [n s]
  (util/seefreq (ngram n s)))

(defn see-ngrams-in-file
  [n file]
  (see-ngrams n (tokens-in-file file)))

(defn skip1-2gram ;; rename this
  [col]
  (map #(vector (first %) (last %)) (ngram 3 col)))

(defn skip1-2gram-in-file ;; yeah... rename this
  [file]
  (map #(vector (first %) (last %)) (ngrams-in-file 3 file)))
