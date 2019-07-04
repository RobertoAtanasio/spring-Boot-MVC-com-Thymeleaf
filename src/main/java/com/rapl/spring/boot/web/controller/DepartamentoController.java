package com.rapl.spring.boot.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.rapl.spring.boot.domain.Departamento;
import com.rapl.spring.boot.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	protected String url;
	
	@Autowired
	private DepartamentoService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "/departamento/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("departamentos", service.buscarTodos());
		return "/departamento/lista"; 
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			// página: diretório/pagina.html
			return "/departamento/cadastro";
		}
		this.url = "redirect:/departamentos/cadastrar";
		service.salvar(departamento);
		// usamos o attr.addFlashAttribute quando utilizamos o redirect
		attr.addFlashAttribute("success", "Departamento inserido com sucesso.");
		return this.url;
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("departamento", service.buscarPorId(id));
		return "/departamento/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {
		this.url = "redirect:/departamentos/cadastrar";
		if (result.hasErrors()) {
			// página: diretório/pagina.html
			return "/departamento/cadastro";
		}
		service.editar(departamento);
		// usamos o attr.addFlashAttribute quando utilizamos o redirect
		attr.addFlashAttribute("success", "Departamento editado com sucesso.");
		return this.url;
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		// as mensagens são inseridas nos campos definidos em alert.html
		if (service.departamentoTemCargos(id)) {
			model.addAttribute("fail", "Departamento não removido. Possui cargo(s) vinculado(s).");
		} else {
			service.excluir(id);
			model.addAttribute("success", "Departamento excluído com sucesso.");
		}
		// poderia fazer um redirect para a página departamento/lista
		return listar(model);
	}
	
	@ExceptionHandler({IllegalArgumentException.class})
	public String errorIllegalArgumentException(IllegalArgumentException e, RedirectAttributes attr) {
		attr.addFlashAttribute("fail", e.getMessage());
		return this.url;
    }
}