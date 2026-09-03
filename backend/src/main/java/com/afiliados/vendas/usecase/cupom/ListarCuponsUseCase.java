package com.afiliados.vendas.usecase.cupom;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.usecase.cupom.dto.CupomDTO;
import com.afiliados.vendas.usecase.cupom.gateway.CupomGateway;
import com.afiliados.vendas.usecase.cupom.mapper.CupomDTOMapper;

import java.time.LocalDateTime;
import java.util.List;

/** Use Case: listar cupons válidos hoje para a vitrine pública. */
public class ListarCuponsUseCase {

    private final CupomGateway cupomGateway;

    public ListarCuponsUseCase(CupomGateway cupomGateway) {
        this.cupomGateway = cupomGateway;
    }

    public List<CupomDTO> executar() {
        LocalDateTime agora = LocalDateTime.now();

        return cupomGateway.buscarAtivos().stream()
                .filter(cupom -> cupom.isValido(agora))
                .map(CupomDTOMapper::paraDTO)
                .toList();
    }
}
