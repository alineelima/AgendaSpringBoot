package curso.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.repository.PessoaRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository; //injecao de dependencia
	
	@RequestMapping(method=RequestMethod.GET, value="/cadastroPessoa")
	public ModelAndView inicio() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("objPessoa", new Pessoa());
		
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="**/salvarPessoa")
	public ModelAndView salvar(Pessoa pessoa) {
		pessoaRepository.save(pessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll(); //o repository do JPA retorna Iterable
		modelAndView.addObject("pessoas", pessoasIt); //add ao  atributo pessoas (${pessoas}) o Iterable com dados da tabela Pessoa
		modelAndView.addObject("objPessoa", new Pessoa());
		
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET, value= "/listaPessoa")
	public ModelAndView pessoas() {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
			Iterable<Pessoa> pessoasIt = pessoaRepository.findAll(); //o repository do JPA retorna Iterable
			modelAndView.addObject("pessoas", pessoasIt); //add ao  atributo pessoas (${pessoas}) o Iterable com dados da tabela Pessoa
			modelAndView.addObject("objPessoa", new Pessoa()); //adc um objeto vazio para que o formulario seja iniciado sem erros
			
			return modelAndView;
	}
	
	@GetMapping("/editarPessoa/{idPessoa}")
	public ModelAndView editar(@PathVariable("idPessoa") Long idPessoa) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		Optional<Pessoa> pessoa = pessoaRepository.findById(idPessoa);
		modelAndView.addObject("objPessoa", pessoa.get());
		
		return modelAndView;
	}
	
	@GetMapping("/removerPessoa/{idPessoa}")
	public ModelAndView excluir(@PathVariable("idPessoa") Long idPessoa) {
		pessoaRepository.deleteById(idPessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findAll());
		modelAndView.addObject("objPessoa", new Pessoa());
		
		return modelAndView;
	}
	
	@PostMapping("**/pesquisarPessoa")
	public ModelAndView pesquisar(@RequestParam("nomePesquisa") String nomePesquisa) {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroPessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(nomePesquisa));
		modelAndView.addObject("objPessoa", new Pessoa());
		
		return modelAndView;
	}
}
