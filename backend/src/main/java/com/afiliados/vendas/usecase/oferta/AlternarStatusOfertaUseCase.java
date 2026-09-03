package com.afiliados.vendas.usecase.oferta;

import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.usecase.oferta.dto.OfertaAdminDTO;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import com.afiliados.vendas.usecase.oferta.mapper.OfertaDTOMapper;

/** Use Case: habilitar/desabilitar uma oferta sem apagar seus dados. */
public class AlternarStatusOfertaUseCase {

    private final OfertaGateway ofertaGateway;

    public AlternarStatusOfertaUseCase(OfertaGateway ofertaGateway) {
        this.ofertaGateway = ofertaGateway;
    }

    public OfertaAdminDTO executar(Long id, boolean habilitar) {
        Oferta oferta = ofertaGateway.buscarPorId(id)
                .orElseThrow(() -> new OfertaNaoEncontradaException(id));

        if (habilitar) {
            oferta.habilitar();
        } else {
            oferta.desabilitar();
        }

        Oferta atualizada = ofertaGateway.salvar(oferta);
        return OfertaDTOMapper.paraAdminDTO(atualizada);
    }
}
