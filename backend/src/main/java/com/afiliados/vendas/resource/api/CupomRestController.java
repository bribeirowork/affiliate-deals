package com.afiliados.vendas.resource.api;

import com.afiliados.vendas.usecase.cupom.ListarCuponsUseCase;
import com.afiliados.vendas.usecase.cupom.dto.CupomDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cupons")
public class CupomRestController {

    private final ListarCuponsUseCase listarCuponsUseCase;

    public CupomRestController(ListarCuponsUseCase listarCuponsUseCase) {
        this.listarCuponsUseCase = listarCuponsUseCase;
    }

    @GetMapping
    public List<CupomDTO> listar() {
        return listarCuponsUseCase.executar();
    }
}
