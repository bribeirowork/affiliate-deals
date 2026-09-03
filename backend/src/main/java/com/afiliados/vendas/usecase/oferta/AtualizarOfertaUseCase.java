package com.afiliados.vendas.usecase.oferta;

import com.afiliados.vendas.domain.oferta.ImagemProduto;
import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.usecase.oferta.dto.AtualizarOfertaInput;
import com.afiliados.vendas.usecase.oferta.dto.OfertaAdminDTO;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import com.afiliados.vendas.usecase.oferta.mapper.OfertaDTOMapper;

public class AtualizarOfertaUseCase {

    private final OfertaGateway ofertaGateway;

    public AtualizarOfertaUseCase(OfertaGateway ofertaGateway) {
        this.ofertaGateway = ofertaGateway;
    }

    public OfertaAdminDTO executar(AtualizarOfertaInput input) {
        Oferta oferta = ofertaGateway.buscarPorId(input.id())
                .orElseThrow(() -> new OfertaNaoEncontradaException(input.id()));

        oferta.atualizarDados(
                input.nomeProduto(),
                input.imagemUrl(),
                resolverImagem(input, oferta),
                Loja.valueOf(input.loja()),
                input.valor(),
                input.valorOriginal(),
                input.linkAfiliado(),
                input.dataExpiracaoDia(),
                input.dataExpiracaoSemana()
        );

        Oferta atualizada = ofertaGateway.salvar(oferta);
        return OfertaDTOMapper.paraAdminDTO(atualizada);
    }

    /**
     * Um novo arquivo enviado substitui a imagem atual; marcar "remover" apaga
     * a imagem sem enviar outra; caso contrário, a imagem existente é preservada
     * (o formulário de edição não reenvia o arquivo a cada salvamento).
     */
    private ImagemProduto resolverImagem(AtualizarOfertaInput input, Oferta ofertaAtual) {
        if (input.imagemDados() != null && input.imagemDados().length > 0) {
            return new ImagemProduto(input.imagemDados(), input.imagemContentType(), input.imagemNomeArquivo());
        }
        if (input.removerImagem()) {
            return null;
        }
        return ofertaAtual.getImagem();
    }
}
