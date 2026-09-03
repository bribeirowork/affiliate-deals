import { apiGet } from './apiClient';

/**
 * GET /api/ofertas -> { ofertasDoDia: [...], ofertasDaSemana: [...] }
 * A separação por seção já vem pronta do backend (regra de domínio
 * aplicada no ListarOfertasUseCase) — o front só renderiza.
 */
export function buscarOfertas() {
  return apiGet('/api/ofertas');
}
