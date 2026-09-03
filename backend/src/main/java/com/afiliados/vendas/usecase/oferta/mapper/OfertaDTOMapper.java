package com.afiliados.vendas.usecase.oferta.mapper;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.usecase.oferta.dto.CupomResumoDTO;
import com.afiliados.vendas.usecase.oferta.dto.ImagemOfertaDTO;
import com.afiliados.vendas.usecase.oferta.dto.OfertaAdminDTO;
import com.afiliados.vendas.usecase.oferta.dto.OfertaDTO;

import java.util.List;

/**
 * Fronteira entre o domínio e o mundo externo (API / Thymeleaf).
 * É o único lugar autorizado a "ler" a entidade Oferta (e, para o cruzamento
 * com cupons da mesma loja, a entidade Cupom) para produzir DTOs — assim as
 * entidades nunca precisam sair da camada de Use Case.
 */
public final class OfertaDTOMapper {

    private OfertaDTOMapper() {}

    /**
     * Uma oferta com imagem própria (upload) expõe a URL do endpoint que serve os bytes;
     * sem imagem própria, expõe a URL externa informada (ou null).
     */
    private static String resolverUrlExibicao(Oferta oferta) {
        return oferta.possuiImagemPropria()
                ? "/api/ofertas/" + oferta.getId() + "/imagem"
                : oferta.getImagemUrl();
    }

    public static OfertaDTO paraDTO(Oferta oferta, List<Cupom> cuponsDaLoja) {
        return new OfertaDTO(
                oferta.getId(),
                oferta.getNomeProduto(),
                resolverUrlExibicao(oferta),
                oferta.getLoja().name(),
                oferta.getValor(),
                oferta.getValorOriginal(),
                oferta.getLinkAfiliado(),
                oferta.getDataExpiracaoDia(),
                oferta.getDataExpiracaoSemana(),
                cuponsDaLoja.stream().map(OfertaDTOMapper::paraCupomResumoDTO).toList()
        );
    }

    public static OfertaAdminDTO paraAdminDTO(Oferta oferta) {
        return new OfertaAdminDTO(
                oferta.getId(),
                oferta.getNomeProduto(),
                resolverUrlExibicao(oferta),
                oferta.possuiImagemPropria(),
                oferta.getLoja().name(),
                oferta.getValor(),
                oferta.getValorOriginal(),
                oferta.getLinkAfiliado(),
                oferta.getDataExpiracaoDia(),
                oferta.getDataExpiracaoSemana(),
                oferta.getStatus().name()
        );
    }

    public static ImagemOfertaDTO paraImagemDTO(Oferta oferta) {
        return new ImagemOfertaDTO(
                oferta.getImagem().getDados(),
                oferta.getImagem().getTipoConteudo(),
                oferta.getImagem().getNomeArquivo()
        );
    }

    private static CupomResumoDTO paraCupomResumoDTO(Cupom cupom) {
        return new CupomResumoDTO(
                cupom.getId(),
                cupom.getCodigo(),
                cupom.getLinkOpcional(),
                cupom.getTipoDesconto().name(),
                cupom.getValorDesconto(),
                cupom.getDataValidade()
        );
    }
}
