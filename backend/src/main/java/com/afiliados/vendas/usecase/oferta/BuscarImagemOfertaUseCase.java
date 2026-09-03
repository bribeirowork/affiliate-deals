package com.afiliados.vendas.usecase.oferta;

import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.usecase.oferta.dto.ImagemOfertaDTO;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import com.afiliados.vendas.usecase.oferta.mapper.OfertaDTOMapper;

import java.util.Optional;

/** Use Case: servir os bytes da imagem própria de uma oferta (endpoint público). */
public class BuscarImagemOfertaUseCase {

    private final OfertaGateway ofertaGateway;

    public BuscarImagemOfertaUseCase(OfertaGateway ofertaGateway) {
        this.ofertaGateway = ofertaGateway;
    }

    public Optional<ImagemOfertaDTO> executar(Long id) {
        return ofertaGateway.buscarPorId(id)
                .filter(Oferta::possuiImagemPropria)
                .map(OfertaDTOMapper::paraImagemDTO);
    }
}
