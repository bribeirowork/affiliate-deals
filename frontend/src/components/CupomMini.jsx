import { useState } from 'react';

function formatarDesconto(cupom) {
  return cupom.tipoDesconto === 'PERCENTUAL'
    ? `${cupom.valorDesconto}% OFF`
    : `R$ ${cupom.valorDesconto} OFF`;
}

/**
 * Cupom embutido logo abaixo do produto da mesma loja.
 * Com link: o clique redireciona para a página de resgate.
 * Sem link (só código): o clique copia o código para a área de transferência.
 */
export default function CupomMini({ cupom, onUsar }) {
  const [copiado, setCopiado] = useState(false);
  const temLink = Boolean(cupom.linkOpcional);

  function marcarCopiado() {
    setCopiado(true);
    setTimeout(() => setCopiado(false), 2000);
  }

  /** Fallback para quando a Clipboard API não está disponível ou a permissão é negada. */
  function copiarComExecCommand() {
    const campoTemporario = document.createElement('textarea');
    campoTemporario.value = cupom.codigo;
    campoTemporario.style.position = 'fixed';
    campoTemporario.style.opacity = '0';
    document.body.appendChild(campoTemporario);
    campoTemporario.select();
    try {
      document.execCommand('copy');
      marcarCopiado();
    } catch {
      // se nem o fallback funcionar, o código já está visível no botão para o usuário copiar manualmente
    } finally {
      document.body.removeChild(campoTemporario);
    }
  }

  function copiarCodigo(evento) {
    evento.preventDefault();
    onUsar?.();

    if (navigator.clipboard?.writeText) {
      navigator.clipboard.writeText(cupom.codigo).then(marcarCopiado).catch(copiarComExecCommand);
    } else {
      copiarComExecCommand();
    }
  }

  if (temLink) {
    return (
      <a
        className="cupom-mini cupom-mini--link"
        href={cupom.linkOpcional}
        target="_blank"
        rel="noopener noreferrer sponsored"
        onClick={onUsar}
      >
        <span className="cupom-mini__desconto">{formatarDesconto(cupom)}</span>
        <span className="cupom-mini__acao">Resgatar cupom</span>
      </a>
    );
  }

  return (
    <button type="button" className="cupom-mini" onClick={copiarCodigo}>
      <span className="cupom-mini__desconto">{formatarDesconto(cupom)}</span>
      <span className="cupom-mini__acao">{copiado ? 'Copiado!' : `USE ${cupom.codigo}`}</span>
    </button>
  );
}
