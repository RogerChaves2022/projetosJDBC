package br.com.roger.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.roger.exception.ErroAutenticacao;
import br.com.roger.exception.RegraNegocioException;
import br.com.roger.model.entity.Usuario;
import br.com.roger.model.repository.UsuarioRepository;
import br.com.roger.service.impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl service;
	
	@MockBean
	UsuarioRepository repository;
	
	
	@Test
	public void deveSalvarUmUsuario() {
		Assertions.assertDoesNotThrow(() -> {
		//cenario
		Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
		Usuario usuario = Usuario.builder().nome("roger").email("roger@gmail.com").senha("senha").build();
		Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
		//acao
		Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
		//verificacao
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo).isNotNull();
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo.getId()).isEqualTo(1L);
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("roger");
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("roger@gmail.com");
		org.assertj.core.api.Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
		});
	}
	
	@Test
	public void deveValidarEmail() {
		Assertions.assertDoesNotThrow(() -> {
		//cenario
			Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		//acao
		service.validarEmail("email@email.com");
		});
	}
	
	@Test
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		Assertions.assertThrows(RegraNegocioException.class, () -> {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		//acao
		service.validarEmail("usuario@email.com");
		});
	}
	
	@Test
	public void deveAutenticarUsuarioComSucesso() {
		Assertions.assertDoesNotThrow(() -> {
		//cenario
		String email = "roger@gmail.com";
		String senha = "senha123";
		
		Usuario usuario = Usuario
				.builder()
				.email(email)
				.senha(senha)
				.id(1L)
				.build();
		
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//acao
		Usuario result = service.autenticar(email, senha);
		
		//verificação
		org.assertj.core.api.Assertions.assertThat(result).isNotNull();
		});
	}
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
			//Cenario
			Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
			//acao
			Throwable exception = org.assertj.core.api.Assertions
					.catchThrowable(() -> service.autenticar("email@gmail.com", "senha"));
			org.assertj.core.api.Assertions
				.assertThat(exception)
				.isInstanceOf(ErroAutenticacao.class)
				.hasMessage("Usuario não encontrado para o email informado.");
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
			//cenario
			String senha = "senha";
			Usuario usuario = Usuario.builder().email("usuario@gmail.com").senha(senha).build();
			Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
			
			//acao
			
			Throwable exception = org.assertj.core.api.Assertions
					.catchThrowable(() -> service.autenticar("usuario@gmail.com", "senha123"));
			
			org.assertj.core.api.Assertions
				.assertThat(exception)
				.isInstanceOf(ErroAutenticacao.class)
				.hasMessage("Senha inválida");
	}

}
