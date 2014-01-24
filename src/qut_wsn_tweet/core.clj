(ns qut-wsn-tweet.core
  (:gen-class)
  (:require [http.async.client :as ac])
  (:use [clojure.java.shell :only [sh]]
        [clojure.string :only [split trim]]
        [twitter.api.restful]
        [twitter.oauth]))

(def twitter-account
  {:screen-name "qut_wsn"
   :password "rpi_qut_wsn"
   :email "l.cooper@student.qut.edu.au"})

(defn credentials
  "These credentials were generated using the dev.twitter.com website using the
   above twitter account login. Navigate to the QUT WSN application details to
   view or refresh the OAuth tokens."
  []
  (make-oauth-creds "wUcOcgBnqAFN5Q6NJKVtXw"                             ; consumer key
                    "8GnA3iwwHaPI7Xh3bT6yQ20DHES6beVEWYPRKPEc"           ; consumer secret
                    "2307426354-yIG6am8YwOwv1PptSeLNhelhyfUCgni3YJUmGhJ" ; access token
                    "CrdYnBycJaV5wtZBF8rkF3OwjGQWEmkRPNkFtA5bkKZWn"      ; access token secret
                    ))

(defn hostname
  []
  (trim (:out (sh "hostname"))))

(defn ip-addr
  []
  (trim (:out (sh "hostname" "-I"))))

(defn host-address
  []
  (format "%s %s" (hostname) (ip-addr)))

(defn tweet
  "Posts a text tweet using the given OAuth credentials."
  [message client]
  (statuses-update :oauth-creds (credentials)
                   :params {:status message}
                   :client client))

(defn get-tweets
  "Retrieves a seq of status updates for the given screen name."
  [screen-name client]
  (statuses-user-timeline :oauth-creds (credentials)
                          :params {:screen-name screen-name}
                          :client client))

(defn get-last-tweet-text
  "Returns the text of the last status update for the given screen-name."
  [screen-name client]
  (:text (first (:body (get-tweets screen-name client)))))

(defn -main
  [& args]
  (let [message (host-address)]
    (with-open [client (ac/create-client :follow-redirects false
                                         ; :proxy {:host "localhost" :port 3128}
                                         )]      
      (if-not (= message (get-last-tweet-text (:screen-name twitter-account) client))
        (tweet (host-address) client)))))
