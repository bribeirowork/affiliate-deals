import { useEffect, useState } from 'react';

function calcularRestante(alvo) {
  const diff = new Date(alvo).getTime() - Date.now();
  return diff > 0 ? diff : 0;
}

function formatar(ms) {
  const totalSegundos = Math.floor(ms / 1000);
  const dias = Math.floor(totalSegundos / 86400);
  const horas = Math.floor((totalSegundos % 86400) / 3600);
  const minutos = Math.floor((totalSegundos % 3600) / 60);
  const segundos = totalSegundos % 60;
  const doisDigitos = (n) => String(n).padStart(2, '0');

  return dias > 0
    ? `${dias}d ${doisDigitos(horas)}:${doisDigitos(minutos)}:${doisDigitos(segundos)}`
    : `${doisDigitos(horas)}:${doisDigitos(minutos)}:${doisDigitos(segundos)}`;
}

/**
 * Cronômetro de urgência até `alvo` (data real de expiração vinda do backend).
 * Não trava em "00:00:00": quando o tempo se esgota, o App já está recarregando
 * os dados periodicamente em segundo plano, então a oferta se reclassifica (ou
 * some da vitrine) sozinha, sem o usuário precisar atualizar a página.
 */
export default function CountdownTimer({ alvo }) {
  const [restante, setRestante] = useState(() => calcularRestante(alvo));

  useEffect(() => {
    setRestante(calcularRestante(alvo));
    const intervalo = setInterval(() => setRestante(calcularRestante(alvo)), 1000);
    return () => clearInterval(intervalo);
  }, [alvo]);

  const urgente = restante > 0 && restante < 60 * 60 * 1000; // menos de 1h restante

  return (
    <div className={`card__cronometro ${urgente ? 'card__cronometro--urgente' : ''}`}>
      <span aria-hidden="true">⏰</span>
      <span>{restante > 0 ? `Termina em ${formatar(restante)}` : 'Encerrando...'}</span>
    </div>
  );
}
