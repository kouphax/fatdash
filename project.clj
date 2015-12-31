(defproject fatdash "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [stasis "2.3.0"]
                 [ring "1.4.0"]]
  :plugins  [[lein-ring "0.9.7"]]
  :aliases { "new-entry" ["run" "-m" "fatdash.core/create-entry"]
             "build-site" ["run" "-m" "fatdash.core/build-site"] }
  :ring  {:handler fatdash.core/app})
