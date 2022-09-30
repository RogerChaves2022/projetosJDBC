package br.com.roger.service.impl;


import org.springframework.stereotype.Service;

import br.com.roger.model.entity.Usuario;
import br.com.roger.model.repository.UsuarioRepository;
import br.com.roger.service.UsuarioService;
import br.com.roger.service.exception.RegraNegocioException;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	
	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		super();
		this.repository = repository;
	}
	
	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = repository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
	}
	
	
	
}
