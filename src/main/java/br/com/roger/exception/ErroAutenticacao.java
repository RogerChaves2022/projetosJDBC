package br.com.roger.exception;

public class ErroAutenticacao extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ErroAutenticacao(String mensagem) {
		super(mensagem);
	}
	
}
