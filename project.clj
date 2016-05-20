(defproject thinktopic/raven "0.1.2-SNAPSHOT"
  :description "A simple notifications library using reagent."
  :url "http://www.github.com/thinktopic/raven"
  :license "Eclipse License"
  :scm {:name "git"
        :url "https://github.com/thinktopic/raven"}

  :source-paths ["src/clj" "src/cljs"]

  :plugins [[lein-environ "1.0.0"]
            [lein-garden "0.2.6"]]

  :garden {:builds [{:id "dev"
                     :pretty-print? true
                     :source-paths ["src/clj/raven/styles.clj"]
                     :stylesheet raven.styles/raven-styles
                     :compiler {:output-to "resources/public/css/raven.css"
                                :pretty-print? true}}]}

  :min-lein-version "2.5.0"

  :clean-targets ^{:protect false} [:target-path
                                    [:cljsbuild :builds :app :compiler :output-dir]
                                    [:cljsbuild :builds :app :compiler :output-to]]

  :profiles {:provided {:dependencies [[org.clojure/clojurescript "1.7.48"]
                                       [reagent "0.5.0"]]}

             :dev {:repl-options {:init-ns user
                                  :nrepl-middleware []}

                   :ring {:handler raven.handler/app
                          :uberwar-name "raven.war"}

                   :cljsbuild {:builds {:app {:source-paths ["src/cljs" "env/dev/cljs"]
                                              :compiler {:output-to "resources/public/js/app.js"
                                                         :output-dir "resources/public/js/out"
                                                         :asset-path "js/out"
                                                         :optimizations :none
                                                         :pretty-print true
                                                         :main "raven.dev"
                                                         :source-map true}}}}

                   :dependencies [[garden "1.2.5"]
                                  [cljsjs/react "0.13.3-0"]
                                  [org.clojure/clojure "1.7.0"]
                                  [environ "1.0.0"]
                                  [ring-server "0.4.0"]
                                  [ring "1.3.2"]
                                  [ring/ring-defaults "0.1.5"]
                                  [prone "0.8.2"]
                                  [compojure "1.3.3"]
                                  [metosin/compojure-api "0.22.1"]
                                  [hiccup "1.0.5"]
                                  [secretary "1.2.3"]
                                  [com.taoensso/timbre "4.0.2"]
                                  [org.danielsz/system "0.1.8"]
                                  [http-kit "2.1.19"]
                                  [ring-mock "0.1.5"]
                                  [ring/ring-devel "1.3.2"]
                                  [leiningen-core "2.5.1"]
                                  [lein-figwheel "0.3.5"]
                                  [reagent-utils "0.1.5"]
                                  [org.clojure/tools.nrepl "0.2.10"]
                                  [pjstadig/humane-test-output "0.7.0"]]

                   :source-paths ["env/dev/clj"]
                   :plugins [[lein-figwheel "0.3.3"]
                             [lein-cljsbuild "1.0.6"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :nrepl-port 7002
                              :css-dirs ["resources/public/css"]
                              :ring-handler handler/app}

                   :env {:dev true}}})
