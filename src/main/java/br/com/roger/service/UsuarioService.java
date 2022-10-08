package br.com.roger.service;

import java.util.Optional;

import br.com.roger.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(String email, String senha);
	
	Usuario salvarUsuario(Usuario usuario);
	
	Optional<Usuario> obterPorId(Long id);
	
	void validarEmail(String email);

}
