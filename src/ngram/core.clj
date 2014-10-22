(ns ngram.core
  (:require [ngram.util :as util]))

(defn read-file-by-line
  [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (doall (line-seq rdr))))

(defn tokens-word
  [s]
  (clojure.string/split s #"\s+"))

(defn- drop-empty ;; needed?, see metadocs.
  [col]
  (util/drop-if "" col))

(defn word-tokens-in-col
  [col]
  (flatten (map (partial tokens-word) col)))

(defn wtokens-file
  [file]
  (drop-empty (word-tokens-in-col (read-file-by-line file))))

(defn ngram
  [n col]
  (partition n 1 col))

(defn ngram-f
  [n file]
  (partition n 1 (wtokens-file file)))

(defn ngrams?
  [n s]
  (util/seefreq (ngram n s)))

(defn ngrams?-wt-f    ;; rename this
  [n file]
  (ngrams? n (wtokens-file file)))

(defn skip1-2gram
  [col]
  (map #(vector (first %) (last %)) (ngram 3 col)))

(defn skip1-2gram-wt-f ;; yeah... rename this
  [file]
  (map #(vector (first %) (last %)) (ngram-f 3 file)))
