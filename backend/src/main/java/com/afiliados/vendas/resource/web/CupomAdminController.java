package com.afiliados.vendas.resource.web;

import com.afiliados.vendas.domain.cupom.TipoDesconto;
import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.usecase.cupom.*;
import com.afiliados.vendas.usecase.cupom.dto.AtualizarCupomInput;
import com.afiliados.vendas.usecase.cupom.dto.CriarCupomInput;
import com.afiliados.vendas.usecase.cupom.dto.CupomAdminDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/cupons")
public class CupomAdminController {

    private final ListarCuponsAdminUseCase listarCuponsAdminUseCase;
    private final BuscarCupomPorIdUseCase buscarCupomPorIdUseCase;
    private final CriarCupomUseCase criarCupomUseCase;
    private final AtualizarCupomUseCase atualizarCupomUseCase;
    private final RemoverCupomUseCase removerCupomUseCase;
    private final AlternarStatusCupomUseCase alternarStatusCupomUseCase;

    public CupomAdminController(ListarCuponsAdminUseCase listarCuponsAdminUseCase,
                                 BuscarCupomPorIdUseCase buscarCupomPorIdUseCase,
                                 CriarCupomUseCase criarCupomUseCase,
                                 AtualizarCupomUseCase atualizarCupomUseCase,
                                 RemoverCupomUseCase removerCupomUseCase,
                                 AlternarStatusCupomUseCase alternarStatusCupomUseCase) {
        this.listarCuponsAdminUseCase = listarCuponsAdminUseCase;
        this.buscarCupomPorIdUseCase = buscarCupomPorIdUseCase;
        this.criarCupomUseCase = criarCupomUseCase;
        this.atualizarCupomUseCase = atualizarCupomUseCase;
        this.removerCupomUseCase = removerCupomUseCase;
        this.alternarStatusCupomUseCase = alternarStatusCupomUseCase;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cupons", listarCuponsAdminUseCase.executar());
        return "admin/cupons/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("lojas", Loja.values());
        model.addAttribute("tipos", TipoDesconto.values());
        return "admin/cupons/form";
    }

    @PostMapping
    public String criar(@RequestParam String codigo, @RequestParam String loja,
                         @RequestParam(required = false) String linkOpcional,
                         @RequestParam String tipoDesconto, @RequestParam BigDecimal valorDesconto,
                         @RequestParam(required = false) BigDecimal valorMinimoCompra,
                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataValidade) {
        criarCupomUseCase.executar(new CriarCupomInput(
                codigo, loja, linkOpcional, tipoDesconto, valorDesconto, valorMinimoCompra, dataValidade));
        return "redirect:/admin/cupons";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        CupomAdminDTO cupom = buscarCupomPorIdUseCase.executar(id);
        model.addAttribute("cupom", cupom);
        model.addAttribute("lojas", Loja.values());
        model.addAttribute("tipos", TipoDesconto.values());
        return "admin/cupons/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @RequestParam String codigo, @RequestParam String loja,
                             @RequestParam(required = false) String linkOpcional,
                             @RequestParam String tipoDesconto, @RequestParam BigDecimal valorDesconto,
                             @RequestParam(required = false) BigDecimal valorMinimoCompra,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataValidade) {
        atualizarCupomUseCase.executar(new AtualizarCupomInput(
                id, codigo, loja, linkOpcional, tipoDesconto, valorDesconto, valorMinimoCompra, dataValidade));
        return "redirect:/admin/cupons";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id) {
        removerCupomUseCase.executar(id);
        return "redirect:/admin/cupons";
    }

    @PostMapping("/{id}/habilitar")
    public String habilitar(@PathVariable Long id) {
        alternarStatusCupomUseCase.executar(id, true);
        return "redirect:/admin/cupons";
    }

    @PostMapping("/{id}/desabilitar")
    public String desabilitar(@PathVariable Long id) {
        alternarStatusCupomUseCase.executar(id, false);
        return "redirect:/admin/cupons";
    }
}
