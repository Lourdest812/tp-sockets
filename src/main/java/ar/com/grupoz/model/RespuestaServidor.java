package ar.com.grupoz.model;

public class RespuestaServidor {
	private boolean aceptado;
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
