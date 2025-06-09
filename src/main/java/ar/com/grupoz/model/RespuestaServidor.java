package ar.com.grupoz.model;

/**
 * Representa la respuesta enviada por el servidor tras ejecutar un comando.
 * Contiene un indicador booleano que señala si la operación fue aceptada
 * y un mensaje adicional con información o detalles del resultado.
 */
public class RespuestaServidor {
	/**
	 * Indica si el servidor aceptó o no el comando enviado.
	 * {@code true} si fue aceptado; {@code false} en caso contrario.
	 */	private boolean aceptado;

	/**
	 * Mensaje devuelto por el servidor, puede ser informativo o de error.
	 */
	private String mensaje;

	public RespuestaServidor() {}

	public boolean getAceptado() {
		return aceptado;
	}

	public void setAceptado(boolean aceptado) {
		this.aceptado = aceptado;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
