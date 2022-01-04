(ns clojure-synth.oscillators)

(defn sine
  [freq volume duration sample-rate]
  (let [tmp (* 2.0 Math/PI freq)
        time (* (/ sample-rate 1000) duration)]
    (->> (map #(* (Math/sin (* (/ % sample-rate) tmp)) volume) (range time))
         (byte-array))))