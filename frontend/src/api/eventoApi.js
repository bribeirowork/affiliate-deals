import { BASE_URL } from './apiClient';

const SESSION_KEY = 'ofertahub_session_id';

function getSessionId() {
  let id = localStorage.getItem(SESSION_KEY);
  if (!id) {
    id = crypto.randomUUID();
    localStorage.setItem(SESSION_KEY, id);
  }
  return id;
}

export function registrarEvento(ofertaId, tipo) {
  if (!ofertaId) return;
  const payload = {
    ofertaId,
    tipo,
    sessionId: getSessionId(),
    leadId: null,
  };
  // fire-and-forget — erros silenciosos para não impactar a UX
  fetch(`${BASE_URL}/api/eventos`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
    keepalive: true,
  }).catch(() => {});
}
