package com.rapl.spring.boot.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rapl.spring.boot.domain.Cargo;
import com.rapl.spring.boot.domain.Departamento;
import com.rapl.spring.boot.service.CargoService;
import com.rapl.spring.boot.service.DepartamentoService;

@Controller
@RequestMapping("/cargos")
public class CargoController {

	protected String url;

	@Autowired
	private CargoService cargoService;
	
	@Autowired
	private DepartamentoService departamentoService;

//	@GetMapping("/cadastrar")
//	public String cadastrar(Cargo cargo, ModelMap model) {
//		model.addAttribute("departamentos", departamentoService.buscarTodos());	
//		return "/cargo/cadastro";
//	}
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		this.url = "/cargo/cadastro";
		return this.url;
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		this.url = "/cargo/lista";
		model.addAttribute("cargos", cargoService.buscarTodos());
		return this.url; 
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			// página: diretório/pagina.html
			return "/cargo/cadastro";
		}
		this.url = "redirect:/cargos/cadastrar";
		cargoService.salvar(cargo);
		attr.addFlashAttribute("success", "Cargo inserido com sucesso.");		
		return this.url;
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		this.url = "cargo/cadastro";
		model.addAttribute("cargo", cargoService.buscarPorId(id));
		return this.url;
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Cargo cargo, BindingResult result,  RedirectAttributes attr) {
		if (result.hasErrors()) {
			return "/cargo/cadastro";		// página: diretório/pagina.html
		}
		this.url = "redirect:/cargos/cadastrar";
		cargoService.editar(cargo);
		attr.addFlashAttribute("success", "Registro atualizado com sucesso.");
		return this.url;
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		this.url = "redirect:/cargos/listar";
		cargoService.excluir(id);
		attr.addFlashAttribute("success", "Cargo excluido com sucesso.");
		return this.url;
	}
	
	// carrega os departamentos sempre que um dos métodos for executado
	
	@ModelAttribute("departamentos")
	private List<Departamento> listaDeDepartamentos() {
		return departamentoService.buscarTodos();
	}	
	
	@ExceptionHandler({IllegalArgumentException.class})
	public String errorIllegalArgumentException(IllegalArgumentException e, RedirectAttributes attr) {
		attr.addFlashAttribute("fail", e.getMessage());
		return this.url;
    }
	
	// obtem a URL corrente
//	@ExceptionHandler({IllegalArgumentException.class})
//	public String errorIllegalArgumentException(IllegalArgumentException e, RedirectAttributes attr, HttpServletRequest request) {
//		attr.addFlashAttribute("fail", e.getMessage());
//		System.out.println(">>>>>> URL : " + this.getURLValue(request));
//		return this.url;
//    }

//	@RequestMapping(value ="/",produces = "application/json")
//	public String getURLValue(HttpServletRequest request){
//	    String test = request.getRequestURI();
//	    return test;
//	}
}
