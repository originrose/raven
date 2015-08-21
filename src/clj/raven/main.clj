(ns raven.main
    (:gen-class)
    (:require
     [taoensso.timbre :as log]
     [reloaded.repl :as reloaded]
     [raven.system :as system]))

(defn -main [& args]
  (log/info "Hold onto your butts, starting raven...")
  (reloaded/set-init! system/prod-system)
  (reloaded/go))
