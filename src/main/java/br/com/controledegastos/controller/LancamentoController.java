package br.com.controledegastos.controller;

import br.com.controledegastos.model.Lancamento;
import br.com.controledegastos.model.TipoLancamento;
import br.com.controledegastos.repository.LancamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@Controller
public class LancamentoController {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        lancamentos.sort(Comparator.comparing(Lancamento::getData).reversed());
        model.addAttribute("lancamentos", lancamentos);
        model.addAttribute("novoLancamento", new Lancamento());
        model.addAttribute("tipos", TipoLancamento.values());
        return "index";
    }

    @PostMapping("/lancamentos")
    public String addLancamento(@ModelAttribute Lancamento novoLancamento, Model model) {
        lancamentoRepository.save(novoLancamento);

        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        lancamentos.sort(Comparator.comparing(Lancamento::getData).reversed());
        model.addAttribute("lancamentos", lancamentos);

        return "index :: lista-lancamentos";
    }

    @DeleteMapping("/lancamentos/{id}")
    public String deleteLancamento(@PathVariable Long id, Model model) {
        lancamentoRepository.deleteById(id);

        List<Lancamento> lancamentos = lancamentoRepository.findAll();
        lancamentos.sort(Comparator.comparing(Lancamento::getData).reversed());
        model.addAttribute("lancamentos", lancamentos);

        return "index :: lista-lancamentos";
    }
}
