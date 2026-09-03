package com.afiliados.vendas.resource.web;

import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.usecase.oferta.*;
import com.afiliados.vendas.usecase.oferta.dto.AtualizarOfertaInput;
import com.afiliados.vendas.usecase.oferta.dto.CriarOfertaInput;
import com.afiliados.vendas.usecase.oferta.dto.OfertaAdminDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Controller SSR (Thymeleaf) do Backoffice. Protegido por /admin/** no
 * SecurityConfig — só chega aqui quem estiver autenticado.
 * Assim como o controller REST, só conhece Use Cases e DTOs.
 */
@Controller
@RequestMapping("/admin/ofertas")
public class OfertaAdminController {

    private final ListarOfertasAdminUseCase listarOfertasAdminUseCase;
    private final BuscarOfertaPorIdUseCase buscarOfertaPorIdUseCase;
    private final CriarOfertaUseCase criarOfertaUseCase;
    private final AtualizarOfertaUseCase atualizarOfertaUseCase;
    private final RemoverOfertaUseCase removerOfertaUseCase;
    private final AlternarStatusOfertaUseCase alternarStatusOfertaUseCase;

    public OfertaAdminController(ListarOfertasAdminUseCase listarOfertasAdminUseCase,
                                  BuscarOfertaPorIdUseCase buscarOfertaPorIdUseCase,
                                  CriarOfertaUseCase criarOfertaUseCase,
                                  AtualizarOfertaUseCase atualizarOfertaUseCase,
                                  RemoverOfertaUseCase removerOfertaUseCase,
                                  AlternarStatusOfertaUseCase alternarStatusOfertaUseCase) {
        this.listarOfertasAdminUseCase = listarOfertasAdminUseCase;
        this.buscarOfertaPorIdUseCase = buscarOfertaPorIdUseCase;
        this.criarOfertaUseCase = criarOfertaUseCase;
        this.atualizarOfertaUseCase = atualizarOfertaUseCase;
        this.removerOfertaUseCase = removerOfertaUseCase;
        this.alternarStatusOfertaUseCase = alternarStatusOfertaUseCase;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ofertas", listarOfertasAdminUseCase.executar());
        return "admin/ofertas/list";
    }

    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("lojas", Loja.values());
        return "admin/ofertas/form";
    }

    @PostMapping
    public String criar(@RequestParam String nomeProduto, @RequestParam String imagemUrl,
                         @RequestParam(required = false) MultipartFile imagemArquivo,
                         @RequestParam String loja, @RequestParam BigDecimal valor,
                         @RequestParam(required = false) BigDecimal valorOriginal,
                         @RequestParam String linkAfiliado,
                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataExpiracaoDia,
                         @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataExpiracaoSemana) {
        criarOfertaUseCase.executar(new CriarOfertaInput(
                nomeProduto, imagemUrl,
                lerBytes(imagemArquivo), tipoConteudo(imagemArquivo), nomeOriginal(imagemArquivo),
                loja, valor, valorOriginal, linkAfiliado, dataExpiracaoDia, dataExpiracaoSemana));
        return "redirect:/admin/ofertas";
    }

    @GetMapping("/{id}/editar")
    public String editarForm(@PathVariable Long id, Model model) {
        OfertaAdminDTO oferta = buscarOfertaPorIdUseCase.executar(id);
        model.addAttribute("oferta", oferta);
        model.addAttribute("lojas", Loja.values());
        return "admin/ofertas/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @RequestParam String nomeProduto,
                             @RequestParam String imagemUrl,
                             @RequestParam(required = false) MultipartFile imagemArquivo,
                             @RequestParam(defaultValue = "false") boolean removerImagem,
                             @RequestParam String loja,
                             @RequestParam BigDecimal valor,
                             @RequestParam(required = false) BigDecimal valorOriginal,
                             @RequestParam String linkAfiliado,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataExpiracaoDia,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime dataExpiracaoSemana) {
        atualizarOfertaUseCase.executar(new AtualizarOfertaInput(
                id, nomeProduto, imagemUrl,
                lerBytes(imagemArquivo), tipoConteudo(imagemArquivo), nomeOriginal(imagemArquivo), removerImagem,
                loja, valor, valorOriginal, linkAfiliado, dataExpiracaoDia, dataExpiracaoSemana));
        return "redirect:/admin/ofertas";
    }

    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id) {
        removerOfertaUseCase.executar(id);
        return "redirect:/admin/ofertas";
    }

    @PostMapping("/{id}/habilitar")
    public String habilitar(@PathVariable Long id) {
        alternarStatusOfertaUseCase.executar(id, true);
        return "redirect:/admin/ofertas";
    }

    @PostMapping("/{id}/desabilitar")
    public String desabilitar(@PathVariable Long id) {
        alternarStatusOfertaUseCase.executar(id, false);
        return "redirect:/admin/ofertas";
    }

    /** Leitura do multipart é um detalhe de I/O do controller — o Use Case só recebe bytes prontos. */
    private static byte[] lerBytes(MultipartFile arquivo) {
        if (arquivo == null || arquivo.isEmpty()) {
            return null;
        }
        try {
            return arquivo.getBytes();
        } catch (IOException e) {
            throw new UncheckedIOException("Falha ao ler o arquivo de imagem enviado.", e);
        }
    }

    private static String tipoConteudo(MultipartFile arquivo) {
        return arquivo != null && !arquivo.isEmpty() ? arquivo.getContentType() : null;
    }

    private static String nomeOriginal(MultipartFile arquivo) {
        return arquivo != null && !arquivo.isEmpty() ? arquivo.getOriginalFilename() : null;
    }
}
