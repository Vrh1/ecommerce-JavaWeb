package br.com.dankicommerce.controller;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.IncludeParameters;
import br.com.caelum.vraptor.validator.SimpleMessage;
import br.com.caelum.vraptor.validator.Validator;
import br.com.dankicommerce.model.Usuario;
import br.com.olimposistema.aipa.dao.DAO;

@Controller
@Path("cadastrar")
public class CadastrarController {
	
	@Inject EntityManager em;
	@Inject Result result;
	@Inject DAO<Usuario> usuarioDao;
	@Inject Validator validator;
	
	@Get("")
	public void cadastrar() {
		
	}
	
	@IncludeParameters
	@Post("salvaUsuario")
	public void salvaUsuario(@Valid Usuario usuario, String confirmaSenha) {
		
		boolean verificaSenhasIguais = usuario.getSenha().equals(confirmaSenha); // Verifica se as senhas são iguais.
		validator.ensure(verificaSenhasIguais, new SimpleMessage("error", "A confirmação de senha está diferente.")); //addIf se for true, ensure se o retorno é false
		validator.onErrorRedirectTo(this).cadastrar();
		// se tiver erro redireciona para cadastrar:
		
		usuarioDao.insert(usuario);
		result.redirectTo(ProdutosController.class).produtos();
		// result.include("usuario", usuario);
	}
	
}
