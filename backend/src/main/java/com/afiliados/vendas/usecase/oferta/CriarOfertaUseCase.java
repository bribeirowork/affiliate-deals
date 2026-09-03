package com.afiliados.vendas.usecase.oferta;

import com.afiliados.vendas.domain.oferta.ImagemProduto;
import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.usecase.oferta.dto.CriarOfertaInput;
import com.afiliados.vendas.usecase.oferta.dto.OfertaAdminDTO;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import com.afiliados.vendas.usecase.oferta.mapper.OfertaDTOMapper;

public class CriarOfertaUseCase {

    private final OfertaGateway ofertaGateway;

    public CriarOfertaUseCase(OfertaGateway ofertaGateway) {
        this.ofertaGateway = ofertaGateway;
    }

    public OfertaAdminDTO executar(CriarOfertaInput input) {
        ImagemProduto imagem = input.imagemDados() != null && input.imagemDados().length > 0
                ? new ImagemProduto(input.imagemDados(), input.imagemContentType(), input.imagemNomeArquivo())
                : null;

        Oferta oferta = Oferta.criar(
                input.nomeProduto(),
                input.imagemUrl(),
                imagem,
                Loja.valueOf(input.loja()),
                input.valor(),
                input.valorOriginal(),
                input.linkAfiliado(),
                input.dataExpiracaoDia(),
                input.dataExpiracaoSemana()
        );

        Oferta salva = ofertaGateway.salvar(oferta);
        return OfertaDTOMapper.paraAdminDTO(salva);
    }
}
