(ns clojure-synth.volume-helpers)

(def volume-range {:min 0 :max 100})

(defn volume-validator [volume]
  (if (and (>= volume (get volume-range :min)) (<= volume (get volume-range :max)))
    true
    false))