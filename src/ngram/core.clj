(ns ngram.core
  (:require [ngram.util :as util]))


(defn read-file-by-line
  "Read in text file."
  [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (doall (line-seq rdr))))

(defn tokens-word
  "Simple tokenizer.
   Splits strings on whitespace."
  [s]
  (clojure.string/split s #"\s+"))

(defn- drop-empty
  "Helper fn, drop empty strings from a collection of strings."
  [col]
  (util/drop-if "" col))

(defn word-tokens-in-col
  "Word tokens in each string in a collection of strings."
  [col]
  (flatten (map (partial tokens-word) col)))

(defn wtokens-file
  "Get the word tokens from a file.
   Read file line by line, generate seq of strings.
   Naive word tokenizer is mapped to this collection.
   The collection is flattened into one large collection of word tokens."
  [file]
  (drop-empty (word-tokens-in-col (read-file-by-line file))))

(defn ngram
  "Takes a collection of (sequential) tokens and an integer, n.
   Returns all *n*grams of the collection of tokens."
  [n col]
  (partition n 1 col))

(defn ngrams?
  "Query a sequence for its n-grams.

   Accepts n, an integer, denoting the value of n, for the desired
   length of the n-gram. e.g. n = 2 for bigram, n = 3 for trigram.

   Accepts s, a sequence, denoting the sequential collection of
   tokens from which n-grams will be extracted.

   Returns a sorted list of the n-grams of s. The list is a collection
   of vectors, each vector contains an n-gram and the frequency at
   which it occurs. The list is sorted by frequency."
  [n s]
  (util/seefreq (ngram n s)))

(defn ngrams?-wt-f    ;; rename this
  "Query a text file for its [\"word token\"] n-grams.

   Accepts n, an integer, denoting the value of n, for the desired
   length of the n-gram. e.g. n = 2 for bigram, n = 3 for trigram.

   Accepts file, a textfile, denoting the text file that will be
   tokenized by [\"word tokens\"] from which n-grams will be extracted.

   Returns a sorted list of the n-grams of s. The list is a collection
   of vectors, each vector contains an n-gram and the frequency at
   which it occurs. The list is sorted by frequency."
  [n file]
  (ngrams? n (wtokens-file file)))



(comment

  ;; META DOCUMENTATION - On the implementation of this code.
  ;;                    - Possibly remove for 'final release'

  ;; Draft 1

  ;; Appologies for technical debt.

  ;; overall this is just a sampler, optimization to come

  ;;;;;;;;;;;;;;;;;;;;; fn notes ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

  ;; *  'drop-empty fn

  ;;      NOTE: this could probably be fixed with better regex, but
  ;;      ill finance this operation with a bit of technical debt in
  ;;      the interest of time.

  ;;      Some of these empty strings are showing up from natural
  ;;      line breaks in the text. Some of the empty strings are showing
  ;;      up as space from after punct. is parse out. Fix later.
)
