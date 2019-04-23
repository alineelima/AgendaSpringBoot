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
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository; //injecao de dependencia
	@Autowired
	private TelefoneRepository telefoneRepository;
	
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
	
	@GetMapping("/telefones/{idPessoa}")
	public ModelAndView telefones(@PathVariable("idPessoa") Long idPessoa) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(idPessoa);
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoa", pessoa.get());
		modelAndView.addObject("telefone", telefoneRepository.getTelefones(idPessoa));
		
		return modelAndView;
	}
	
	@PostMapping("**/addTelefonePessoa/{pessoaId}")
	public ModelAndView addFonePessoa(Telefone telefone,
				@PathVariable("pessoaId") Long pessoaId) {
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		Pessoa pessoa = pessoaRepository.findById(pessoaId).get(); //busca a pessoa através dp id
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		
		modelAndView.addObject("pessoa", pessoa);
		modelAndView.addObject("telefone", telefoneRepository.getTelefones(pessoaId));
				
		return modelAndView;
	}
	
	@GetMapping("/removerTelefone/{idNum}")
	public ModelAndView excluirTelefone(@PathVariable("idNum") Long idNum) {
		Pessoa pessoa = telefoneRepository.findById(idNum).get().getPessoa();
		telefoneRepository.deleteById(idNum);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("pessoa",pessoa);
		modelAndView.addObject("telefone", telefoneRepository.getTelefones(pessoa.getId()));
		
		return modelAndView;
	}
}
