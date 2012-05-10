(ns ^{:doc "MigLayout support for Seesaw"
      :author "Dave Ray"}
  seesaw.spring
  (:use [seesaw.core :only [abstract-panel default-options]]
        [seesaw.layout :only [LayoutManipulation add-widget handle-structure-change]]
        [seesaw.options :only [default-option option-map option-provider]]
        [seesaw.util :only [cond-doto]]))

(def compass {:north javax.swing.SpringLayout/NORTH :south javax.swing.SpringLayout/SOUTH :east javax.swing.SpringLayout/EAST :west javax.swing.SpringLayout/WEST})

(defn- add-spring-items [^java.awt.Container parent items]
  (.removeAll parent)
  (doseq [[widget constraint] items]
    (add-widget parent widget)
    (doseq [[e1 pad e2 c2] constraint]
      (if (= :this c2)
        (. (. parent getLayout) putConstraint (e1 compass) widget pad (e2 compass) parent)
        (. (. parent getLayout) putConstraint (e1 compass) widget pad (e2 compass) c2))))
  (handle-structure-change parent))

(def spring-layout-options
  (option-map
    (default-option :items add-spring-items)))

(option-provider javax.swing.SpringLayout spring-layout-options)

(def spring-panel-options default-options)

(defn spring-panel
    { :seesaw {:class 'javax.swing.JPanel }}
  [& opts]
  (abstract-panel (javax.swing.SpringLayout.) opts))
