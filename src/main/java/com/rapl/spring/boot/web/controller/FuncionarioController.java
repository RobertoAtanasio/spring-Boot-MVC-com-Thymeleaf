package com.rapl.spring.boot.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rapl.spring.boot.domain.Cargo;
import com.rapl.spring.boot.domain.Funcionario;
import com.rapl.spring.boot.domain.UF;
import com.rapl.spring.boot.service.CargoService;
import com.rapl.spring.boot.service.FuncionarioService;
import com.rapl.spring.boot.web.validator.FuncionarioValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

//	@GetMapping("/cadastrar")
//	public String cadastrar() {
//		return "funcionario/cadastro";
//	}
//	
//	@GetMapping("/listar")
//	public String listar() {
//		return "funcionario/lista";
//	}
	
	protected String url;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private CargoService cargoService;

	// esta classe acessa o messages.properties para acessa as mensagens de erro cadastradas.
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new FuncionarioValidator());
	}
	
	@GetMapping("/cadastrar")
	public String cadastrar(Funcionario funcionario) {
		this.url = "funcionario/cadastro";
		return this.url;
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		this.url = "funcionario/lista";
		model.addAttribute("funcionarios", funcionarioService.buscarTodos());
		return this.url; 
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "funcionario/cadastro";
		}
		this.url = "redirect:/funcionarios/cadastrar";
		funcionarioService.salvar(funcionario);
		attr.addFlashAttribute("success", "Funcionário inserido com sucesso.");
		return this.url;
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		this.url = "funcionario/cadastro";
		model.addAttribute("funcionario", funcionarioService.buscarPorId(id));
		return this.url;
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "funcionario/cadastro";
		}
		this.url = "redirect:/funcionarios/cadastrar";
		funcionarioService.editar(funcionario);
//		result.addError(new ObjectError("funcionario", "Funcionário editado com sucesso."));
		attr.addFlashAttribute("success", "Funcionário editado com sucesso.");
		return this.url;
	}	
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		this.url = "redirect:/funcionarios/listar";
		funcionarioService.excluir(id);
		attr.addFlashAttribute("success", "Funcionário removido com sucesso.");
		return this.url;
	}	
	
	@GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap model) {
		this.url = "funcionario/lista";
		model.addAttribute("funcionarios", funcionarioService.buscarPorNome(nome));
		return this.url;
	}
	
	@GetMapping("/buscar/cargo")
	public String getPorCargo(@RequestParam("id") Long id, ModelMap model) {
		System.out.println(">>>> ID: " + id);
		this.url = "funcionario/lista";
		model.addAttribute("funcionarios", funcionarioService.buscarPorCargo(id));
		return this.url;
	}		
	
    @GetMapping("/buscar/data")
    public String getPorDatas(@RequestParam("entrada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrada,
                              @RequestParam("saida") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate saida,
                              ModelMap model) {
    	this.url = "funcionario/lista";
        model.addAttribute("funcionarios", funcionarioService.buscarPorDatas(entrada, saida));
        return this.url;
    }
	
	@ModelAttribute("cargos")
	public List<Cargo> getCargos() {
		return cargoService.buscarTodos();
	}
	
	@ModelAttribute("ufs")
	public UF[] getUFs() {
		return UF.values();
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
	public String errorIllegalArgumentException(IllegalArgumentException e, RedirectAttributes attr) {
		attr.addFlashAttribute("fail", e.getMessage());
		return this.url;
    }
}
