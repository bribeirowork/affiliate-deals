export const BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8080';

/**
 * Wrapper fino sobre fetch. A API é pública (sem sessão/token) — o
 * backend libera /api/** explicitamente no SecurityConfig.
 */
export async function apiGet(path) {
  const response = await fetch(`${BASE_URL}${path}`);

  if (!response.ok) {
    throw new Error(`Falha ao consultar ${path}: HTTP ${response.status}`);
  }

  return response.json();
}

/**
 * Ofertas com imagem própria (upload) trazem um caminho relativo ao backend
 * (ex.: "/api/ofertas/1/imagem"); URLs externas já vêm completas e passam direto.
 */
export function resolverUrlImagem(imagemUrl) {
  if (!imagemUrl) return imagemUrl;
  return imagemUrl.startsWith('/') ? `${BASE_URL}${imagemUrl}` : imagemUrl;
}
