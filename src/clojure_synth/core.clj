(ns clojure-synth.core
  (:require [clojure-synth.audio-formats :as audio-formats]
            [clojure-synth.oscillators :as oscillators]
            [clojure-synth.player :as player]
            [clojure-synth.sample-melodies :as sample-melodies]))

(def af audio-formats/default-format)
(def sine oscillators/sine)
(def saw oscillators/saw)
(def square oscillators/square)
(def samo-mi-kazi sample-melodies/samo-mi-kazi)
(def rondo-alla-turca sample-melodies/rondo-alla-turca)

(def play-melody player/play-melody)
;; (play-melody audio-format oscillator melody bpm)
;; (play-melody af sine rondo-alla-turca 260)
;; (play-melody af sine samo-mi-kazi 165)