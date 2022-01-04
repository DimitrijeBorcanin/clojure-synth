(ns clojure-synth.player
  (:require [clojure-synth.frequency-helpers :as frequency-helpers]
             [clojure-synth.duration-helpers :as duration-helpers]))

(import '(javax.sound.sampled AudioSystem DataLine$Info SourceDataLine))

(defn open-line [audio-format]
  (let [srcDataLine ^SourceDataLine (AudioSystem/getLine (DataLine$Info. SourceDataLine audio-format))]
    (doto srcDataLine
      (.open audio-format)
      (.start))))

(defn convert-note [bpm note]
  (let [pitch (get note 0)
        conv-pitch (frequency-helpers/find-frequency pitch)
        volume (get note 1)
        duration (get note 2)
        conv-duration (duration-helpers/note-to-milliseconds duration bpm)]
    [conv-pitch volume conv-duration]))

(defn convert-melody [melody bpm]
  (map
   (partial convert-note bpm)
   melody))

(defn play-tone [freq volume duration sample-rate oscillator line]
  (let [buf (oscillator freq volume duration sample-rate)]
    (.write ^SourceDataLine line buf 0 (count buf))))

(defn play-melody [audio-format oscillator melody bpm]
  (let [{:keys [sampleRate]} (bean audio-format)
        line (open-line audio-format)]
    (doseq [[freq volume duration] (convert-melody melody bpm)]
      (play-tone freq volume duration sampleRate oscillator line))
    (.drain line)
    (.stop line)
    (.close line)))