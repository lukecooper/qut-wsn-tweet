(defproject qut-wsn-tweet "0.1.0-SNAPSHOT"
  :description "Tweet utility for qut-wsn"
  :url "http://github.com/lukecooper/qut-wsn-tweet"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [twitter-api "0.7.5"]
                 [org.clojure/tools.logging "0.2.4"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :main ^:skip-aot qut-wsn-tweet.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
