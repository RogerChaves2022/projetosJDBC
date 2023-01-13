package br.com.roger.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.roger.model.entity.Usuario;
import br.com.roger.model.repository.UsuarioRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;
	
	public SecurityUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuarioEncontrado = usuarioRepository
					.findByEmail(email)
					.orElseThrow(() -> new UsernameNotFoundException("Email não cadastrado."));
		
		return User.builder()
				.username(usuarioEncontrado.getEmail())
				.password(usuarioEncontrado.getSenha())
				.roles("USER")
				.build();
	}

}
