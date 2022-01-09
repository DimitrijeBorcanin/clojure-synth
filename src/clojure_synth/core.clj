(ns clojure-synth.core
  (:require [clojure-synth.audio-formats :as audio-formats]
            [clojure-synth.oscillators :as oscillators]
            [clojure-synth.player :as player]
            [clojure-synth.sample-melodies :as sample-melodies]
            [clojure-synth.duration-helpers :as duration-helpers]
            [clojure-synth.frequency-helpers :as frequency-helpers]
            [clojure-synth.volume-helpers :as volume-helpers]))

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

(defn -main
  []
  (println "Clojure-Synth has started!")
  (while true 
    (println "Press enter to start using the app or type in 'test' to play the default tune! You can exit anytime by typing exit.")
    (let [in (read-line)]
      (case in
        "test" (play-melody af sine samo-mi-kazi 165)
        "exit" (java.lang.System/exit 0)
        ""
        (with-local-vars [bpm 120
                          melody []
                          oscillator sine
                          oscillator-name "sine"
                          melody-set false
                          bpm-valid false
                          osc-valid false]
          (println "Please enter bpm (30 - 300):")
          (while (not (var-get bpm-valid))
            (let [b (read-line)]
              (when (= b "exit")
                (java.lang.System/exit 0))
              (if (and (every? #(Character/isDigit %) b) (duration-helpers/bpm-validator (Integer/parseInt b)))
                (do (var-set bpm (Integer/parseInt b))
                    (var-set bpm-valid true))
                (println "Invalid bpm value, try again:"))))
          (println "Please choose an oscillator (1 - sine, 2 - square, 3 - sawtooth):")
          (while (not (var-get osc-valid))
            (let [osc (read-line)]
              (case osc
                "1" (do (var-set oscillator sine)
                        (var-set osc-valid true)
                        (var-set oscillator-name "sine"))
                "2" (do (var-set oscillator square)
                        (var-set osc-valid true)
                        (var-set oscillator-name "square"))
                "3" (do (var-set oscillator saw)
                        (var-set osc-valid true)
                        (var-set oscillator-name "sawtooth"))
                "exit" (java.lang.System/exit 0)
                (println "Invalid oscillator, try again:"))))
          (while (not (var-get melody-set))
            (with-local-vars [note []
                              pitch-valid false
                              volume-valid false
                              duration-valid false]
              (println "Please enter pitch (C0 - B8) or type in done to finish:")
              (while (not (var-get pitch-valid))
                (let [n (read-line)]
                  (when (= n "exit")
                    (java.lang.System/exit 0))
                  (if (= n "done")
                    (do (var-set melody-set true)
                        (var-set pitch-valid true))
                    (do (if (frequency-helpers/pitch-validator n)
                          (do (var-set note (conj (var-get note) n))
                              (var-set pitch-valid true)
                              (println "Please enter volume (0 - 100):")
                              (while (not (var-get volume-valid))
                                (let [v (read-line)]
                                  (when (= v "exit")
                                    (java.lang.System/exit 0))
                                  (if (and (every? #(Character/isDigit %) v) (volume-helpers/volume-validator (Integer/parseInt v)))
                                    (do (var-set note (conj (var-get note) (Integer/parseInt v)))
                                        (var-set volume-valid true))
                                    (println "Invalid volume, try again:"))))
                              (println "Please enter duration (1, 2, 4, 8, 16, 32, 64):")
                              (while (not (var-get duration-valid))
                                (let [d (read-line)]
                                  (when (= d "exit")
                                    (java.lang.System/exit 0))
                                  (if (and (every? #(Character/isDigit %) d) (duration-helpers/duration-validator (Integer/parseInt d)))
                                    (do (var-set note (conj (var-get note) (Integer/parseInt d)))
                                        (var-set duration-valid true))
                                    (println "Invalid duration, try again:"))))
                              (var-set melody (conj (var-get melody) (var-get note)))
                              (println (var-get melody)))
                          (println "Invalid pitch, try again:"))))))))
          (println (str "Playing melody at " (var-get bpm) " bpms, using " (var-get oscillator-name) " oscillator."))
          (play-melody af (var-get oscillator) (var-get melody) (var-get bpm)))
        (println "Invalid command, try again:")))))