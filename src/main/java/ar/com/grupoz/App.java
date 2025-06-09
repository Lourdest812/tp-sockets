package ar.com.grupoz;

import ar.com.grupoz.ui.Presentacion;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.inject.Inject;

/**
 * Clase principal de la aplicación.
 * Esta clase utiliza Jakarta CDI para la inyección de dependencias. Por eso, no se instancia directamente con `new`,
 * sino que se obtiene desde el contenedor (`SeContainer`) para asegurar que sus dependencias (como `Presentacion`)
 * sean correctamente inyectadas.
 */
public class App {

	@Inject
	Presentacion presentacion;

	public void run() {
		presentacion.menu();
	}

	public static void main(String[] _args) {
		SeContainer container = SeContainerInitializer.newInstance().initialize();
		App app = container.select(App.class).get();
		app.run();
	}
}