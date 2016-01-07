(ns fatdash.core
  (:require  [stasis.core :as stasis]
             [clojure.java.shell :refer [sh]]))

(def file-date-format (java.text.SimpleDateFormat. "yyyyMMddhhmm"))

(defn create-entry [text]
  (let [date (.format  file-date-format  (java.util.Date.))
        file (str date ".fd")
        path (str "./resources/log/" file)]
    (println "Generating file " path)
    (spit path text)
    (println "Commiting " file)
    (sh "git" "add" path)
    (sh "git" "commit" "-m" (str "New entry: " file))
    (println "Rebasing remote repo")
    (sh "git" "pull" "--rebase" "origin" "master")
    (println "Pushing to remote repo")
    (sh "git" "push")))

(defn- read-entries []
  (let [file-map (stasis/slurp-directory "./resources/log" #"\.fd$")]
    (for [[file-name contents] file-map
          :let [date (.parse file-date-format (clojure.string/replace file-name #"[\/(\.fd)]" ""))
                weight (->> (re-find #"@\d+" contents) rest clojure.string/join Integer.)]]
      { :date date
        :contents contents
        :weight weight})))

(def pages  {"/index.html" (str (into [] (read-entries)))})

(def app (stasis/serve-pages pages))

(defn build-site []
  (stasis/empty-directory! "build")
  (stasis/export-pages pages "build"))
