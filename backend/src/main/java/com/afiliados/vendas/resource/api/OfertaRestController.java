package com.afiliados.vendas.resource.api;

import com.afiliados.vendas.usecase.oferta.BuscarImagemOfertaUseCase;
import com.afiliados.vendas.usecase.oferta.ListarOfertasUseCase;
import com.afiliados.vendas.usecase.oferta.dto.ImagemOfertaDTO;
import com.afiliados.vendas.usecase.oferta.dto.ListarOfertasOutput;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint público consumido pela landing page em React.
 * Fica em resource/api porque é a camada mais externa (I/O) — só conhece
 * o Use Case e o DTO que ele devolve, nunca a entidade de domínio.
 */
@RestController
@RequestMapping("/api/ofertas")
public class OfertaRestController {

    private final ListarOfertasUseCase listarOfertasUseCase;
    private final BuscarImagemOfertaUseCase buscarImagemOfertaUseCase;

    public OfertaRestController(ListarOfertasUseCase listarOfertasUseCase,
                                 BuscarImagemOfertaUseCase buscarImagemOfertaUseCase) {
        this.listarOfertasUseCase = listarOfertasUseCase;
        this.buscarImagemOfertaUseCase = buscarImagemOfertaUseCase;
    }

    @GetMapping
    public ListarOfertasOutput listar() {
        return listarOfertasUseCase.executar();
    }

    /** Serve o binário da imagem de uma oferta que foi enviada por upload (armazenada no banco). */
    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> imagem(@PathVariable Long id) {
        return buscarImagemOfertaUseCase.executar(id)
                .map(OfertaRestController::paraResposta)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private static ResponseEntity<byte[]> paraResposta(ImagemOfertaDTO imagem) {
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imagem.contentType()))
                .body(imagem.dados());
    }
}
