(ns clj-progress-quest-stub.core

  [:import [javax.swing JLabel JPanel JFrame JProgressBar JButton SwingUtilities]])

;; Stolen from Stuart Sierra's blog
(defmacro with-action [component event & body]
	`(. ~component addActionListener
		(proxy [java.awt.event.ActionListener] []
		(actionPerformed [~event] ~@body))))


(defn create-world [] {
	:quest-text "The quest begins"
	:quests-completed 0
	})

(defn update-world [world progress]
	(if (= progress 100)
		(let [quests-completed (inc (:quests-completed world))
			quest-text (str "Carrying out quest " quests-completed)]
		(assoc world :quest-text quest-text :quests-completed quests-completed))
		world))


(defn perform-heroic-action [status-message progress-bar world]
	(let [current-progress (.getValue progress-bar) _ (println current-progress)
		new-world (update-world world current-progress)]
	(if (= current-progress 100)
		(do
			(.setValue progress-bar 0)
			(.setText status-message (:quest-text new-world)))

		(.setValue progress-bar (+ current-progress 10)))
	(Thread/sleep 1000)
	(recur status-message progress-bar new-world)))

(defn progress-quest []
		(let [status-message (JLabel. "Your adventure awaits")
		progress-bar (JProgressBar. 0 100)
		start-button (JButton. "Begin your quest")
		panel (doto (JPanel.) (. add status-message) (. add progress-bar) (. add start-button))]
	(with-action start-button e
		(do (.setEnabled start-button false)
			(.setText status-message "Your quest begins")
			(.setValue progress-bar 0)
			(future (perform-heroic-action status-message progress-bar (create-world)))))
	(doto (JFrame. "Clojure Progress Quest")
		(.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
		(.setContentPane panel)
		(.pack)
		(.setVisible true))))

(defn -main [& args]
	(SwingUtilities/invokeLater progress-quest))