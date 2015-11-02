(ns user
  (:require [clojure.repl :refer [doc find-doc]]
            [clojure.tools.namespace.repl :refer [refresh]]
            [com.stuartsierra.component :as component]
            [taoensso.timbre :as log]
            [environ.core :refer [env]]
            [system :refer :all]
            [reloaded.repl]))

(reloaded.repl/set-init! dev-system)
