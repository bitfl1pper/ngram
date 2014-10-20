(ns ngram.core
  (:require [ngram.util :as util]))

;; Draft 1

;; Known issue, since it drops punctuation (cause: bad regex), false word tokens are created
;; i.e. in the canonical text you have: isn't ; this will result in tokens such as isn and t
;; 2 tokens where there should have been one. Appologies for technical debt.

;; overall this is just a sampler, optimization to come

(defn read-file-by-line
  "Read in text file."
  [file]
  (with-open [rdr (clojure.java.io/reader file)]
    (doall (line-seq rdr))))

;; (defn tokens-word
;;   "Simple tokenizer, splits strings on not words.
;;    Accepts a string, e.g. \"This is test.\"
;;    Returns a vector of 'word' tokens, e.g. [\"This\" \"is\" \"test\"]"
;;   [s]
;;   (clojure.string/split s #"\W"))

(defn tokens-word
  "Simple tokenizer.
   Splits strings on whitespace."
  [s]
  (clojure.string/split s #"\s+"))

(defn- drop-empty
  "Helper fn, drop empty strings from a collection of strings.
   Useful when reading large texts line by line, empty strings
   show up.

   See comment section at the bottom of the source of this
   working draft for more info."
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

(defn unigrams?
  "Query a sequence for its unigrams. Returns a sorted list of seqs
   unigrams and the frequency at which they occur."
  [s]
  (util/seefreq (ngram 1 s)))

(defn bigrams?
  "Query a sequence for its bigrams. Returns a sorted list of a seqs
   bigrams and the frequency at which they occur."
  [s]
  (util/seefreq (ngram 2 s)))

(defn trigrams?
  "Query a sequence for its trigrams. Returns a sorted list of a seqs
   trigrams and the frequency at which they occur."
  [s]
  (util/seefreq (ngram 3 s)))

(defn quadgrams?
  "Query a sequence for its quadgrams. Returns a sorted list of a seqs
   quadgrams and the frequency at which they occur."
  [s]
  (util/seefreq (ngram 4 s)))

(defn unigrams?-f
  "Query a text file for its unigrams. Returns a sorted list of a text
   file's bigrams and the frequency at which they occur."
  [file]
  (unigrams? (wtokens-file file)))

(defn bigrams?-f
  "Query a text file for its bigrams. Returns a sorted list of a text
   file's bigrams and the frequency at which they occur."
  [file]
  (bigrams? (wtokens-file file)))

(defn trigrams?-f
  "Query a text file for its trigrams. Returns a sorted list of a text
   file's trigrams and the frequency at which they occur."
  [file]
  (trigrams? (wtokens-file file)))

(defn quadgrams?-f
  "Query a text file for its quadgrams. Returns a sorted list of a text
   file's quadgrams and the frequency at which they occur."
  [file]
  (quadgrams? (wtokens-file file)))



(comment

  ;; META DOCUMENTATION - On the implementation of this code.
  ;;                    - Possibly remove for 'final release'

  ;; *  'drop-empty fn

  ;;      NOTE: this could probably be fixed with better regex, but
  ;;      ill finance this operation with a bit of technical debt in
  ;;      the interest of time.

  ;;      Some of these empty strings are showing up from natural
  ;;      line breaks in the text. Some of the empty strings are showing
  ;;      up as space from after punct. is parse out. Fix later.
)
