(defproject thinktopic/raven "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://www.github.com/ThinkTopic/raven"
  :license {:name "Super Secret ThinkTopic License"
            :url "http://thinktopic.com"}

  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[org.clojure/clojurescript "1.7.48" :scope "provided"]
                 [cljsjs/react "0.13.3-0"]
                 [reagent "0.5.0"]
                 [garden "1.2.5"]]

  :plugins [[lein-environ "1.0.0"]
            [lein-asset-minifier "0.2.2"]
            [lein-garden "0.2.6"]
            [s3-wagon-private "1.1.2"]]

  :garden {:builds [{:id "dev"
                     :pretty-print? true
                     :source-paths ["src/clj/raven/styles.clj"]
                     :stylesheet raven.styles/raven-styles
                     :compiler {:output-to "resources/public/css/raven.css"
                                :pretty-print? true}}]}

  :ring {:handler raven.handler/app
         :uberwar-name "raven.war"}

  :min-lein-version "2.5.0"

  :uberjar-name "raven.jar"

  :clean-targets ^{:protect false} [:target-path
                                    [:cljsbuild :builds :app :compiler :output-dir]
                                    [:cljsbuild :builds :app :compiler :output-to]]

  :minify-assets
  {:assets
    {"resources/public/css/site.min.css" "resources/public/css/site.css"}}

  :cljsbuild {:builds {:app {:source-paths ["src/cljs"]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :asset-path   "js/out"
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :profiles {:dev {:repl-options {:init-ns user
                                  :nrepl-middleware []}

                   :dependencies [[org.clojure/clojure "1.7.0"]
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

                   :source-paths ["env" "dev" "clj"]
                   :plugins [[lein-figwheel "0.3.3"]
                             [lein-cljsbuild "1.0.6"]]

                   :injections [(require 'pjstadig.humane-test-output)
                                (pjstadig.humane-test-output/activate!)]

                   :figwheel {:http-server-root "public"
                              :server-port 3449
                              :nrepl-port 7002
                              :css-dirs ["resources/public/css"]
                              :ring-handler raven.handler/app}

                   :env {:dev true}

                   :cljsbuild {:builds {:app {:source-paths ["env/dev/cljs"]
                                              :compiler {:main "raven.dev"
                                                         :source-map true}}
}
}}

             :uberjar {:hooks [leiningen.cljsbuild minify-assets.plugin/hooks]
                       :env {:production true}
                       :aot :all
                       :omit-source true
                       :cljsbuild {:jar true
                                   :builds {:app
                                            {:source-paths ["env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}}

  :repositories  {"snapshots"  {:url "s3p://thinktopic.jars/snapshots/"
                                :passphrase :env
                                :username :env}
                  "releases"  {:url "s3p://thinktopic.jars/releases/"
                               :passphrase :env
                               :username :env}})
