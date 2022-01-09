(ns clojure-synth.frequency-helpers
  (:require
   [clojure.string :as str]))

(def available-notes {:C 0
                      :Db 1
                      :D 2
                      :Eb 3
                      :E 4
                      :F 5
                      :Gb 6
                      :G 7
                      :Ab 8
                      :A 9
                      :Bb 10
                      :B 11})

(def available-octaves (range 9)) 

(def base-frequency {:note "A" :octave 4 :frequency 440}) ;; Base note A4 = 440Hz

(def ratio (Math/pow 2 (/ 1 12))) ;; Equal temperament

(defn pitch-splitter [pitch]
  (if (= (count pitch) 3) 
    (re-seq #".{1,2}" pitch)
    (str/split pitch #"")))

(defn pitch-validator [pitch]
  (let [[note octave] (pitch-splitter pitch)]
    (if (and (contains? available-notes (keyword note)) (every? #(Character/isDigit %) octave) (some #{(Integer/parseInt octave)} available-octaves))
      true
      false)))

(defn find-frequency [pitch]
  (let [[note octave] (pitch-splitter pitch)
        current-note (get available-notes (keyword note))
        base-note (get available-notes (keyword (get base-frequency :note)))
        base-octave (get base-frequency :octave)
        note-diff (- current-note base-note)
        octave-diff (- (Long/parseLong octave) base-octave)
        pitch-diff (+ (* octave-diff 12) note-diff)
        operation (if (< pitch-diff 0) / *)]
    (operation (get base-frequency :frequency) (Math/pow ratio (Math/abs pitch-diff)))))
