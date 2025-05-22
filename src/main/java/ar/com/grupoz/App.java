package ar.com.grupoz;

import ar.com.grupoz.ui.Presentacion;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

public class App {

	@Inject
	Presentacion presentacion;

	public void run() {
		presentacion.menu();
	}

	public static void main(String[] args) {
		SeContainer container = SeContainerInitializer.newInstance().initialize();
		App app = container.select(App.class).get();
		app.run();
	}
}