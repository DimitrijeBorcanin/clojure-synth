(ns clojure-synth.oscillators)

(defn sine
  [freq volume duration sample-rate]
  (let [tmp (* 2.0 Math/PI freq)
        time (* (/ sample-rate 1000) duration)]
    (->> (map #(* (Math/sin (* (/ % sample-rate) tmp)) volume) (range time))
         (byte-array))))

(defn square
  [freq volume duration sample-rate]
  (let [tmp (* 2.0 Math/PI freq)
        time (* (/ sample-rate 1000) duration)]
    (->> (map #(* (Math/signum (Math/sin (* (/ % sample-rate) tmp))) volume) (range time))
         (byte-array))))

(defn saw
  [freq volume duration sample-rate]
  (let [tmp (/ sample-rate freq)
        time (* (/ sample-rate 1000) duration)]
    (->> (map #(* (- (/ (* (mod % tmp) 2) tmp) 1) volume) (range time))
         (byte-array))))