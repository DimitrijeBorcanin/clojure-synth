(ns clojure-synth.duration-helpers)

(def available-durations #{1 2 4 8 16 32 64})

(def bpm-range {:min 30 :max 300})

(defn bpm-validator [bpm]
  (if (and (>= bpm (get bpm-range :min)) (<= bpm (get bpm-range :max)))
    true
    false))

(defn duration-validator [duration]
  (if (contains? available-durations duration)
    true
    false))

(defn quarter-note-duration [bpm]
  (if (bpm-validator bpm)
    (/ 60.0 bpm)
    (throw (Throwable. "BPM must be between 30 and 300!"))))

(defn note-duration [duration]
  (if (contains? available-durations duration)
    (/ 4.0 duration)
    (throw (Throwable. "Invalid duration!"))))

(defn get-milliseconds [duration]
  (* duration 1000))

(defn note-to-milliseconds [note bpm]
  (let [q-note (quarter-note-duration bpm)
        n-duration (note-duration note)]
    (Math/round (get-milliseconds (* q-note n-duration)))))
