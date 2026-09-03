package com.afiliados.vendas.usecase.oferta;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.domain.oferta.SecaoOferta;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import com.afiliados.vendas.usecase.oferta.dto.ListarOfertasOutput;
import com.afiliados.vendas.usecase.oferta.dto.OfertaDTO;
import com.afiliados.vendas.usecase.oferta.gateway.OfertaGateway;
import com.afiliados.vendas.usecase.oferta.mapper.OfertaDTOMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Use Case: Listar ofertas para a vitrine pública.
 *
 * Orquestração (e só orquestração):
 *  1. Busca as ofertas ativas e os cupons válidos via seus Gateways (portas de
 *     saída, sem saber que é JPA) — o cruzamento entre os dois é feito aqui, pois
 *     um Gateway nunca deve conversar com outro Gateway diretamente.
 *  2. Pergunta a cada Oferta (regra de domínio) em qual seção ela se encaixa agora.
 *  3. Usa o Mapper (Factory) para juntar Oferta + cupons da mesma loja em um DTO.
 *
 * Este Use Case NUNCA chama outro Use Case, e as entidades nunca saem deste
 * método — apenas os DTOs produzidos pelo mapper atravessam a fronteira.
 */
public class ListarOfertasUseCase {

    private final OfertaGateway ofertaGateway;
    private final CupomGateway cupomGateway;

    public ListarOfertasUseCase(OfertaGateway ofertaGateway, CupomGateway cupomGateway) {
        this.ofertaGateway = ofertaGateway;
        this.cupomGateway = cupomGateway;
    }

    public ListarOfertasOutput executar() {
        LocalDateTime agora = LocalDateTime.now();

        List<Oferta> ofertasAtivas = ofertaGateway.buscarAtivas();
        Map<Loja, List<Cupom>> cuponsPorLoja = cupomGateway.buscarAtivos().stream()
                .filter(cupom -> cupom.isValido(agora))
                .collect(Collectors.groupingBy(Cupom::getLoja));

        List<OfertaDTO> ofertasDoDia = new ArrayList<>();
        List<OfertaDTO> ofertasDaSemana = new ArrayList<>();

        for (Oferta oferta : ofertasAtivas) {
            SecaoOferta secao = oferta.classificarSecao(agora);
            List<Cupom> cuponsDaLoja = cuponsPorLoja.getOrDefault(oferta.getLoja(), List.of()).stream()
                    .filter(cupom -> cupom.aplicavelPara(oferta.getValor()))
                    .toList();

            if (secao == SecaoOferta.DO_DIA) {
                ofertasDoDia.add(OfertaDTOMapper.paraDTO(oferta, cuponsDaLoja));
            } else if (secao == SecaoOferta.DA_SEMANA) {
                ofertasDaSemana.add(OfertaDTOMapper.paraDTO(oferta, cuponsDaLoja));
            }
            // SecaoOferta.EXPIRADA: descartada, não aparece em nenhuma seção pública.
        }

        return new ListarOfertasOutput(ofertasDoDia, ofertasDaSemana);
    }
}
