(ns clojure-synth.audio-formats)

(import '(javax.sound.sampled AudioSystem DataLine$Info SourceDataLine
                              AudioFormat AudioFormat$Encoding))

(def available-sample-rates #{44100 48000 88200 96000})
(def available-bits #{8 16 24})
(def available-channels #{1 2})

(defn format-validation
  [sample-rate bits channels]
  (if (and
       (contains? available-sample-rates sample-rate)
       (contains? available-bits bits)
       (contains? available-channels channels))
    true
    false))

(def default-format
  (AudioFormat. AudioFormat$Encoding/PCM_SIGNED
                48000 ; sample rate
                16    ; bits per sample
                2     ; channels
                4     ; frame size 2*16bits [bytes]
                48000 ; frame rate
                false ; little endian
                ))

(defn custom-format
  [sample-rate bits channels]
  (if (format-validation sample-rate bits channels)
    (AudioFormat. AudioFormat$Encoding/PCM_SIGNED
                  sample-rate
                  bits
                  channels
                  (/ (* bits channels) 8)
                  sample-rate
                  false)
    (throw (Throwable. "Illegal arguments!"))))

