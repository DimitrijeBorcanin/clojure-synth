(ns clojure-synth.audio-formats)

(import '(javax.sound.sampled AudioFormat))

(def available-sample-rates #{44100 48000 88200 96000})
(def available-bits #{8 16})
(def available-channels #{1 2})

(defn format-validator
  [sample-rate bits channels]
  (if (and
       (contains? available-sample-rates sample-rate)
       (contains? available-bits bits)
       (contains? available-channels channels))
    true
    false))

(def default-format
  (AudioFormat. 44100 ; sample rate
                8    ; bits per sample
                1     ; channels
                true
                false))

(defn custom-format
  [sample-rate bits channels]
  (if (format-validator sample-rate bits channels)
    (AudioFormat. sample-rate
                  bits
                  channels
                  true
                  false)
    (throw (Throwable. "Illegal arguments!"))))

