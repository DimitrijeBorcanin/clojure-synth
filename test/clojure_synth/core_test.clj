(ns clojure-synth.core-test
  (:require [clojure.test :refer :all]
            [clojure-synth.core :as core]))

(deftest playing-test
  (testing "Melody plays."
    (is (nil? (core/play-melody core/af core/sine core/samo-mi-kazi 165)))))