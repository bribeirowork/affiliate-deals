package com.afiliados.vendas.usecase.oferta;

import com.afiliados.vendas.usecase.oferta.dto.OfertaAdminDTO;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import com.afiliados.vendas.usecase.oferta.mapper.OfertaDTOMapper;

import java.util.List;

/** Use Case: listar TODAS as ofertas (ativas e inativas) para o backoffice. */
public class ListarOfertasAdminUseCase {

    private final OfertaGateway ofertaGateway;

    public ListarOfertasAdminUseCase(OfertaGateway ofertaGateway) {
        this.ofertaGateway = ofertaGateway;
    }

    public List<OfertaAdminDTO> executar() {
        return ofertaGateway.buscarTodas().stream()
                .map(OfertaDTOMapper::paraAdminDTO)
                .toList();
    }
}
